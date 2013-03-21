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
 * @author cdovgal
 */
public class DatabaseQuerySF {

    public static List fillMap(String element) throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT "+element+"_name FROM "+element);
        List<String> list = new ArrayList();
        while (rs.next()) {
            String element_name = rs.getString(element+"_name");
            list.add(element_name);
        }
        rs.close();
        return list;
    }
    
    
    
}
