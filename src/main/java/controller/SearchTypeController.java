package controller;

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

    private final Map<String, SearchType> SEARCHLIST = new HashMap<>(); // хранит все виды поисков (по автору, по названию, по ISBN)

    public SearchTypeController() {

        ResourceBundle bundle = ResourceBundle.getBundle("nls.properties", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        SEARCHLIST.clear();
        SEARCHLIST.put(bundle.getString("author_name"), SearchType.AUTHOR);
        SEARCHLIST.put(bundle.getString("book_name"), SearchType.NAME);
        SEARCHLIST.put("ISBN", SearchType.ISBN);
    }

    public Map<String, SearchType> getSEARCHLIST() {
        return SEARCHLIST;
    }
}
