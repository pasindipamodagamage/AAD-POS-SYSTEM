import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import java.io.IOException;
import java.sql.*;

/**
 * Author: pasindi
 * Date: 1/2/25
 * Time: 9:25 AM
 * Description:
 */

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        String name = req.getParameter("name");
        double price = Double.parseDouble(req.getParameter("price"));
        int qty = Integer.parseInt(req.getParameter("qty"));

        if (code == null || name == null || price == 0.0 || qty == 0){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"All fields are required\"}");
            return;
        }

        Connection connection =getConnection();

        try {
            PreparedStatement pstm=connection.prepareStatement("INSERT INTO item VALUES (?,?,?,?)");
            pstm.setString(1,code);
            pstm.setString(2,name);
            pstm.setDouble(3,price);
            pstm.setInt(4,qty);
            pstm.executeUpdate();

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("{\"success\": \"Item added successfully\"}");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code=req.getParameter("code");
        String name=req.getParameter("name");
        double price= Double.parseDouble(req.getParameter("price"));
        int qty= Integer.parseInt(req.getParameter("qty"));

        if (code == null || name == null || price == 0.0 || qty == 0){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"All fields are required\"}");
            return;
        }

        Connection connection=getConnection();
        ItemDTO item = findByCode(code);

            try {
                if (item != null) {
                    PreparedStatement pstm = connection.prepareStatement("UPDATE item SET name = ?, price = ?, qty = ? WHERE code = ?");
                    pstm.setString(1, name);
                    pstm.setDouble(2, price);
                    pstm.setInt(3, qty);
                    pstm.setString(4, code);
                    pstm.executeUpdate();

                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("{\"error\": \"Item not found\"}");
                }
                resp.getWriter().write("{\"success\": \"Item updated successfully\"}");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

    }

    private ItemDTO findByCode(String code) {
        Connection connection= getConnection();

        try {
            PreparedStatement pstm=connection.prepareStatement("SELECT * FROM item WHERE code = ?");
            pstm.setString(1,code);
            ResultSet resultSet=pstm.executeQuery();
            if (resultSet.next()){
                String name=resultSet.getString(2);
                double price=resultSet.getDouble(3);
                int qty=resultSet.getInt(4);
                return new ItemDTO(code,name,price,qty);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code=req.getParameter("code");
        ItemDTO item = findByCode(code);

        if (item == null){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\": \"Item not found\"}");
            return;
        }

        Connection connection =getConnection();

        try{
            PreparedStatement pstm =connection.prepareStatement("DELETE FROM item WHERE code = ?");
            pstm.setString(1,code);
            int rowsAffected =pstm.executeUpdate();

            if (rowsAffected > 0){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                resp.getWriter().write("{\"success\": \"Item deleted successfully\"}");
            }else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("{\"error\": \"Failed to delete Item\"}");
            }
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Connection connection=getConnection();
            ResultSet resultSet= connection.prepareStatement("SELECT * FROM item").executeQuery();

            JsonArrayBuilder allItems = Json.createArrayBuilder();

            while (resultSet.next()){
                String code = resultSet.getString(1);
                String name = resultSet.getString(2);
                double price = resultSet.getDouble(3);
                int qty= resultSet.getInt(4);

                JsonArrayBuilder item = Json.createArrayBuilder();
                item.add("code");
                item.add("name");
                item.add("price");
                item.add(qty, "qty");
                allItems.add(item.build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
