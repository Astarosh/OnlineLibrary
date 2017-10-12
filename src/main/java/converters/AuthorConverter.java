package converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import controllers.AuthorController;
import db.DataHelper;

public class AuthorConverter implements Converter {

    private static AuthorController authorController;

    public AuthorConverter() {
        authorController = (AuthorController) FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("authorController");
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return DataHelper.getInstance().getAuthor(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        System.out.println(value);
        return "";//((Author)value).getFio();
    }
}
