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

@ManagedBean(name="group")
@SessionScoped
public class GroupBean implements Serializable{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//resource injection
	@Resource(name="jdbc/webprog2")
	private DataSource ds;
	
	
	private List<Group> list;
    private Group item = new Group();
    private Group beforeEditItem = null;
    private boolean edit;
    private Connection con;
    private String where;
    
	
    @ManagedProperty(value="#{commonUtils}")
    private CommonUtils util;
    public void setUtil(CommonUtils util) {
        this.util = util;
    }

	@PostConstruct
    public void init(){
        list = new ArrayList<Group>();
    }

	public List<Group> getGroupList() throws SQLException{
		
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
				"select * from groups " + (where!=null && !where.equals("")? "WHERE " + where : "")); 
		ResultSet result =  ps.executeQuery();

		list.clear();
		
		while(result.next()){
			Group entity = new Group();
			
			entity.setId(result.getLong("id"));
			entity.setName(result.getString("name"));
			entity.setTime_create(result.getDate("time_create"));
			entity.setCanCreateUser(result.getBoolean("canCreateUser"));
			entity.setCanReadUser(result.getBoolean("canReadUser"));
			entity.setCanEditUser(result.getBoolean("canEditUser"));

			entity.setCanCreateGroup(result.getBoolean("canCreateGroup"));
			entity.setCanReadGroup(result.getBoolean("canReadGroup"));
			entity.setCanEditGroup(result.getBoolean("canEditGroup"));

			entity.setCanCreateDevice(result.getBoolean("canCreateDevice"));
			entity.setCanReadDevice(result.getBoolean("canReadDevice"));
			entity.setCanEditDevice(result.getBoolean("canEditDevice"));

			entity.setCanCreateConsumables(result.getBoolean("canCreateConsumables"));
			entity.setCanReadConsumables(result.getBoolean("canReadConsumables"));
			entity.setCanEditConsumables(result.getBoolean("canEditConsumables"));

			entity.setCanCreateRepair(result.getBoolean("canCreateRepair"));
			entity.setCanReadRepair(result.getBoolean("canReadRepair"));
			entity.setCanEditRepair(result.getBoolean("canEditRepair"));
			
			entity.setOwnedByUser(result.getBoolean("ownedByUser"));
			
			
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
		
       	//long userId = selectedUser != null? selectedUser.getId() : null;
		PreparedStatement ps 
			= con.prepareStatement("INSERT INTO groups (name, "
									+ "canCreateUser, canReadUser, canEditUser, "
									+ "canCreateGroup, canReadGroup, canEditGroup, "
									+ "canCreateDevice, canReadDevice, canEditDevice, "
									+ "canCreateConsumables, canReadConsumables, canEditConsumables, "
									+ "canCreateRepair, canReadRepair, canEditRepair, "
									+ "ownedByUser) VALUES ( '" 
									+ item.name + "' , " + item.isCanCreateUser() + " , " + item.isCanReadUser()
									+  " , " + item.isCanEditUser()  + " , " +  item.isCanCreateGroup()  + " , "
									+ item.isCanReadGroup() + " , " + item.isCanEditGroup() + " , " 
									+ item.isCanCreateDevice() + " , " 
									+ item.isCanReadDevice() + " , " + item.isCanEditDevice() + " , " 
									+ item.isCanCreateConsumables() + " , " + item.isCanReadConsumables() + " , " 
									+ item.isCanEditConsumables() + " , " + item.isCanCreateRepair() + " , "
									+ item.isCanReadRepair() + " , " + item.isCanEditRepair() + " , "
									+ item.isOwnedByUser() + " )");
		
		int status =  ps.executeUpdate();  
		
		if(status == 1){		
	        item.setId(list.isEmpty() ? 1 : list.get(list.size() - 1).getId() + 1);
	        list.add(item);
	        item = new Group();
		}

        util.redirectWithGet();
	}
    
    public void resetAdd() {
        item = new Group();
        where = null;
        util.redirectWithGet();
    }
    
    public void edit(Group item){//, UserBean userBean){
        beforeEditItem = item.clone();
        this.item = item;
        //selectedUser = userBean.getUser(item.getUser_id());
        edit = true;
        where = null;
        util.redirectWithGet();
    }
    
    public void cancelEdit() {
        //selectedUser = null;
        this.item.restore(beforeEditItem);
        this.item = new Group();
        edit = false;
        where = null;
        util.redirectWithGet();
        //selectedUser = null;
    }
    
    public void saveEdit() throws SQLException {
       	if(con==null){
    		//get database connection
    		con = ds.getConnection();
    		
    		if(con==null)
    			throw new SQLException("Can't get database connection");
    	}
		
       	PreparedStatement ps;

       	//long userId = selectedUser != null? selectedUser.getId() : null;
		ps = con.prepareStatement("UPDATE  groups SET name = '" + item.name 
				+ "', canCreateUser = " + item.isCanCreateUser() 
				+ ", canReadUser = " + item.isCanEditUser() 
				+ ", canEditUser = " + item.isCanCreateGroup() 
				+ ", canCreateGroup = " + item.isCanCreateGroup() 
				+ ", canReadGroup = " + item.isCanReadGroup() 
				+ ", canEditGroup = " + item.isCanEditGroup() 
				+ ", canCreateDevice = " + item.isCanCreateDevice() 
				+ ", canReadDevice = " + item.isCanReadDevice() 
				+ ", canEditDevice = " + item.isCanEditDevice() 
				+ ", canCreateConsumables = " + item.isCanCreateConsumables() 
				+ ", canReadConsumables = " + item.isCanReadConsumables() 
				+ ", canEditConsumables = " + item.isCanEditConsumables() 
				+ ", canCreateRepair = " + item.isCanCreateRepair() 
				+ ", canReadRepair = " + item.isCanReadRepair() 
				+ ", canEditRepair = " + item.isCanEditRepair() 
				+ ", ownedByUser = " + item.isOwnedByUser() 
				+ " WHERE id = " + item.id );
		
			
		int status =  ps.executeUpdate();  
		
		if(status == 1){
	        this.item = new Group();
	        edit = false;
	        where = null;
		}
        util.redirectWithGet();
        //selectedUser = null;
    }
    
    public void delete(Group item) throws SQLException {
    	if(con==null){
    		//get database connection
    		con = ds.getConnection();
    		
    		if(con==null)
    			throw new SQLException("Can't get database connection");
    	}
		
		PreparedStatement ps 
			= con.prepareStatement(
				"delete from groups where id = " + item.getId()); 
		
		int status =  ps.executeUpdate();
		if(status == 1){
			list.remove(item);
	        where = null;
		} else {
			
		}
        util.redirectWithGet();
    }

	/*public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}*/
   
	
    public List<Group> getList() throws SQLException {
        return getGroupList();
    }
    
    public Group getItem() {
        return this.item;
    }
    
    public boolean isEdit() {
        return this.edit;
    }
    
    public void search() throws SQLException {
    	StringBuilder whereSB = new StringBuilder(" 1=1 ");
    	if(item.getName() != null && !item.getName().equals(""))
    		whereSB.append(" AND login LIKE '%" + item.getName() + "%'");
    	
    	where = whereSB.toString();
    	getGroupList();
    }
    public Group getGroup(Long long1) {
        if (long1 == null){
            throw new IllegalArgumentException("no id provided");
        }
        for (Group group : list){
            if (long1.equals(group.getId())){
                return group;
            }
        }
        return null;
    }

}
