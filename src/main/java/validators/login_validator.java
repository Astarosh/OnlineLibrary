/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Ast
 */
@FacesValidator("validators.login_validator")
public class login_validator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        FacesMessage message;
        ResourceBundle bundle = ResourceBundle.getBundle("nls.properties", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        char c;
        String str = value.toString().trim();
        try{
            if(str.length() == 0){
                throw new IllegalArgumentException(bundle.getString("login_required"));
        } else{
            c = str.charAt(0);
        }
        if(!Character.isLetter(c)){
            throw new IllegalArgumentException(bundle.getString("login_firstletter_error"));
        } else if(str.length() <= 4 || str.length() >= 20){
            throw new IllegalArgumentException(bundle.getString("login_length_error"));
        } else if(str.equals("username") || str.equals("login")){
            throw new IllegalArgumentException(bundle.getString("login_bindingwords_error"));
        }
        } catch (IllegalArgumentException ex){
            message = new FacesMessage(ex.getMessage());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
    
}
