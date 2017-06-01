package uni.lu.beans;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import uni.lu.Repair;
import uni.lu.beans.RepairBean;

@FacesConverter(value = "repairConverter")
public class RepairConverter implements Converter {

	    //public static List<Repair> repairs;
	 
//	    @Override
//	    public Object getAsObject(FacesContext context, UIComponent component, String value) {
//	        return repair.getById(Long.valueOf(value));
//	    }
	    @Override
	    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String repairId) {
	        ValueExpression vex =
	                ctx.getApplication().getExpressionFactory()
	                        .createValueExpression(ctx.getELContext(),
	                                "#{repair}", RepairBean.class);

	        RepairBean repairs = (RepairBean)vex.getValue(ctx.getELContext());
	        return repairs.getRepair(Long.valueOf(repairId));
	    }
	    @Override
	    public String getAsString(FacesContext context, UIComponent component, Object value) {
	        if(value != null)
	        	return String.valueOf(((Repair) value).getId());
	        else
	        	return null;
	    }
}
