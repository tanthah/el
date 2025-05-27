package test;

import Model.Account;
import Model.Admin;
import Model.Course;
import Model.Teacher;
import dao.*;
import dao.AdminDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "TestServlet", urlPatterns = {"/test"})
public class test extends HttpServlet {
    
    private CourseDAO courseDAO;

    @Override
    public void init() throws ServletException {
              courseDAO = new CourseDAO();
    }

    
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountDAO accountDAO = new AccountDAO();
               
           if ( courseDAO.deleteCourse(3L)) {
               
                // Gửi danh sách khóa học và cờ showCourses đến JSP
                     request.getRequestDispatcher("/Done.jsp").forward(request, response);
           }
           else
                request.getRequestDispatcher("/ERROR.jsp").forward(request, response);
           
    
    }
    
}