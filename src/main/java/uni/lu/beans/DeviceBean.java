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
import uni.lu.Device;
import uni.lu.User;

@ManagedBean(name="device")
@SessionScoped
public class DeviceBean implements Serializable{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//resource injection
	@Resource(name="jdbc/webprog2")
	private DataSource ds;

	private User selectedUser;
	private List<Device> list;
    private Device item = new Device();
    private Device beforeEditItem = null;
    private boolean edit;
    private Connection con;
    private String where;
    private long userId;
    private boolean ownedByUser;
	
    @ManagedProperty(value="#{commonUtils}")
    private CommonUtils util;
    public void setUtil(CommonUtils util) {
        this.util = util;
    }

	@PostConstruct
    public void init(){
        list = new ArrayList<Device>();
    }

	public List<Device> getDeviceList(boolean ownedByUser, long userId) throws SQLException{
		this.ownedByUser = ownedByUser;
		this.userId = userId;

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
				"select * from devices inner join users on devices.user_id = users.id " 
			+ (where!=null && !where.equals("")? "WHERE " + where : "") 
			+ (ownedByUser? " AND devices.user_id = " + userId : "")); 
		ResultSet result =  ps.executeQuery();

		list.clear();
		
		while(result.next()){
			Device entity = new Device();
			
			entity.setId(result.getLong("id"));
			entity.setName(result.getString("name"));
			entity.setType(result.getString("type"));
			entity.setMac(result.getString("mac"));
			entity.setUser_id(result.getLong("user_id"));
			entity.setOwner(result.getString("first_name") + " " + result.getString("last_name"));
			entity.setNotes(result.getString("notes"));
			entity.setProducer(result.getString("producer"));
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
		
       	long userId = selectedUser != null? selectedUser.getId() : null;
		PreparedStatement ps 
			= con.prepareStatement("INSERT INTO devices (name, type, mac, user_id, notes, producer) "+
					"VALUES ('" + item.name + "', '" + item.type + "', '" + item.mac + "', '" + 
					userId + "', '" + item.notes + "', '" + item.producer + "')  "); 
		
		int status =  ps.executeUpdate();  
		
		if(status == 1){		
	        item.setId(list.isEmpty() ? 1 : list.get(list.size() - 1).getId() + 1);
	        list.add(item);
	        item = new Device();
		}

        util.redirectWithGet();
	}
    
    public void resetAdd() {
    	selectedUser = null;
        item = new Device();
        where = null;
        util.redirectWithGet();
    }
    
    public void edit(Device item, UserBean userBean){
        beforeEditItem = item.clone();
        this.item = item;
        selectedUser = userBean.getUser(item.getUser_id());
        edit = true;
        where = null;
        util.redirectWithGet();
    }
    
    public void cancelEdit() {
        selectedUser = null;
        this.item.restore(beforeEditItem);
        this.item = new Device();
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
		
       	PreparedStatement ps;

       	long userId = selectedUser != null? selectedUser.getId() : null;
		
       	ps = con.prepareStatement("UPDATE  devices " +
					"SET name = '" + item.name + "', type = '" + item.type  + "', mac = '" +
					item.mac  + "', user_id = '" + userId + "', notes = '" + item.notes + 
					"' , producer = '" + item.producer + "' WHERE id = " + item.id );
      
		
		int status =  ps.executeUpdate();  
		
		if(status == 1){
	        this.item = new Device();
	        edit = false;
	        where = null;
		}
        util.redirectWithGet();
        selectedUser = null;
    }
    
    public void delete(Device item) throws SQLException {
    	if(con==null){
    		//get database connection
    		con = ds.getConnection();
    		
    		if(con==null)
    			throw new SQLException("Can't get database connection");
    	}
		
		PreparedStatement ps 
			= con.prepareStatement(
				"delete from devices where id = " + item.getId()); 
		
		int status =  ps.executeUpdate();
		if(status == 1){
			list.remove(item);
	        where = null;
		} else {
			
		}
        util.redirectWithGet();
    }

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

    public List<Device> getList() throws SQLException {
        return getDeviceList(this.ownedByUser, this.userId);
    }
    
    public Device getItem() {
        return this.item;
    }
    
    public boolean isEdit() {
        return this.edit;
    }
    
    public void search() throws SQLException {
//    	selectedUser = null;
//    	item.type = null;
    	StringBuilder whereSB = new StringBuilder(" 1=1 ");
    	if(item.getName() != null && !item.getName().equals(""))
    		whereSB.append(" AND name LIKE '%" + item.getName() + "%'");
    	if(item.getType() != null && !item.getType().equals(""))
    		whereSB.append(" AND type LIKE '%" + item.getType() + "%'");
    	if(item.getMac() != null && !item.getMac().equals(""))
    		whereSB.append(" AND mac LIKE '%" + item.getMac() + "%'");
    	if (selectedUser != null)
    		whereSB.append(" AND user_id LIKE '%" + selectedUser.getId() + "%'");
    	if(item.getProducer() != null && !item.getProducer().equals(""))
    		whereSB.append(" AND producer LIKE '%" + item.getProducer() + "%'");
    	
    	where = whereSB.toString();
    	getDeviceList(this.ownedByUser, this.userId);
    }
    public Device getDevice(Long id) {
        if (id == null){
            throw new IllegalArgumentException("no id provided");
        }
        for (Device device : list){
            if (id.equals(device.getId())){
                return device;
            }
        }
        return null;
    }
}
