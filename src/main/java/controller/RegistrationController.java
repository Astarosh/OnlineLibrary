package controller;

import bean.User;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named
public class RegistrationController implements Serializable{
    private User user;
    private String passwordTwo;

    public RegistrationController(){
        user = new User();
    }
    
    public String getPasswordTwo() {
        return passwordTwo;
    }

    public void setPasswordTwo(String passwordTwo) {
        this.passwordTwo = passwordTwo;
    }
    public void saveUser(){
    }
    public void openRegistration(){
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
