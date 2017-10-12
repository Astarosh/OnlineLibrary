package controllers;

import enums.SearchType;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

@RequestScoped
@Named
public class SearchTypeController implements Serializable {

    private final Map<String, SearchType> searchList = new HashMap<>(); // хранит все виды поисков (по автору, по названию)

    public SearchTypeController() {

        ResourceBundle bundle = ResourceBundle.getBundle("nls.properties", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        searchList.clear();
        searchList.put(bundle.getString("author_name"), SearchType.AUTHOR);
        searchList.put(bundle.getString("book_name"), SearchType.NAME);
        searchList.put("ISBN", SearchType.ISBN);
    }

    public Map<String, SearchType> getSearchList() {
        return searchList;
    }
}
