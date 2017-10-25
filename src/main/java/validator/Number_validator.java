package validator;

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validators.number_validator")
public class Number_validator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        FacesMessage message;
        ResourceBundle bundle = ResourceBundle.getBundle("nls.properties", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        try {
            char c;
            String str;
            if (value == null) {
                throw new IllegalArgumentException(bundle.getString("enter_concern"));
            }
            str = value.toString().trim();
            for(int i = 0; i< str.length(); i++){
                c = str.charAt(i);
                if(!Character.isDigit(c)){
                    throw new IllegalArgumentException(bundle.getString("concern_mustnot_contains_strings"));
                }
            }
            if (str.length() == 0) {
                throw new IllegalArgumentException(bundle.getString("enter_concern"));
            }
        } catch (IllegalArgumentException ex) {
            message = new FacesMessage(ex.getMessage());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
}
