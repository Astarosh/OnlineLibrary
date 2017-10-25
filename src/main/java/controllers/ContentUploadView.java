package controllers;

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;

@ManagedBean
public class ContentUploadView {

    @ManagedProperty(value = "#{searchController}")
    private SearchController searchController;
    private byte[] content;

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public SearchController getSearchController() {
        return searchController;
    }

    public void setSearchController(SearchController searchController) {
        this.searchController = searchController;
    }

    public void handleFileUpload(FileUploadEvent event) {
        ResourceBundle bundle = ResourceBundle.getBundle("nls.properties", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        content = event.getFile().getContents();
        searchController.getCurrentBookList().get(0).setContent(content);
        searchController.getCurrentBookList().get(0).setContentEdited(true);
        FacesMessage message = new FacesMessage(bundle.getString("successfull"), event.getFile().getFileName() + " " + bundle.getString("uploaded"));
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
