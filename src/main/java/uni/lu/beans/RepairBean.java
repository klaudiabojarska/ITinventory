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
import uni.lu.Repair;
import uni.lu.User;


@ManagedBean(name="repair")
@SessionScoped
public class RepairBean implements Serializable{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//resource injection
	@Resource(name="jdbc/webprog2")
	private DataSource ds;

	private Device selectedDevice;
	private User selectedRepairer;
	private User selectedOwner;

	private List<Repair> list;
    private Repair item = new Repair();
    private Repair beforeEditItem = null;
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
        list = new ArrayList<Repair>(); 
	}
    
	public List<Repair> getRepairList(boolean ownedByUser, long userId) throws SQLException{
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
				"select * from repairs r join users repairer on r.repairer_id = repairer.id "
				+ "join devices d on r.device_id = d.id "
				+ "join users owner on d.user_id = owner.id WHERE 1=1 " 
				+ (where!=null && !where.equals("")? where : "")
				+ (ownedByUser? " AND d.user_id = '" + userId + "' " : " "));  
		
		ResultSet result =  ps.executeQuery();

		list.clear();
		
		while(result.next()){
			Repair entity = new Repair();
			
			entity.setId(result.getLong("id"));
			entity.setName(result.getString("name"));
			entity.setDevice_id(result.getLong("device_id"));
			entity.setRepairer_id(result.getLong("repairer_id"));/*
			entity.setOwner_id(result.getLong("owner_id"));*/
			entity.setDescription(result.getString("description"));/*
			entity.setRepair_date(result.getDate("repair_date"));*/
			entity.setTime_create(result.getDate("time_create"));
			entity.setDevice_name(result.getString("d.name"));
			entity.setOwner(result.getString("owner.first_name") + " " 
							+ result.getString("owner.last_name"));
			entity.setRepairer(result.getString("repairer.first_name") + " " 
							+ result.getString("repairer.last_name"));
			
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
       	long repairerId = selectedRepairer != null? selectedRepairer.getId() : null;
		PreparedStatement ps 
			= con.prepareStatement("INSERT INTO repairs (name, device_id, repairer_id, description "
					+ ") VALUES (' " + item.name + " ', ' " 
					+ deviceId + " ', ' " + repairerId + " ', ' " + item.description 
					+ " ')  "); 
		
		int status =  ps.executeUpdate();  
		
		if(status == 1){		
	        item.setId(list.isEmpty() ? 1 : list.get(list.size() - 1).getId() + 1);
	        list.add(item);
	        item = new Repair();
		}

        util.redirectWithGet();
	}
    
    public void resetAdd() {
        item = new Repair();
        where = null;
        util.redirectWithGet();
    }
    
    public void edit(Repair item, UserBean userBean, DeviceBean deviceBean){
        beforeEditItem = item.clone();
        this.item = item;
        selectedRepairer = userBean.getUser(item.getRepairer_id());
        selectedOwner = userBean.getUser(item.getOwner_id());
        selectedDevice = deviceBean.getDevice(item.getDevice_id());
        edit = true;
        where = null;
        util.redirectWithGet();
    }
    
    public void cancelEdit() {
    	selectedRepairer = null;
    	selectedOwner = null;
    	selectedDevice = null;
        this.item.restore(beforeEditItem);
        this.item = new Repair();
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

       	long deviceId = selectedDevice != null? selectedDevice.getId() : null;
       	long repairerId = selectedRepairer != null? selectedRepairer.getId() : null;
		
       	PreparedStatement ps;
       	ps = con.prepareStatement("UPDATE  repairs " +
					"SET name = '" + item.name + "', device_id = '" + deviceId  + "', repairer_id = '" 
					+ repairerId  + "', description = '" + item.description + /*"', repair_date = '" 
					+ item.repair_date +*/ "' WHERE id = " + item.id );
      
		
		int status =  ps.executeUpdate();  
		
		if(status == 1){
	        this.item = new Repair();
	        edit = false;
	        where = null;
		}
        util.redirectWithGet();
    }
    
    public void delete(Repair item) throws SQLException {
    	if(con==null){
    		//get database connection
    		con = ds.getConnection();
    		
    		if(con==null)
    			throw new SQLException("Can't get database connection");
    	}
		
		PreparedStatement ps 
			= con.prepareStatement(
				"delete from repairs where id = " + item.getId()); 
		
		int status =  ps.executeUpdate();
		if(status == 1){
			list.remove(item);
	        where = null;
		} else {
			
		}
        util.redirectWithGet();
    }
    
   
    public List<Repair> getList() throws SQLException {
        return getRepairList(this.ownedByUser, this.userId);
    }
    
    public Repair getItem() {
        return this.item;
    }
    
    public boolean isEdit() {
        return this.edit;
    }
    
    
    public void search() throws SQLException {
    	StringBuilder whereSB = new StringBuilder("");
    	if(item.getName() != null && !item.getName().equals(""))
    		whereSB.append(" AND r.name LIKE '%" + item.getName() + "%'");
    	if(selectedDevice != null )
    		whereSB.append(" AND device_id LIKE '%" + item.getDevice_id() + "%'");
    	if(selectedRepairer != null )
    		whereSB.append(" AND repairer_id LIKE '%" + selectedRepairer.getId() + "%'");
    	if(selectedOwner != null )
    		whereSB.append(" AND owner.id LIKE '%" + selectedOwner.getId() + "%'");
    	if(item.getDescription() != null && !item.getDescription().equals(""))
    		whereSB.append(" AND description LIKE '%" + item.getDescription() + "%'");
    	/*if(item.getRepair_date() != null && !item.getRepair_date().equals(""))
    		whereSB.append(" AND repair_date LIKE '%" + item.getRepair_date() + "%'");*/
    	
    	where = whereSB.toString();
    	getRepairList(this.ownedByUser, this.userId);
    }

	public Device getSelectedDevice() {
		return selectedDevice;
	}

	public void setSelectedDevice(Device selectedDevice) {
		this.selectedDevice = selectedDevice;
	}
	
	public User getSelectedRepairer() {
		return selectedRepairer;
	}

	public void setSelectedRepairer(User selectedRepairer) {
		this.selectedRepairer = selectedRepairer;
	}
	public Repair getRepair(Long id) {
        if (id == null){
            throw new IllegalArgumentException("no id provided");
        }
        for (Repair repair : list){
            if (id.equals(repair.getId())){
                return repair;
            }
        }
        return null;
    }

	public User getSelectedOwner() {
		return selectedOwner;
	}

	public void setSelectedOwner(User selectedOwner) {
		this.selectedOwner = selectedOwner;
	}
	
}
    

