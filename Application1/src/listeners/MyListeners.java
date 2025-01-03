package listeners;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.SQLException;

/**
 * Author: pasindi
 * Date: 1/2/25
 * Time: 3:13 PM
 * Description:
 */

@WebListener
public class MyListeners implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        database pool
        BasicDataSource ds=new BasicDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/Shop");
        ds.setUsername("root");
        ds.setPassword("Ijse@123");
        ds.setMaxTotal(5);
        ds.setInitialSize(5);


//        common interface to all servlet
        ServletContext servletContext=sce.getServletContext();
        servletContext.setAttribute("dataSource",ds);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext servletContext=sce.getServletContext();
        BasicDataSource dataSourse= (BasicDataSource) servletContext.getAttribute("dataSource");

        try {
            dataSourse.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
