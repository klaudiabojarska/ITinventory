package uni.lu.beans;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import uni.lu.Group;

@FacesConverter(value = "groupConverter")
public class GroupConverter implements Converter {

	    //public static List<User> users;
	 
//	    @Override
//	    public Object getAsObject(FacesContext context, UIComponent component, String value) {
//	        return user.getById(Long.valueOf(value));
//	    }
	    @Override
	    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String groupId) {
	        ValueExpression vex =
	                ctx.getApplication().getExpressionFactory()
	                        .createValueExpression(ctx.getELContext(),
	                                "#{group}", GroupBean.class);

	        GroupBean group = (GroupBean)vex.getValue(ctx.getELContext());
	        return group.getGroup(Long.valueOf(groupId));
	    }
	    @Override
	    public String getAsString(FacesContext context, UIComponent component, Object value) {
	        if(value != null)
	        	return String.valueOf(((Group) value).getId());
	        else
	        	return null;
	    }
}
