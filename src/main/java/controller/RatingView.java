package controller;

import db.DataHelper;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.primefaces.event.RateEvent;

@Named
public class RatingView {

    @ManagedProperty(value = "#{searchController}")
    private SearchController searchController;

    public SearchController getSearchController() {
        return searchController;
    }

    public void setSearchController(SearchController searchController) {
        this.searchController = searchController;
    }
    private Integer rating;
    private Long bookId;
    private Long voteCount;
    private Integer bookRating;

    public Integer getBookRating() {
        return bookRating;
    }

    public void setBookRating(Integer bookRating) {
        this.bookRating = bookRating;
    }

    public Long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public void onrate(RateEvent rateEvent) {
        ResourceBundle bundle = ResourceBundle.getBundle("nls.properties", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        bookId = searchController.getBookIdForRating();
        voteCount = searchController.getBookVoteCountForRating();
        rating = (Integer) rateEvent.getRating();
        bookRating = searchController.getBookRating();
        Integer totalRating = Math.round((voteCount*bookRating+rating)/(voteCount+1));
        DataHelper.getInstance().updateRating(bookId, totalRating, voteCount+1);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("thank"), bundle.getString("you_rated")+ ": " + rating);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
