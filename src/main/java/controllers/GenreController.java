package controllers;

import db.DataHelper;
import java.io.Serializable;
import java.util.ArrayList;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import entity.Genre;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

@RequestScoped
@Named
public class GenreController implements Serializable, Converter {

    private final List<SelectItem> selectItems = new ArrayList<>();
    private final Map<Long, Genre> genreMap;
    private final List<Genre> genreList;

    public GenreController() {

        genreMap = new HashMap<>();
        genreList = DataHelper.getInstance().getAllGenres();
        for (Genre genre : genreList) {
            genreMap.put(genre.getId(), genre);
            selectItems.add(new SelectItem(genre.getId(), genre.getName()));
        }
    }

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

    // 
    public List<Genre> getGenreList() {
        return genreList;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return genreMap.get(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return String.valueOf(((Genre)value).getId());
    }
}
