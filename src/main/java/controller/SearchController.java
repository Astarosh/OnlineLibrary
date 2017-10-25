package controller;

import db.DataHelper;
import entity.Author;
import entity.Book;
import entity.Genre;
import entity.Publisher;
import java.io.Serializable;
import java.util.Map;
import javax.faces.context.FacesContext;
import enums.SearchType;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

@ManagedBean(eager = true)
@javax.faces.bean.SessionScoped
public class SearchController implements Serializable {

    //переменные
    private SearchType searchType;  // хранит выбранный тип поиска
    private List<Book> currentBookList; // текущий список книг для отображения
    private int totalBookCountForNav; // общее количество найденных книг
    private int booksPerPageForNav; // общее количество страниц в навигации
    private ArrayList<Integer> totalPagesInNav; // список для отображения навигации
    private String chars; // сохраненные данные поиска chars
    private String letter;
    private int clickedPage; //нажатая страница
    private boolean editMode; // включен ли editmode
    private boolean addMode;
    private Author author; //
    private Publisher publisher;
    private Genre genre;
    private Long genreId;
    private int firstBookNumber; // номер первой книги для поиска
    private int lastBookNumber;  // количеств книг для поиска
    private Book selectedBook;
    private Long bookIdForRating;
    private Long bookVoteCountForRating;
    private Integer bookRating;
    private String birthDate;

    public SearchController() {
        clickedPage = 1;
        currentBookList = null;
        booksPerPageForNav = 2;
        searchType = SearchType.NAME;
    }

