package validators;

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator("validators.password_validator")
public class password_validator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        FacesMessage message;
        ResourceBundle bundle = ResourceBundle.getBundle("nls.properties", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String str = value.toString().trim();
        try {
            if (str.length() == 0) {
                throw new IllegalArgumentException(bundle.getString("password_required"));
            } else if (str.length() < 6 || str.length() >= 20) {
                throw new IllegalArgumentException(bundle.getString("password_length_error"));
            } else if (str.equals("username") || str.equals("login")) {
                throw new IllegalArgumentException(bundle.getString("login_bindingwords_error"));
            }
        } catch (IllegalArgumentException ex) {
            message = new FacesMessage(ex.getMessage());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }

}
