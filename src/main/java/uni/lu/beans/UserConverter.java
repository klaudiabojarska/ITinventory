package uni.lu.beans;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import uni.lu.User;
import uni.lu.beans.UserBean;

@FacesConverter(value = "userConverter")
public class UserConverter implements Converter {

	    //public static List<User> users;
	 
//	    @Override
//	    public Object getAsObject(FacesContext context, UIComponent component, String value) {
//	        return user.getById(Long.valueOf(value));
//	    }
	    @Override
	    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String userId) {
	        ValueExpression vex =
	                ctx.getApplication().getExpressionFactory()
	                        .createValueExpression(ctx.getELContext(),
	                                "#{user}", UserBean.class);

	        UserBean users = (UserBean)vex.getValue(ctx.getELContext());
	        return users.getUser(Long.valueOf(userId));
	    }
	    @Override
	    public String getAsString(FacesContext context, UIComponent component, Object value) {
	        if(value != null)
	        	return String.valueOf(((User) value).getId());
	        else
	        	return null;
	    }
}
