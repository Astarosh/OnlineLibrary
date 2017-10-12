/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entity.Publisher;
import db.DataHelper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 *
 * @author Ast
 */
@RequestScoped
@Named
public class PublisherController implements Serializable, Converter{

    private final List<SelectItem> selectItems = new ArrayList<>();;
    private final Map<Long,Publisher> publisherNameList;

    public PublisherController() {
        publisherNameList = new HashMap<>();
        List<Publisher> list = DataHelper.getInstance().getAllPublishers();

        for (Publisher publisher : list) {
            publisherNameList.put(publisher.getId(), publisher);
            selectItems.add(new SelectItem(publisher.getId(), publisher.getName()));
        }
    }
    
    public List<SelectItem> getSelectItems() {
        return selectItems;
    }
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return publisherNameList.get(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return String.valueOf(((Publisher)value).getId());
    }
}
