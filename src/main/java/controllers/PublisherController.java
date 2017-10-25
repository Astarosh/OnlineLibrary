package controllers;

import entity.Publisher;
import db.DataHelper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

@RequestScoped
@Named
public class PublisherController implements Serializable{

    private final List<SelectItem> selectItems = new ArrayList<>();;

    public PublisherController() {
        List<Publisher> list = DataHelper.getInstance().getAllPublishers();

        for (Publisher publisher : list) {
            selectItems.add(new SelectItem(publisher.getId(), publisher.getName()));
        }
    }
    
    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

}
