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
import uni.lu.Consumable;
import uni.lu.Device;

@ManagedBean(name="consumable")
@SessionScoped
public class ConsumableBean implements Serializable{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//resource injection
	@Resource(name="jdbc/webprog2")
	private DataSource ds;
	
	private Device selectedDevice;	
	private List<Consumable> list;
    private Consumable item = new Consumable();
    private Consumable beforeEditItem = null;
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
        list = new ArrayList<Consumable>();
    }

	public List<Consumable> getConsumableList(boolean ownedByUser, long userId) throws SQLException{
		this.setOwnedByUser(ownedByUser);
		this.setUserId(userId);
		
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
				"select * from consumables c left join devices d on c.device_id = d.id WHERE 1=1 "
				+ (where!=null && !where.equals("")? where : "" ) 
				+ (ownedByUser? " AND d.user_id = '" + userId + "' " : ""));  
		
		ResultSet result =  ps.executeQuery();

		list.clear();
		
		while(result.next()){
			Consumable entity = new Consumable();
			
			entity.setId(result.getLong("id"));
			entity.setName(result.getString("c.name"));
			entity.setDevice_id(result.getLong("device_id"));
			entity.setDevice_name(result.getString("d.name"));
			entity.setStock(result.getInt("stock"));
			entity.setTime_create(result.getDate("time_create"));
			
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

       	long deviceId = selectedDevice != null? selectedDevice.getId() : null;
       	
		PreparedStatement ps 
			= con.prepareStatement("INSERT INTO consumables (name, device_id, stock) VALUES (' " + item.name 
					+ " ', ' " + deviceId + " ', ' " + item.stock + " ')  "); 
		
		int status =  ps.executeUpdate();  
		
		if(status == 1){		
	        item.setId(list.isEmpty() ? 1 : list.get(list.size() - 1).getId() + 1);
	        list.add(item);
	        item = new Consumable();
		}

        util.redirectWithGet();
	}
    
    public void resetAdd() {
        item = new Consumable();
        where = null;
        util.redirectWithGet();
    }
    
    public void edit(Consumable item, DeviceBean deviceBean){
        beforeEditItem = item.clone();
        this.item = item;
        selectedDevice = deviceBean.getDevice(item.getDevice_id());
        edit = true;
        where = null;
        util.redirectWithGet();
    }
    
    public void cancelEdit() {
    	selectedDevice = null;
        this.item.restore(beforeEditItem);
        this.item = new Consumable();
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
       	
       	Long deviceId = null;
       	if(selectedDevice != null) {
       		deviceId = selectedDevice.getId();

    		ps = con.prepareStatement("UPDATE  consumables " +
    					"SET name = '" + item.name + "', device_id = '" + deviceId  + "', stock = '" 
    					+ item.stock  + "' WHERE id = " + item.id );
       	} else {
			ps = con.prepareStatement("UPDATE  consumables " +
						"SET name = '" + item.name + "', stock = '" 
						+ item.stock  + "' WHERE id = " + item.id );
       	}
		
      
		
		int status =  ps.executeUpdate();  
		
		if(status == 1){
	        this.item = new Consumable();
	        edit = false;
	        where = null;
		}
        util.redirectWithGet();
    }
    
    public void delete(Consumable item) throws SQLException {
    	if(con==null){
    		//get database connection
    		con = ds.getConnection();
    		
    		if(con==null)
    			throw new SQLException("Can't get database connection");
    	}
		
		PreparedStatement ps 
			= con.prepareStatement(
				"delete from consumables where id = " + item.getId()); 
		
		int status =  ps.executeUpdate();
		if(status == 1){
			list.remove(item);
	        where = null;
		} else {
			
		}
        util.redirectWithGet();
    }

	public Device getSelectedDevice() {
		return selectedDevice;
	}

	public void setSelectedDevice(Device selectedDevice) {
		this.selectedDevice = selectedDevice;
	}
    public List<Consumable> getList() throws SQLException {
        return getConsumableList(this.ownedByUser, this.userId);
    }
    
    public Consumable getItem() {
        return this.item;
    }
    
    public boolean isEdit() {
        return this.edit;
    }
    
    
    public void search() throws SQLException {
    	StringBuilder whereSB = new StringBuilder();
    	if(item.getName() != null && !item.getName().equals(""))
    		whereSB.append(" AND c.name LIKE '%" + item.getName() + "%'");
    	if(selectedDevice != null )
    		whereSB.append(" AND c.device_id LIKE '%" + selectedDevice.getId() + "%'");
    	if(item.getStock() != null )
    		whereSB.append(" AND stock =" + item.getStock());
    	
    	where = whereSB.toString();
    	getConsumableList(this.ownedByUser, this.userId);
    }
    
    public Consumable getConsumable(Long id) {
        if (id == null){
            throw new IllegalArgumentException("no id provided");
        }
        for (Consumable consumable : list){
            if (id.equals(consumable.getId())){
                return consumable;
            }
        }
        return null;
    }

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public boolean isOwnedByUser() {
		return ownedByUser;
	}

	public void setOwnedByUser(boolean ownedByUser) {
		this.ownedByUser = ownedByUser;
	}
}
    

