package controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import db.DataHelper;
import entity.Author;
import javax.enterprise.context.RequestScoped;

@RequestScoped
@Named
public class AuthorController implements Serializable, Converter {

    private final List<SelectItem> selectItems = new ArrayList<>();
    private final Map<Long,Author> authorMap;

    public AuthorController() {
        authorMap = new HashMap<>();
        
        for (Author author : DataHelper.getInstance().getAllAuthors()) {
            authorMap.put(author.getId(), author);
            selectItems.add(new SelectItem(author.getId(), author.getFio()));
        }
    }

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return authorMap.get(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        System.out.println(value);
        return String.valueOf(((Author)value).getId());
    }

    

}
