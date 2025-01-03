import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Author: pasindi
 * Date: 1/2/25
 * Time: 2:25 PM
 * Description:
 */
@WebServlet(urlPatterns = "/lifecycle")
public class LifeCycleServlet extends HttpServlet {
    public LifeCycleServlet(){
        System.out.println("inside constructor");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("inside doGet");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("inside init");
    }

    @Override
    public void destroy() {
        System.out.println("inside destroy");
    }
}
