package uni.lu;

import java.io.Serializable;
import java.util.Date;

public class Device implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public Device(long id, String name, String type, String mac, long user_id, String owner, 
			String notes, String producer, Date time_create) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.mac = mac;
		this.user_id = user_id;
		this.owner = owner;
		this.notes = notes;
		this.producer = producer;
		this.time_create = time_create;
	}
	public Device(){
		super();
	}
	
	public long id;
	public String name;
	public String type;
	public String mac;
	public long user_id;
	private String owner;
	public String notes;
	public String producer;
	public Date time_create;
	
	
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public long getUser_id() {
		return user_id;
	}
	public String getOwner() {
		return owner;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public Date getTime_create() {
		return time_create;
	}
	public void setTime_create(Date time_create) {
		this.time_create = time_create;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
    public Device clone() {
        return new Device(id, name, type, mac, user_id, owner, notes, producer, time_create);
    }
	
	@Override
	public boolean equals(Object o) {
	    Device d = (Device) o;
	    if(this.id == d.id)
	    	return true;
    	else
    		return false;
	}
    
    public void restore(Device device) {
        this.id = device.getId();
        this.name = device.getName();
        this.type = device.getType();
        this.mac = device.getMac();
        this.user_id = device.getUser_id();
        this.owner = device.getOwner();
        this.notes = device.getNotes();
        this.producer = device.getProducer();
        this.time_create = device.getTime_create();
        
    }
}