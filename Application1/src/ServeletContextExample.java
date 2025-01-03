import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Author: pasindi
 * Date: 1/2/25
 * Time: 2:15 PM
 * Description:
 */

@WebServlet(urlPatterns = "/dbcp_example")
public class ServeletContextExample extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext=req.getServletContext();
        BasicDataSource ds = (BasicDataSource) servletContext.getAttribute("dataSource");

        try {
            Connection connection=ds.getConnection();
            ResultSet resultSet=connection.prepareStatement("SELECT * FROM customer").executeQuery();

            while (resultSet.next()){
                String id=resultSet.getString("id");
                String name=resultSet.getString("name");
                String address=resultSet.getString("address");
                System.out.println(id+" "+name+" "+address);
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
