package controller;

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

    private final List<SelectItem> SELECTITEMS = new ArrayList<>();
    private final List<Genre> GENRELIST;

    public GenreController() {

        GENRELIST = DataHelper.getInstance().getAllGenres();
        for (Genre genre : GENRELIST) {
            SELECTITEMS.add(new SelectItem(genre.getId(), genre.getName()));
        }
    }

    public List<SelectItem> getSELECTITEMS() {
        return SELECTITEMS;
    }

    // 
    public List<Genre> getGENRELIST() {
        return GENRELIST;
    }
    
}
