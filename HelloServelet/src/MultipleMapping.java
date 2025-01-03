import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Author: pasindi
 * Date: 2024-12-18
 * Time: 08:48 AM
 * Description:
 */

@WebServlet(urlPatterns = {"/xx","/yy"})
public class MultipleMapping extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Multiple Mapping Invoked");
    }
}
