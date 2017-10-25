package validators;

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

@FacesValidator("validators.imageUpload_validator")
public class imageUpload_validator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        FacesMessage message;
        ResourceBundle bundle = ResourceBundle.getBundle("nls.properties", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        try {
            if(value == null){
                throw new IllegalArgumentException(bundle.getString("upload_file"));
            }
            Part file = (Part) value;
            if (file.getSize() > 102400) {
                System.out.println(file.getSize());
                throw new IllegalArgumentException(bundle.getString("file_too_big"));
            }
        } catch (IllegalArgumentException ex) {
            message = new FacesMessage(ex.getMessage());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }

}
