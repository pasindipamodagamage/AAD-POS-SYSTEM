import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Author: pasindi
 * Date: 1/2/25
 * Time: 11:01 AM
 * Description:
 */


@WebServlet(urlPatterns = "/example")
public class ExampleSevlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        this for response
        resp.setContentType("application/json");
        resp.addHeader("token","wruhegd7tr7387");
//        resp.addHeader("ipaddress","127.233.1.1");

//        reqest types writtten from js
        req.getContentType();

//        status eka properly manage kiriima
//        status ekak set karanna
//        mewa set karanne ui ekat.. e kiynne html eke js ekat
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}