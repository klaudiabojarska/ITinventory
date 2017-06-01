package uni.lu.beans;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import uni.lu.Device;
import uni.lu.beans.DeviceBean;

@FacesConverter(value = "deviceConverter")
public class DeviceConverter implements Converter {

	    //public static List<User> users;
	 
//	    @Override
//	    public Object getAsObject(FacesContext context, UIComponent component, String value) {
//	        return user.getById(Long.valueOf(value));
//	    }
	    @Override
	    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String deviceId) {
	        ValueExpression vex =
	                ctx.getApplication().getExpressionFactory()
	                        .createValueExpression(ctx.getELContext(),
	                                "#{device}", DeviceBean.class);

	        DeviceBean devices = (DeviceBean)vex.getValue(ctx.getELContext());
	        return devices.getDevice(Long.valueOf(deviceId));
	    }
	    @Override
	    public String getAsString(FacesContext context, UIComponent component, Object value) {
	        if(value != null)
	        	return String.valueOf(((Device) value).getId());
	        else
	        	return null;
	    }
}
