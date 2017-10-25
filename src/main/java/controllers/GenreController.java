package controllers;

import db.DataHelper;
import java.io.Serializable;
import java.util.ArrayList;
import javax.inject.Named;
import entity.Genre;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;

@RequestScoped
@Named
public class GenreController implements Serializable {

    private final List<SelectItem> selectItems = new ArrayList<>();
    private final List<Genre> genreList;

    public GenreController() {

        genreList = DataHelper.getInstance().getAllGenres();
        for (Genre genre : genreList) {
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
    
}
