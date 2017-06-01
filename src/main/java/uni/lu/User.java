package uni.lu;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{

	
	public long id;
	public String login;
	public String group_name;
	public String f_name;
	public String l_name;
	public String address;
	public String password;
	public Long group_id;
	public Date time_create;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public User(long id, String login, String f_name, String l_name, String address, String password, Long group_id,
			Date time_create) {
		super();
		this.id = id;
		this.login = login;
		this.f_name = f_name;
		this.l_name = l_name;
		this.address = address;
		this.password = password;
		this.group_id = group_id;
		this.time_create = time_create;
	}
	
	public User(){
		super();
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getF_name() {
		return f_name;
	}
	public void setF_name(String f_name) {
		this.f_name = f_name;
	}
	public String getL_name() {
		return l_name;
	}
	public void setL_name(String l_name) {
		this.l_name = l_name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	
	public Date getTime_create() {
		return time_create;
	}
	public void setTime_create(Date time_create) {
		this.time_create = time_create;
	}
	
	@Override
    public User clone() {
        return new User(id, login,  f_name,  l_name,  address,  password, group_id, time_create);
    }
	@Override
	public boolean equals(Object o) {
	    User u = (User) o;
	    if(this.id == u.id)
	    	return true;
    	else
    		return false;
	}
    public void restore(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.f_name = user.getF_name();
        this.l_name = user.getL_name();
        this.address = user.getAddress();
        this.password = user.getPassword();
        this.group_id = user.getGroup_id();
        this.time_create = user.getTime_create();
        
    }
}