package validator;

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validators.string_validator")
public class String_validator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        FacesMessage message;
        ResourceBundle bundle = ResourceBundle.getBundle("nls.properties", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        try {
            String str;
            if (value == null) {
                throw new IllegalArgumentException(bundle.getString("enter_concern"));
            } else if(value instanceof Number){
                throw new IllegalArgumentException(bundle.getString("concern_mustnot_contains_numbers"));
            }
            str = value.toString().trim();
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
