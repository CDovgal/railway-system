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
import railway.information.system.dao.MapElement;
import railway.information.system.dao.Route;
import railway.information.system.dao.Train;
import railway.information.system.main.AuthFaceController;
/**
 *
 * @author cdovgal
 */
public class DatabaseQuerySF {
    // new code
    
    public static ArrayList get_all_stations() throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT country_id, country_name FROM country");
        ArrayList<MapElement> list = new ArrayList();
        while (rs.next()) {
            list.add(new MapElement(rs.getString("country_id"), rs.getString("country_name")));
        }
        rs.close();
        return list;
    }
    
    public static ArrayList get_cities(String country_id) throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT city_id, city_name FROM city WHERE fk_country_id = " + country_id);
        ArrayList<MapElement> list = new ArrayList();
        while (rs.next()) {
            list.add(new MapElement(rs.getString("city_id"), rs.getString("city_name")));
        }
        rs.close();
        return list;
    }
    
    public static ArrayList get_stations(String city_id) throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        String q = "SELECT station_id, station_name FROM station WHERE fk_city_id = " + city_id;
        ResultSet rs = stmt.executeQuery(q);
        ArrayList<MapElement> list = new ArrayList();
        while (rs.next()) {
            list.add(new MapElement(rs.getString("station_id"), rs.getString("station_name")));
        }
        rs.close();
        return list;
    }
    
    // end new code
    // ----
    // old code
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
    
    public static void commit_map(ArrayList<String> hash) throws SQLException {
        AuthFaceController.conn.setAutoCommit(false);
        try {
            for (String query : hash) {
                AuthFaceController.conn.prepareCall(query).execute();
            }
            AuthFaceController.conn.commit();
            AuthFaceController.conn.setAutoCommit(true);
        } catch (SQLException ex) {
            AuthFaceController.conn.rollback();
        }
    }
    // end map
    //-------------------
    // start train
    public static List fillTrains() throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT train_id, train_name, fk_departure_station_id, fk_arrival_station_id FROM train");
        List<Train> list = new ArrayList();
        while (rs.next()) {
            String train_id = rs.getString("train_id");
            String train_name = rs.getString("train_name");
            String departure_station_id = rs.getString("fk_departure_station_id");
            String arrival_station_id = rs.getString("fk_arrival_station_id");
            list.add(new Train(train_id,train_name,departure_station_id,arrival_station_id));
        }
        rs.close();
        return list;
    }
    
    public static void ChangeTrainName(String train_id, String new_name) throws SQLException {
        String query = "update train set train_name = '"+new_name+"' where train_id = "+train_id;
        AuthFaceController.conn.prepareCall(query).execute();
    }
    
    public static List fillRoute(String train_id, String departure_station_id, String arrival_station_id) throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery(
"with rec_route(station_id_from, station_id_to, arrival_time, departure_time,travel_time) as\n" +
"  (select fk_departure_station_id, fk_arrival_station_id, departure_time - INTERVAL '45:00' MINUTE TO SECOND, departure_time, travel_time\n" +
"   from route, dual\n" +
"   where fk_train_id = "+train_id+" and fk_departure_station_id = "+departure_station_id+"\n" +
"     union all\n" +
"   select r.fk_departure_station_id, r.fk_arrival_station_id, s.departure_time+s.travel_time, r.departure_time, r.travel_time\n" +
"   from route r JOIN rec_route s on(r.fk_departure_station_id = s.station_id_to ) where fk_train_id = "+train_id+"\n" +
"  )\n" +
"select station_name, TO_CHAR( arrival_time, 'HH24:MI') arrival_time,  TO_CHAR( departure_time, 'HH24:MI') departure_time \n" +
"from rec_route join station on(rec_route.station_id_from=station.station_id)\n" +
"  union \n" +
"select station_name, TO_CHAR( departure_time+travel_time, 'HH24:MI') arrival_time, null\n" +
"from route join station on (fk_arrival_station_id = station_id)\n" +
"where fk_train_id = "+train_id+" and fk_arrival_station_id = "+arrival_station_id+"\n" +
"order by arrival_time"
                );
        List<Route> list = new ArrayList();
        while (rs.next()) {
            Route temp_route = new Route();
            temp_route.setStation(rs.getString("station_name"));
            temp_route.setDeparture_time(rs.getString("arrival_time"));
            temp_route.setArrival_time(rs.getString("departure_time"));
            
            list.add(temp_route);
        }
        rs.close();
        return list;
    }
    
    public static void deleteStationFromRoute(String train_id, String station_name_delete) throws SQLException { 
        try {
            AuthFaceController.conn.setAutoCommit(false);
            String query = "{ call delete_route("+train_id+",'"+station_name_delete+"') }";        
            AuthFaceController.conn.prepareCall(query).execute();
            AuthFaceController.conn.commit();
            AuthFaceController.conn.setAutoCommit(true);
        } catch (SQLException ex) {
            AuthFaceController.conn.rollback();
        }
    }
    
    public static void deleteTrain(String train_id) throws SQLException {
        String query = "delete from train where train_id = "+train_id;
        AuthFaceController.conn.prepareCall(query).execute();
    }
    
    public static void addStationToRoute(
              String train_id
            , String departure_station_name
            , String arrival_station_name
            , String new_station_name
            , String new_travel_time
            , String new_stop_time) throws SQLException {
        try {
            AuthFaceController.conn.setAutoCommit(false);
            String query = "{ call add_route("
                    + train_id + ","
                    + "'" + departure_station_name + "',"
                    + "'" + arrival_station_name + "',"
                    + "'" + new_station_name + "',"
                    + "interval '0 " + new_travel_time + ":00'" + " day to second,"
                    + "interval '0 " + new_stop_time   + ":00'" + " day to second"
                    + ") }";
            AuthFaceController.conn.prepareCall(query).execute();
           AuthFaceController.conn.commit();
            AuthFaceController.conn.setAutoCommit(true);
        } catch (SQLException ex) {
            AuthFaceController.conn.rollback();
        }
        
    }
    
    public static void change_departure_time(String train_id, String station_name, String new_time) throws SQLException {
        String query = "{ call change_departure_time("+train_id+",'"+station_name+"',to_timestamp('"+new_time+"','HH24:MI')) }";
        AuthFaceController.conn.prepareCall(query).execute();
    }
    
    public static void change_travel_time(String train_id, String station_name, String new_travel_time) throws SQLException {
        String query = "{ call change_travel_time("
                + train_id + ","
                + "'" + station_name + "',"
                + "interval '0 " + new_travel_time + ":00'" + " day to second) }";

        AuthFaceController.conn.prepareCall(query).execute();
    }
}

