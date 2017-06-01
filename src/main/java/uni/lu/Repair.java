package uni.lu;

import java.io.Serializable;
import java.util.Date;

public class Repair implements Serializable{
 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public long id;
	public String name;
	public long device_id;
	public long repairer_id;
	public long owner_id;
	public String description;
	public Date repair_date;
	public Date time_create;
	public String device_name;
	public String owner;
	public String repairer;
		
	

	public Repair(long id, String name, long device_id, long repairer_id, String description, Date repair_date,
			Date time_create, String device_name, String owner, String repairer) {
		super();
		this.id = id;
		this.name = name;
		this.device_id = device_id;
		this.repairer_id = repairer_id;
		this.description = description;
		this.repair_date = repair_date;
		this.time_create = time_create;
		this.device_name = device_name;
		this.owner = owner;
		this.repairer = repairer;
	}
	
	public Repair(){
		super();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getDevice_id() {
		return device_id;
	}

	public void setDevice_id(long device_id) {
		this.device_id = device_id;
	}

	public long getRepairer_id() {
		return repairer_id;
	}

	public void setRepairer_id(long repairer_id) {
		this.repairer_id = repairer_id;
	}
	
	public long getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(long owner_id) {
		this.owner_id = owner_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getRepair_date() {
		return repair_date;
	}

	public void setRepair_date(Date repair_date) {
		this.repair_date = repair_date;
	}

	public Date getTime_create() {
		return time_create;
	}

	public void setTime_create(Date time_create) {
		this.time_create = time_create;
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getRepairer() {
		return repairer;
	}

	public void setRepairer(String repairer) {
		this.repairer = repairer;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
    public Repair clone() {
        return new Repair(id, name, device_id, repairer_id, description, repair_date, time_create, 
        		device_name, owner, repairer);
    }
    
    public void restore(Repair device) {
        this.id = device.getId();
        this.name = device.getName();
        this.device_id = device.getDevice_id();
        this.repairer_id = device.getRepairer_id();
        this.description = device.getDescription();
        this.repair_date = device.getRepair_date();
        this.time_create = device.getTime_create();
        this.device_name = device.getDevice_name();
        this.owner = device.getOwner();
        this.repairer = device.getRepairer();
        
    }
	
}