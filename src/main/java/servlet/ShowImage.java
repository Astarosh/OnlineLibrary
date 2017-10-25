package servlet;

import controller.SearchController;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entity.Book;
import java.util.List;

@WebServlet(name = "ShowImage",
urlPatterns = {"/ShowImage"})
public class ShowImage extends HttpServlet {

//    private static Map<Long, Byte[]> imageMap = new HashMap();
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("image/jpeg");
        try (OutputStream out = response.getOutputStream()) {
            int index = Integer.valueOf(request.getParameter("id"));
            SearchController searchController = (SearchController) request.getSession(false).getAttribute("searchController");
            List<Book> list;
            list = searchController.getCurrentBookList();
            byte[] image = null;
            for (Book book : list) {
                if(book.getId() == index){
                    int ind = list.indexOf(book);
                    image = ((Book)list.get(ind)).getImage();
                }
            }
            response.setContentLength(image.length);
            out.write(image);
        } catch (IOException | NumberFormatException ex) {
        }
    }


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
