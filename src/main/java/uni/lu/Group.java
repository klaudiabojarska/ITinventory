package uni.lu;

import java.io.Serializable;
import java.util.Date;

public class Group implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public Group(long id, String name, Date time_create) {
		super();
		this.id = id;
		this.name = name;
		this.time_create = time_create;
	}
	public Group(){
		super();
	}
	
	public long id;
	public String name;
	public Date time_create;
	private boolean canCreateUser;
	private boolean canReadUser;
	private boolean canEditUser;

	private boolean canCreateGroup;
	private boolean canReadGroup;
	private boolean canEditGroup;

	private boolean canCreateDevice;
	private boolean canReadDevice;
	private boolean canEditDevice;

	private boolean canCreateConsumables;
	private boolean canReadConsumables;
	private boolean canEditConsumables;

	private boolean canCreateRepair;
	private boolean canReadRepair;
	private boolean canEditRepair;
	
	private boolean ownedByUser;
	
	
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
	public Date getTime_create() {
		return time_create;
	}
	public void setTime_create(Date time_create) {
		this.time_create = time_create;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	 
		public boolean isCanCreateUser() {
			return canCreateUser;
		}

		public void setCanCreateUser(boolean canCreateUser) {
			this.canCreateUser = canCreateUser;
		}

		public boolean isCanReadUser() {
			return canReadUser;
		}

		public void setCanReadUser(boolean canReadUser) {
			this.canReadUser = canReadUser;
		}

		public boolean isCanEditUser() {
			return canEditUser;
		}

		public void setCanEditUser(boolean canEditUser) {
			this.canEditUser = canEditUser;
		}

		public boolean isCanCreateGroup() {
			return canCreateGroup;
		}

		public void setCanCreateGroup(boolean canCreateGroup) {
			this.canCreateGroup = canCreateGroup;
		}

		public boolean isCanReadGroup() {
			return canReadGroup;
		}

		public void setCanReadGroup(boolean canReadGroup) {
			this.canReadGroup = canReadGroup;
		}

		public boolean isCanEditGroup() {
			return canEditGroup;
		}

		public void setCanEditGroup(boolean canEditGroup) {
			this.canEditGroup = canEditGroup;
		}

		public boolean isCanCreateDevice() {
			return canCreateDevice;
		}

		public void setCanCreateDevice(boolean canCreateDevice) {
			this.canCreateDevice = canCreateDevice;
		}

		public boolean isCanReadDevice() {
			return canReadDevice;
		}

		public void setCanReadDevice(boolean canReadDevice) {
			this.canReadDevice = canReadDevice;
		}

		public boolean isCanEditDevice() {
			return canEditDevice;
		}

		public void setCanEditDevice(boolean canEditDevice) {
			this.canEditDevice = canEditDevice;
		}

		public boolean isCanCreateConsumables() {
			return canCreateConsumables;
		}

		public void setCanCreateConsumables(boolean canCreateConsumables) {
			this.canCreateConsumables = canCreateConsumables;
		}

		public boolean isCanReadConsumables() {
			return canReadConsumables;
		}

		public void setCanReadConsumables(boolean canReadConsumables) {
			this.canReadConsumables = canReadConsumables;
		}

		public boolean isCanEditConsumables() {
			return canEditConsumables;
		}

		public void setCanEditConsumables(boolean canEditConsumables) {
			this.canEditConsumables = canEditConsumables;
		}

		public boolean isCanCreateRepair() {
			return canCreateRepair;
		}

		public void setCanCreateRepair(boolean canCreateRepair) {
			this.canCreateRepair = canCreateRepair;
		}

		public boolean isCanReadRepair() {
			return canReadRepair;
		}

		public void setCanReadRepair(boolean canReadRepair) {
			this.canReadRepair = canReadRepair;
		}

		public boolean isCanEditRepair() {
			return canEditRepair;
		}

		public void setCanEditRepair(boolean canEditRepair) {
			this.canEditRepair = canEditRepair;
		}

		public boolean isOwnedByUser() {
			return ownedByUser;
		}

		public void setOwnedByUser(boolean ownedByUser) {
			this.ownedByUser = ownedByUser;
		}
		
	@Override
    public Group clone() {
        return new Group(id, name, time_create);
    }

	@Override
	public boolean equals(Object o) {
	    Group g = (Group) o;
	    if(this.id == g.id)
	    	return true;
    	else
    		return false;
	}
    
    public void restore(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.time_create = group.getTime_create();
        
    }


}