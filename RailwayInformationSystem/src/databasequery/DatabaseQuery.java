/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package databasequery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import railwayinformationsystem.AuthFaceController;

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
}