    public void updateBooks() {
        ResourceBundle bundle = ResourceBundle.getBundle("nls.properties", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        if (selectedBook == null) {
            int result = DataHelper.getInstance().updateBook(currentBookList.get(0));
            if (result == 1) {
                FacesMessage message = new FacesMessage(bundle.getString("thank"), bundle.getString("book_updated"));
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else {
                FacesMessage message = new FacesMessage(bundle.getString("sorry"), bundle.getString("book_update_fail"));
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } else {
            String result = DataHelper.getInstance().addBook(currentBookList.get(0));
            if (result != null) {
                FacesMessage message = new FacesMessage(bundle.getString("thank"), bundle.getString("book_uploaded"));
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else {
                FacesMessage message = new FacesMessage(bundle.getString("sorry"), bundle.getString("book_uploaded"));
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
        switchEditMode();
        searchType = SearchType.ISBN;
        someChangesToVariables(chars, genreId, letter, searchType);
        fillBooksBySearch();
    }

    private void someChangesToVariables(String chars, Long genreId, String letter, SearchType searchType) {
        selectedBook = null;
        String searchTypes = null;
        if (searchType != null) {
            searchTypes = searchType.toString().toLowerCase();
        }
        totalBookCountForNav = DataHelper.getInstance().getAllBooksNumber(genreId, chars, searchTypes, letter);
        this.chars = chars;
        this.genreId = genreId;
        this.letter = letter;
        this.searchType = searchType;
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (!params.containsKey("clickedPage")) {
            clickedPage = 1;
        }
        firstBookNumber = (clickedPage - 1) * booksPerPageForNav;
        lastBookNumber = booksPerPageForNav;
        if (totalBookCountForNav < clickedPage * booksPerPageForNav) {
            lastBookNumber = totalBookCountForNav - (clickedPage - 1) * booksPerPageForNav;
        }
        if (editMode == true && searchType != SearchType.ISBN) {
            editMode = false;
        }
        if (addMode == true) {
            addMode = false;
        }
    }

    public void fillBooksAll() {
        someChangesToVariables(null, Long.valueOf(-1), null, null);
        currentBookList = DataHelper.getInstance().getAllBooks(firstBookNumber, lastBookNumber);
    }

    public void fillBooksByGenre() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (params.containsKey("genreId")) {
            genreId = Long.valueOf(params.get("genreId"));
        }
        someChangesToVariables(null, genreId, null, SearchType.GENRE);
        currentBookList = DataHelper.getInstance().getBooksByGenre(genreId, firstBookNumber, lastBookNumber);
    }

    public void fillBooksByClickPages() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        clickedPage = Integer.parseInt(params.get("clickedPage").toLowerCase());
        if (genreId != null && genreId != -1) {
            fillBooksByGenre();
        } else if (chars != null) {
            fillBooksBySearch();
        } else if (letter != null) {
            fillBooksByLetter();
        } else {
            fillBooksAll();
        }
    }

    public void fillBooksByLetter() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (params.containsKey("letter")) {
            letter = params.get("letter").trim();
        }
        someChangesToVariables(null, null, letter, SearchType.LETTER);
        currentBookList = DataHelper.getInstance().getBooksByLetter(letter, firstBookNumber, lastBookNumber);
    }

    public void fillBooksBySearch() {
        if (chars != null) {
            chars = chars.trim();
        } else {
            chars = "";
        }
        if (searchType == null || searchType == SearchType.LETTER) {
            searchType = SearchType.NAME;
        }
        switch (searchType) {
            case AUTHOR:
                someChangesToVariables(chars, null, null, searchType);
                currentBookList = DataHelper.getInstance().getBooksByAuthor(chars, firstBookNumber, lastBookNumber);
                break;
            case NAME:
                someChangesToVariables(chars, null, null, searchType);
                currentBookList = DataHelper.getInstance().getBooksByName(chars, firstBookNumber, lastBookNumber);
                break;
            case ISBN:
                someChangesToVariables(chars, null, null, searchType);
                currentBookList = DataHelper.getInstance().getBooksByISBN(chars, firstBookNumber, lastBookNumber);
                break;
            default:
                someChangesToVariables(chars, null, null, searchType);
                currentBookList = DataHelper.getInstance().getBooksByName(chars, firstBookNumber, lastBookNumber);
                break;
        }
    }

    public String numberOfEditForms(String mainForm, String secondForm) {
        int quantityOfForms;
        quantityOfForms = booksPerPageForNav;
        StringBuilder stringbuilder;
        StringBuilder stringbuilder2 = new StringBuilder();
        for (int i = 0; i < quantityOfForms; i++) {
            stringbuilder = new StringBuilder();
            stringbuilder2.append(stringbuilder.append(mainForm).append(":").append(i).append(":").append(secondForm).append(" "));
        }
        return stringbuilder2.toString().trim();
    }

    public void selectEditBook() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        chars = params.get("editBookISBN");
        searchType = SearchType.ISBN;
        switchEditMode();
        fillBooksBySearch();
        searchType = SearchType.NAME;
        author = new Author();
        publisher = new Publisher();
        genre = new Genre();
    }

    public void switchAddMode() {
        switchEditMode();
        addMode = !addMode;
        totalBookCountForNav = 1;
        selectedBook = new Book();
        author = new Author();
        genre = new Genre();
        publisher = new Publisher();
        selectedBook.setAuthor(author);
        selectedBook.setGenre(genre);
        selectedBook.setPublisher(publisher);
        selectedBook.setRating(0);
        selectedBook.setVoteCount(0L);
        currentBookList = null;
        currentBookList = new ArrayList<>();
        currentBookList.add(selectedBook);

    }

    public void fillBooksAllLimit() {
        if (booksPerPageForNav == 0) {
            booksPerPageForNav = 1;
        }
        if (searchType == null) {
            searchType = SearchType.AUTHOR;
        }
        if (editMode == true) {
            editMode = false;
        }
        switch (searchType) {
            case GENRE:
                fillBooksByGenre();
                break;
            case LETTER:
                fillBooksByLetter();
                break;
            case AUTHOR:
                fillBooksBySearch();
                break;
            case NAME:
                fillBooksBySearch();
                break;
            case ISBN:
                fillBooksBySearch();
                break;
            default:
                fillBooksAll();
                break;
        }
    }

    public ArrayList<Integer> totalPagesArr() {
        totalPagesInNav = new ArrayList<>();
        int count = 0;
        if (currentBookList != null) {
            for (int i = 0; i < totalBookCountForNav; i += booksPerPageForNav) {
                count++;
                totalPagesInNav.add(count);
            }
        }
        return totalPagesInNav;
    }

    public void updateRating(Long bookId, Long voteCount, Integer bookRating) {
        bookIdForRating = bookId;
        bookVoteCountForRating = voteCount;
        this.bookRating = bookRating;
    }

    public void addAuthor() {
        LocalDate localDate;
        localDate = LocalDate.parse(birthDate);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        author.setBirthday(date);
        boolean isAuthorAdded;
        isAuthorAdded = DataHelper.getInstance().addAuthor(author);
        ResourceBundle bundle = ResourceBundle.getBundle("nls.properties", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        if (isAuthorAdded == true) {
            FacesMessage message = new FacesMessage(bundle.getString("thank"), bundle.getString("author_added"));
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            FacesMessage message = new FacesMessage(bundle.getString("sorry"), bundle.getString("author_add_fail"));
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void addPublisher() {
        ResourceBundle bundle = ResourceBundle.getBundle("nls.properties", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        boolean isPublisherAdded;
        isPublisherAdded = DataHelper.getInstance().addPublisher(publisher);
        if (isPublisherAdded == true) {
            FacesMessage message = new FacesMessage(bundle.getString("thank"), bundle.getString("publisher_added"));
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            FacesMessage message = new FacesMessage(bundle.getString("sorry"), bundle.getString("publisher_add_fail"));
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void addGenre() {
        ResourceBundle bundle = ResourceBundle.getBundle("nls.properties", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        boolean isGenreAdded = DataHelper.getInstance().addGenre(genre);
        if (isGenreAdded == true) {
            FacesMessage message = new FacesMessage(bundle.getString("thank"), bundle.getString("genre_added"));
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            FacesMessage message = new FacesMessage(bundle.getString("sorry"), bundle.getString("genre_add_fail"));
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void fillBooksByRating() {
        booksPerPageForNav = 5;
        totalBookCountForNav = 5;
        currentBookList = DataHelper.getInstance().getBooksByRating();
    }

    public void fillBooksByViewDate() {
        booksPerPageForNav = 5;
        totalBookCountForNav = 5;
        currentBookList = DataHelper.getInstance().getBooksByViewDate();
    }

    public void fillBooksByUploadDate() {
        booksPerPageForNav = 5;
        totalBookCountForNav = 5;
        currentBookList = DataHelper.getInstance().getBooksByUploadDate();
    }

    public void switchEditMode() {
        currentBookList = null;
        addMode = false;
        editMode = !editMode;
    }

    public boolean isAddMode() {
        return addMode;
    }

    public void setAddMode(boolean addMode) {
        this.addMode = addMode;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getBookRating() {
        return bookRating;
    }

    public void setBookRating(Integer bookRating) {
        this.bookRating = bookRating;
    }

    public Long getBookVoteCountForRating() {
        return bookVoteCountForRating;
    }

    public void setBookVoteCountForRating(Long bookVoteCountForRating) {
        this.bookVoteCountForRating = bookVoteCountForRating;
    }

    public Long getBookIdForRating() {
        return bookIdForRating;
    }

    public void setBookIdForRating(Long bookIdForRating) {
        this.bookIdForRating = bookIdForRating;
    }

    public Book getSelectedBook() {
        return selectedBook;
    }

    public void setSelectedBook(Book selectedBook) {
        this.selectedBook = selectedBook;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public int getClickedPage() {
        return clickedPage;
    }

    public void setClickedPage(int clickedPage) {
        this.clickedPage = clickedPage;
    }

    public int getLastBookNumber() {
        return lastBookNumber;
    }

    public void setLastBookNumber(int lastBookNumber) {
        this.lastBookNumber = lastBookNumber;
    }

    public int getFirstBookNumber() {
        return firstBookNumber;
    }

    public void setFirstBookNumber(int firstBookNumber) {
        this.firstBookNumber = firstBookNumber;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public ArrayList<Integer> getTotalPagesInNav() {
        return totalPagesArr();
    }

    public void setTotalPagesInNav(ArrayList<Integer> totalPagesInNav) {
        this.totalPagesInNav = totalPagesInNav;
    }

    public int getTotalBookCountForNav() {
        return totalBookCountForNav;
    }

    public void setTotalBookCountForNav(int totalBookCountForNav) {
        this.totalBookCountForNav = totalBookCountForNav;
    }

    public int getBooksPerPageForNav() {
        return booksPerPageForNav;
    }

    public void setBooksPerPageForNav(int booksPerPageForNav) {
        this.booksPerPageForNav = booksPerPageForNav;
    }

    public String getChars() {
        return chars;
    }

    public void setChars(String chars) {
        this.chars = chars;
    }

    public List<Book> getCurrentBookList() {
        return currentBookList;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
