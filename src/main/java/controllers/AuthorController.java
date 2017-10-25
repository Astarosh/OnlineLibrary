package controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.faces.model.SelectItem;
import db.DataHelper;
import entity.Author;
import javax.enterprise.context.RequestScoped;

@RequestScoped
@Named
public class AuthorController implements Serializable {

    private final List<SelectItem> selectItems = new ArrayList<>();
    public AuthorController() {
        
        for (Author author : DataHelper.getInstance().getAllAuthors()) {
            selectItems.add(new SelectItem(author.getId(), author.getFio()));
        }
    }

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

    

}
