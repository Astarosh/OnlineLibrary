package entity;

import java.util.HashSet;
import java.util.Set;

public class Genre implements java.io.Serializable {

    private Long id;
    private String name;
    private Set books = new HashSet(0);

    public Genre() {
    }

    public Genre(String name) {
        this.name = name;
    }

    public Genre(String name, Set books) {
        this.name = name;
        this.books = books;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set getBooks() {
        return this.books;
    }

    public void setBooks(Set books) {
        this.books = books;
    }

}
