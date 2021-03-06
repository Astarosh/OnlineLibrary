package entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Author implements java.io.Serializable {

    private Long id;
    private String fio;
    private Date birthday;
    private Set books = new HashSet(0);

    public Author() {
    }

    public Author(String fio, Date birthday) {
        this.fio = fio;
        this.birthday = birthday;
    }

    public Author(String fio, Date birthday, Set books) {
        this.fio = fio;
        this.birthday = birthday;
        this.books = books;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFio() {
        return this.fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Set getBooks() {
        return this.books;
    }

    public void setBooks(Set books) {
        this.books = books;
    }

}
