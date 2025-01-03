import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Author: pasindi
 * Date: 2024-12-11
 * Time: 02:28 PM
 * Description:
 */

@WebServlet(urlPatterns = "/extract") // Extract Mapping
public class ExtractMapping extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Extract Mapping");
    }
}
