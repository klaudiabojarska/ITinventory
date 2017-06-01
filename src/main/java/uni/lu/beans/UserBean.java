package uni.lu.beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List ;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.sql.DataSource;

import uni.lu.CommonUtils;
import uni.lu.Group;
import uni.lu.User;

@ManagedBean(name="user")
@SessionScoped
public class UserBean implements Serializable{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//resource injection
	@Resource(name="jdbc/webprog2")
	private DataSource ds;

	private List<User> list;
    private User item = new User();
    private User beforeEditItem = null;
    private boolean edit;
    private Connection con;
    private String where;
    private Group selectedGroup;
	
    @ManagedProperty(value="#{commonUtils}")
    private CommonUtils util;
    public void setUtil(CommonUtils util) {
        this.util = util;
    }

	@PostConstruct
    public void init(){
        list = new ArrayList<User>();
    }

	public List<User> getUserList() throws SQLException{
		
		if(ds==null)
			throw new SQLException("Can't get data source");
		
    	if(con==null){
    		//get database connection
    		con = ds.getConnection();
    		
    		if(con==null)
    			throw new SQLException("Can't get database connection");
    	}
		
		PreparedStatement ps 
			= con.prepareStatement(
				"select * from users left join groups on users.group_id = groups.id " + (where!=null && !where.equals("")? "WHERE " + where : "")); 
		ResultSet result =  ps.executeQuery();

		list.clear();
		
		while(result.next()){
			User entity = new User();
			
			entity.setId(result.getLong("id"));
			entity.setLogin(result.getString("login"));
			entity.setGroup_name(result.getString("groups.name"));
			entity.setF_name(result.getString("first_name"));
			entity.setL_name(result.getString("last_name"));
			entity.setAddress(result.getString("address"));
			entity.setPassword(result.getString("password"));
			entity.setGroup_id(result.getLong("group_id"));
			entity.setTime_create(result.getDate("time_create"));
			
			
			//store all data into a List
			list.add(entity);
		}
			
		return list;
	}

	public void add() throws SQLException {
       	if(con==null){
    		//get database connection
    		con = ds.getConnection();
    		
    		if(con==null)
    			throw new SQLException("Can't get database connection");
    	}
		
       	long groupId = selectedGroup != null? selectedGroup.getId() : null;
       	
		PreparedStatement ps 
			= con.prepareStatement("INSERT INTO users (login, first_name, last_name, address, password, group_id) "+
					"VALUES ( '" + item.login + "', '" + item.f_name + "', '" + item.l_name +
					"', '" + item.address + "', '" + item.password + "', '" + groupId + "' )"); 
		
		int status =  ps.executeUpdate();  
		
		if(status == 1){		
	        item.setId(list.isEmpty() ? 1 : list.get(list.size() - 1).getId() + 1);
	        list.add(item);
	        item = new User();
		}

        util.redirectWithGet();
	}
    
    public void resetAdd() {
    	selectedGroup = null;
        item = new User();
        where = null;
        util.redirectWithGet();
    }
    
    public void edit(User item, GroupBean groupBean){
        beforeEditItem = item.clone();
        this.item = item;
        selectedGroup = groupBean.getGroup(item.getGroup_id());
        edit = true;
        where = null;
        util.redirectWithGet();
    }
    
    public void cancelEdit() {
    	selectedGroup = null;
    	this.item.restore(beforeEditItem);
        this.item = new User();
        edit = false;
        where = null;
        util.redirectWithGet();
    }
    
    public void saveEdit() throws SQLException {
       	if(con==null){
    		//get database connection
    		con = ds.getConnection();
    		
    		if(con==null)
    			throw new SQLException("Can't get database connection");
    	}

       	long groupId = selectedGroup != null? selectedGroup.getId() : null;
       	PreparedStatement ps;
       	if(item.password != null) {

			ps = con.prepareStatement("UPDATE  users " +
						"SET login = '" + item.login + "', first_name = '" + item.f_name  + "', last_name = '" +
						item.l_name + "', address = '" + item.address + "' , password = '" + item.password + 
						"' , group_id = '" + groupId + 
						"' WHERE id = " + item.id );
       	} else {
			ps = con.prepareStatement("UPDATE  users " +
					"SET login = '" + item.login + "', first_name = '" + item.f_name  + "', last_name = '" +
					item.l_name + "', address = '" + item.address + "' , group_id = '" + groupId + 
					"' WHERE id = " + item.id );
       	}
		
		int status =  ps.executeUpdate();  
		
		if(status == 1){
	        this.item = new User();
	        edit = false;
	        where = null;
		}
        util.redirectWithGet();
    }
    
    public void delete(User item) throws SQLException {
    	if(con==null){
    		//get database connection
    		con = ds.getConnection();
    		
    		if(con==null)
    			throw new SQLException("Can't get database connection");
    	}
		
		PreparedStatement ps 
			= con.prepareStatement(
				"delete from users where id = " + item.getId()); 
		
		int status =  ps.executeUpdate();
		if(status == 1){
			list.remove(item);
	        where = null;
		} else {
			
		}
        util.redirectWithGet();
    }

    public List<User> getList() throws SQLException {
        return getUserList();
    }
    
    public User getItem() {
        return this.item;
    }
    
    public boolean isEdit() {
        return this.edit;
    }
    
    public void search() throws SQLException {
    	StringBuilder whereSB = new StringBuilder(" 1=1 ");
    	if(item.getLogin() != null && !item.getLogin().equals(""))
    		whereSB.append(" AND login LIKE '%" + item.getLogin() + "%'");
    	if(item.getF_name() != null && !item.getF_name().equals(""))
    		whereSB.append(" AND first_name LIKE '%" + item.getF_name() + "%'");
    	if(item.getL_name() != null && !item.getL_name().equals(""))
    		whereSB.append(" AND last_name LIKE '%" + item.getL_name() + "%'");
    	if(item.getAddress() != null && !item.getAddress().equals(""))
    		whereSB.append(" AND address LIKE '%" + item.getAddress() + "%'");
    	if(selectedGroup != null)
    		whereSB.append(" AND group_id LIKE '%" + selectedGroup.getId() + "%'");
    	
    	where = whereSB.toString();
    	getUserList();
    }
    
    public User getUser(Long id) {
        if (id == null){
            throw new IllegalArgumentException("no id provided");
        }
        for (User user : list){
            if (id.equals(user.getId())){
                return user;
            }
        }
        return null;
    }

	public Group getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;
	}
    
}
