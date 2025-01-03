import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.sql.*;

/**
 * Author: pasindi
 * Date: 1/2/25
 * Time: 9:25 AM
 * Description:
 */

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

    private Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Shop",
                    "root",
                    "Ijse@123");
            return connection;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection connection = getConnection();
            ResultSet resultSet = connection.prepareStatement("SELECT * FROM customer")
                    .executeQuery();
            JsonArrayBuilder allCustomers = Json.createArrayBuilder();
            while (resultSet.next()) {
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);

                //create a single json object
                JsonObjectBuilder customer = Json.createObjectBuilder();
                customer.add("id", id);
                customer.add("name", name);
                customer.add("address", address);
                allCustomers.add(customer.build());
            }
            resp.setContentType("application/json");

            resp.getWriter().write(allCustomers.build().toString());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        if (id == null || name == null || address == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"All fields are required\"}");
            return;
        }

        Connection connection = getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO customer VALUES (?,?,?)");
            pstm.setString(1, id);
            pstm.setString(2, name);
            pstm.setString(3, address);
            pstm.executeUpdate();
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("{\"success\": \"Customer added successfully\"}");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        if (id == null || name == null || address == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"All fields are required\"}");
            return;
        }

        Connection connection = getConnection();
        CustomerDTO customer = findById(id);

        try {
            if (customer != null) {
                PreparedStatement pstm = connection.prepareStatement("UPDATE customer SET name = ?, address = ? WHERE id = ?");
                pstm.setString(1, name);
                pstm.setString(2, address);
                pstm.setString(3, id);
                pstm.executeUpdate();
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\": \"Customer not found\"}");
            }
            resp.getWriter().write("{\"success\": \"Customer updated successfully\"}");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private CustomerDTO findById(String id) {
        Connection connection = getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM customer WHERE id = ?");
            pstm.setString(1, id);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                return new CustomerDTO(id, name, address);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        CustomerDTO customer = findById(id);

        if (customer == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\": \"Customer not found\"}");
            return;
        }

        Connection connection = getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM customer WHERE id = ?");
            pstm.setString(1, id);
            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                resp.getWriter().write("{\"success\": \"Customer deleted successfully\"}");
            }else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("{\"error\": \"Failed to delete customer\"}");
            }
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
