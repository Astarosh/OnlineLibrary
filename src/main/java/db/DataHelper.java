package db;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import entity.Author;
import entity.Book;
import entity.Genre;
import entity.HibernateUtil;
import entity.Publisher;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Property;
import org.hibernate.transform.Transformers;

public class DataHelper {

    private SessionFactory sessionFactory = null;
    private static DataHelper dataHelper;
    private final ProjectionList BOOKPROJECTION;

    private DataHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();

        BOOKPROJECTION = Projections.projectionList();
        BOOKPROJECTION.add(Projections.property("id"), "id");
        BOOKPROJECTION.add(Projections.property("name"), "name");
        BOOKPROJECTION.add(Projections.property("image"), "image");
        BOOKPROJECTION.add(Projections.property("genre"), "genre");
        BOOKPROJECTION.add(Projections.property("pageCount"), "pageCount");
        BOOKPROJECTION.add(Projections.property("isbn"), "isbn");
        BOOKPROJECTION.add(Projections.property("publisher"), "publisher");
        BOOKPROJECTION.add(Projections.property("author"), "author");
        BOOKPROJECTION.add(Projections.property("publishYear"), "publishYear");
        BOOKPROJECTION.add(Projections.property("descr"), "descr");
        BOOKPROJECTION.add(Projections.property("rating"), "rating");
        BOOKPROJECTION.add(Projections.property("voteCount"), "voteCount");
        BOOKPROJECTION.add(Projections.property("viewDate"), "viewDate");
        BOOKPROJECTION.add(Projections.property("addDate"), "addDate");
    }

    public static DataHelper getInstance() {
        if (dataHelper == null) {
            dataHelper = new DataHelper();
        }
        return dataHelper;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public int getAllBooksNumber(Long genreId, String searchString, String searchType, String letter) {
        int count;
        Criteria criteriaCount = getSession().createCriteria(Book.class);
        if (genreId != null && genreId != -1) {
            criteriaCount.add(Restrictions.eq("genre.id", genreId));
        } else if (searchString != null && searchType != null && letter == null) {
            if (searchType.equals("author")) {
                List<Author> list = getAuthorByName(searchString);
                if (list.size() > 0) {
                    Disjunction or = Restrictions.disjunction();
                    for (Author author : list) {
                        or.add(Restrictions.eq("author.id", author.getId()));
                    }
                    criteriaCount.add(or);
                } else {
                    return 0;
                }
            } else {
                criteriaCount.add(Restrictions.ilike(searchType, searchString, MatchMode.ANYWHERE));
            }
        } else if (letter != null) {
            criteriaCount.add(Restrictions.ilike("name", letter, MatchMode.START));
        }
        criteriaCount.setProjection(Projections.rowCount());
        count = (int) (long) criteriaCount.uniqueResult();
        return count;
    }

    public int updateBook(Book book) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("update Book ");
        queryBuilder.append("set name = :name, ");
        queryBuilder.append("pageCount = :pageCount, ");
        queryBuilder.append("isbn = :isbn, ");
        queryBuilder.append("genre = :genre, ");
        queryBuilder.append("author = :author, ");
        queryBuilder.append("publishYear = :publishYear, ");
        queryBuilder.append("publisher = :publisher, ");
        queryBuilder.append("rating = :rating, ");
        queryBuilder.append("voteCount = :voteCount, ");
        queryBuilder.append("viewDate = :viewDate, ");
        if (book.isImageEdited()) {
            queryBuilder.append("image = :image, ");
        }

        if (book.isContentEdited()) {
            queryBuilder.append("content = :content, ");
        }

        queryBuilder.append("descr = :descr ");
        queryBuilder.append("where id = :id");

        Query query = getSession().createQuery(queryBuilder.toString());

        LocalDateTime localDateTime = LocalDateTime.now();
        Date date2 = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        query.setParameter("name", book.getName());
        query.setParameter("pageCount", book.getPageCount());
        query.setParameter("isbn", book.getIsbn());
        query.setParameter("genre", book.getGenre());
        query.setParameter("author", book.getAuthor());
        query.setParameter("publishYear", book.getPublishYear());
        query.setParameter("publisher", book.getPublisher());
        query.setParameter("rating", book.getRating());
        query.setParameter("voteCount", book.getVoteCount());
        query.setParameter("viewDate", date2);
        query.setParameter("descr", book.getDescr());
        query.setParameter("id", book.getId());

        if (book.isImageEdited()) {
            query.setParameter("image", book.getImage());
        }
        if (book.isContentEdited()) {
            query.setParameter("content", book.getContent());
        }
        int result = query.executeUpdate();
        return result;
    }

    public String addBook(Book book) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date2 = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        book.setAddDate(date2);
        book.setViewDate(date2);
        Serializable save = getSession().save(book);
        return save.toString();
    }

    public boolean addAuthor(Author author) {
        List<Author> list = getAuthorByNameAdd(author.getFio());
        if (list == null || list.isEmpty()) {
            getSession().save(author);
            return true;
        } else {
            return false;
        }
    }

    public boolean addPublisher(Publisher publisher) {
        List<Publisher> list = getPublisherByName(publisher.getName());
        if (list == null || list.isEmpty()) {
            getSession().save(publisher);
            return true;
        } else {
            return false;
        }
    }

    public boolean addGenre(Genre genre) {
        List<Genre> list = getGenreByName(genre.getName());
        if (list == null || list.isEmpty()) {
            getSession().save(genre);
            return true;
        } else {
            return false;
        }
    }

    public void updateRating(Long bookId, Integer bookRating, Long voteCount) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("update Book ");
        queryBuilder.append("set rating = :rating, ");
        queryBuilder.append("voteCount = :voteCount ");
        queryBuilder.append("where id = :id");
        Query query = getSession().createQuery(queryBuilder.toString());
        query.setParameter("rating", bookRating);
        query.setParameter("voteCount", voteCount);
        query.setParameter("id", bookId);
        query.executeUpdate();
    }

    public List<Genre> getAllGenres() {
        return getSession().createCriteria(Genre.class
        ).addOrder(Order.asc("name")).list();
    }

    public List<Author> getAllAuthors() {
        return getSession().createCriteria(Author.class
        ).addOrder(Order.asc("fio")).list();
    }

    public List<Publisher> getAllPublishers() {
        return getSession().createCriteria(Publisher.class
        ).addOrder(Order.asc("name")).list();
    }

    public List<Author> getAuthorByName(String authorName) {
        return getSession().createCriteria(Author.class
        ).add(Restrictions.ilike("fio", authorName, MatchMode.ANYWHERE)).list();
    }

    public List<Author> getAuthorByNameAdd(String authorName) {
        return getSession().createCriteria(Author.class
        ).add(Restrictions.ilike("fio", authorName, MatchMode.EXACT)).list();
    }

    public List<Publisher> getPublisherByName(String publisherName) {
        return getSession().createCriteria(Publisher.class
        ).add(Restrictions.ilike("name", publisherName, MatchMode.EXACT)).list();
    }

    public List<Genre> getGenreByName(String genreName) {
        return getSession().createCriteria(Genre.class
        ).add(Restrictions.ilike("name", genreName, MatchMode.EXACT)).list();
    }

    public List<Book> getBooksByAuthor(String authorName, int first, int last) {
        List<Author> list;
        Criteria cr;
        Disjunction or;
        list = getAuthorByName(authorName);
        cr
                = getSession().createCriteria(Book.class
                ).addOrder(Order.asc("name"));
        or = Restrictions.disjunction();
        if (list.size() > 0) {
            for (Author author : list) {
                or.add(Restrictions.eq("author.id", author.getId()));
            }
            cr.add(or);
            cr.setFirstResult(first);
            cr.setMaxResults(last);

            return cr.setProjection(BOOKPROJECTION).setResultTransformer(Transformers.aliasToBean(Book.class
            )).list();
        } else {
            return null;
        }
    }

    public List<Book> getBooksByGenre(Long genreId, int first, int last) {
        Criteria criteria;
        criteria
                = getSession().createCriteria(Book.class
                ).setProjection(BOOKPROJECTION).setResultTransformer(Transformers.aliasToBean(Book.class
                )).add(Restrictions.eq("genre.id", genreId));
        criteria.setFirstResult(first);
        criteria.setMaxResults(last);
        criteria.addOrder(Order.asc("name"));
        return criteria.list();
    }

    public List<Book> getAllBooks(int first, int last) {
        return getBookList("name", "", MatchMode.ANYWHERE, first, last);
    }

    public List<Book> getBooksByLetter(String letter, int first, int last) {
        return getBookList("name", letter, MatchMode.START, first, last);
    }

    public List<Book> getBooksByName(String bookName, int first, int last) {
        return getBookList("name", bookName, MatchMode.ANYWHERE, first, last);
    }

    public List<Book> getBooksByISBN(String ISBN, int first, int last) {
        return getBookList("isbn", ISBN, MatchMode.ANYWHERE, first, last);
    }

    public List<Book> getBooksByRating() {
        Criteria criteria;
        criteria = getSession().createCriteria(Book.class).setProjection(BOOKPROJECTION).setResultTransformer(Transformers.aliasToBean(Book.class));
        criteria.setFirstResult(0);
        criteria.setMaxResults(5);
        criteria.addOrder(Order.desc("rating"));
        return criteria.list();
    }

    public List<Book> getBooksByViewDate() {
        Criteria criteria;
        criteria = getSession().createCriteria(Book.class).setProjection(BOOKPROJECTION).setResultTransformer(Transformers.aliasToBean(Book.class));
        criteria.setFirstResult(0);
        criteria.setMaxResults(5);
        criteria.addOrder(Order.desc("viewDate"));
        return criteria.list();
    }

    public List<Book> getBooksByUploadDate() {
        Criteria criteria;
        criteria = getSession().createCriteria(Book.class).setProjection(BOOKPROJECTION).setResultTransformer(Transformers.aliasToBean(Book.class));
        criteria.setFirstResult(0);
        criteria.setMaxResults(5);
        criteria.addOrder(Order.desc("addDate"));
        return criteria.list();
    }

    public byte[] getContent(Long id) {
        Criteria criteria = getSession().createCriteria(Book.class);
        criteria.setProjection(Property.forName("content"));
        criteria.add(Restrictions.eq("id", id));
        return (byte[]) criteria.uniqueResult();
    }

    public byte[] getImage(Long id) {
        return (byte[]) getFieldValue("image", id);
    }

    public Author
            getAuthor(long id) {
        return (Author) getSession().get(Author.class,
                id);
    }

    private List<Book> getBookList(String field, String value, MatchMode matchMode, int first, int last) {
        Criteria criteria;
        criteria
                = getSession().createCriteria(Book.class
                ).setProjection(BOOKPROJECTION).setResultTransformer(Transformers.aliasToBean(Book.class
                )).add(Restrictions.ilike(field, value, matchMode));
        criteria.setFirstResult(first);
        criteria.setMaxResults(last);
        criteria.addOrder(Order.asc("name"));
        return criteria.list();

    }

    private Object getFieldValue(String field, Long id) {
        return getSession().createCriteria(Book.class
        ).setProjection(Projections.property(field)).add(Restrictions.eq("id", id)).uniqueResult();
    }
}
