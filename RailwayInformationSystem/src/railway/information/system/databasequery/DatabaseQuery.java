/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package railway.information.system.databasequery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import railway.information.system.dao.Carriage;
import railway.information.system.main.AuthFaceController;

/**
 *
 * @author Oleksander
 */
public class DatabaseQuery {

    public static String checkAuth(String name, String password) throws SQLException {
        String status = null;
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT user_name, user_password, user_status "
                + "FROM USERS "
                + "WHERE user_name = '" + name + "' and user_password = '" + password + "'");

        if (rs.next()) {
            status = rs.getString("user_status");
            stmt.close();
            return status;
        }
        stmt.close();
        return status;
    }

    public static List fillAllCarriages() throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT carriage_id FROM carriage");
        List<String> list = new ArrayList();
        while (rs.next()) {
            String carriage_id = rs.getString("carriage_id");
            list.add(carriage_id);
        }
        rs.close();
        return list;
    }

    public static List fillAllFreeCarriages() throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT carriage_id FROM carriage"
                + " WHERE fk_rolling_stock_id is null");
        List<String> list = new ArrayList();
        while (rs.next()) {
            String carriage_id = rs.getString("carriage_id");
            list.add(carriage_id);
        }
        stmt.close();
        return list;
    }

    public static Carriage getCarriageByID(String carriageId) throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT carriage_id, carriage_mark, fk_rolling_stock_id, "
                + "carriage_type_name, c_t1.carriage_type_name parent_type "
                + " FROM carriage INNER JOIN carriage_type c_t1 ON carriage.fk_carriage_type_id = c_t1.carriage_type_id" 
                + " JOIN carriage_type c_t2 ON c_t1.fk_parrent_type_id = c_t2.carriage_type_id "               
                + " WHERE carriage_id = " + carriageId + "");
        Carriage carriage = new Carriage();
        while (rs.next()) {
            carriage.setCarriageId(rs.getString("carriage_id"));
            carriage.setCarriageMark(rs.getString("carriage_mark"));
            carriage.setRollingStock(rs.getString("fk_rolling_stock_id"));
            carriage.setCarriageParentType(rs.getString("carriage_type_name"));
            carriage.setCarriageType(rs.getString("parent_type"));
        }
        stmt.close();
        return carriage;
    }
}
