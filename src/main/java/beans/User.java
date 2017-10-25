package beans;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@SessionScoped
@Named
public class User implements Serializable {

    private String password;
    private String username;
    private boolean isLogin = false;

    public User() {
    }

    public String goBooks() {
        return "/pages/books.xhtml?faces-redirect=true";
    }

    public String login() {
        isLogin = !isLogin;
        return "books";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIsLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }
}
