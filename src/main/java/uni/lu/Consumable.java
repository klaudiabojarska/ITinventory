package uni.lu;

import java.io.Serializable;
import java.util.Date;

public class Consumable implements Serializable{
 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public long id;
	public String name;
	public Integer stock;
	public long device_id;
	public String device_name;
	public Date time_create;
	
	public Consumable(long id, String name, int stock, long device_id, String device_name, Date time_create) {
		super();
		this.id = id;
		this.name = name;
		this.stock = stock;
		this.device_id = device_id;
		this.device_name = device_name;
		this.time_create = time_create;
	}
	
	public Consumable() {
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

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public long getDevice_id() {
		return device_id;
	}

	public void setDevice_id(long device_id) {
		this.device_id = device_id;
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
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
    public Consumable clone() {
        return new Consumable(id, name, stock, device_id, device_name, time_create);
    }
    
    public void restore(Consumable device) {
        this.id = device.getId();
        this.name = device.getName();
        this.device_id = device.getDevice_id();
        this.device_name = device.getDevice_name();
        this.stock = device.getStock();
        this.time_create = device.getTime_create();
        
    }
	
}