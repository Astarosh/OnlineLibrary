package controllers;

import beans.User;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@SessionScoped
@Named
public class LoginController implements Serializable {
    private boolean isLogin = false;

    public boolean isIsLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    private String password;
    private String username;

    public LoginController() {
    }

    public String login() {
        isLogin = !isLogin;
        return "books";
    }
//    public String login() {
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            String text;
//            text = password;
//            md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
//            byte[] digest = md.digest();
//            BigInteger bigInt = new BigInteger(1, digest);
//            String output = bigInt.toString(16);
//            password = output;
//        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
//            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        Statement stmt = null;
//        ResultSet rs = null;
//        Connection conn = null;
//        String sql1 = "select userid from user order by userid desc limit 0,1";
//        String sql2;
//        long count = 0;
//        try {
//            conn = Database.getConnection();
//            stmt = conn.createStatement();
//            rs = stmt.executeQuery(sql1);
//            while (rs.next()) {
//                count = rs.getLong("userid");
//            }
//            count++;
//            sql2 = "insert into user values("+count+", '" + username + "', '"+ password + "')";
//            stmt.executeUpdate(sql2);
//        } catch (SQLException ex) {
//            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                if (stmt != null) {
//                    stmt.close();
//                }
//                if (rs != null) {
//                    rs.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return "books";
//    }

    public String logout() {
        isLogin = !isLogin;
        String result = "/books.xhtml?faces-redirect=true";

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            request.logout();
        } catch (ServletException e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, e);
        }

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        return result;
    }

}
