package uni.lu;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import uni.lu.LoginDAO;
import uni.lu.SessionUtils;

@ManagedBean
@SessionScoped
public class Login implements Serializable {

	private static final long serialVersionUID = 1094801825228386363L;

	private String user;
	private String pwd;
	private Long userId;
	private Group group;
	private String msg;

	

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long i) {
		this.userId = i;
	}

	

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


	//validate login
	public String validateUsernamePassword() {
		Login valid = LoginDAO.validate(user, pwd);
		if (valid!=null) {
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("username", valid.user);
			session.setAttribute("userid", valid.userId);
			session.setAttribute("usergroup", String.valueOf(valid.group.id));
			session.setAttribute("canCreateUser", String.valueOf(valid.group.isCanCreateUser()));
			session.setAttribute("canReadUser", String.valueOf(valid.group.isCanReadUser()));
			session.setAttribute("canEditUser", String.valueOf(valid.group.isCanEditUser()));
			session.setAttribute("canCreateGroup", String.valueOf(valid.group.isCanCreateGroup()));
			session.setAttribute("canReadGroup", String.valueOf(valid.group.isCanReadGroup()));
			session.setAttribute("canEditGroup", String.valueOf(valid.group.isCanEditGroup()));
			session.setAttribute("canCreateDevice", String.valueOf(valid.group.isCanCreateDevice()));
			session.setAttribute("canReadDevice", String.valueOf(valid.group.isCanReadDevice()));
			session.setAttribute("canEditDevice", String.valueOf(valid.group.isCanEditDevice()));
			session.setAttribute("canCreateConsumables", String.valueOf(valid.group.isCanCreateConsumables()));
			session.setAttribute("canReadConsumables", String.valueOf(valid.group.isCanReadConsumables()));
			session.setAttribute("canEditConsumables", String.valueOf(valid.group.isCanEditConsumables()));
			session.setAttribute("canCreateRepair", String.valueOf(valid.group.isCanCreateRepair()));
			session.setAttribute("canReadRepair", String.valueOf(valid.group.isCanReadRepair()));
			session.setAttribute("canEditRepair", String.valueOf(valid.group.isCanEditRepair()));
			session.setAttribute("ownedByUser", String.valueOf(valid.group.isOwnedByUser()));
					
			
			return "home";
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Incorrect Username and Password",
							"Please enter correct username and Password"));
			return "login";
		}
	}

	//logout event, invalidate session
	public String logout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		return "login";
	}

}