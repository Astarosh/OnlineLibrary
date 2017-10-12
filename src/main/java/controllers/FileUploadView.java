/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

/**
 *
 * @author Ast
 */

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
 
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
 
@ManagedBean
public class FileUploadView {
    @ManagedProperty(value = "#{searchController}")
    private SearchController searchController;
    private byte[] image;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public SearchController getSearchController() {
        return searchController;
    }

    public void setSearchController(SearchController searchController) {
        this.searchController = searchController;
    }
    public void handleFileUpload(FileUploadEvent event) {
        ResourceBundle bundle = ResourceBundle.getBundle("nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        image = event.getFile().getContents();
        searchController.getCurrentBookList().get(0).setImage(image);
        searchController.getCurrentBookList().get(0).setImageEdited(true);
        FacesMessage message = new FacesMessage(bundle.getString("successfull"), event.getFile().getFileName() + " " + bundle.getString("uploaded"));
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
