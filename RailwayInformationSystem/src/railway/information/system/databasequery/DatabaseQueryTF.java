/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package railway.information.system.databasequery;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
;
import railway.information.system.dao.Carriage;
import railway.information.system.dao.Locomotive;
import railway.information.system.dao.OrderInfo;
import railway.information.system.dao.StockInfo;
import railway.information.system.main.AuthFaceController;
import railway.information.system.dao.Carriage;
import railway.information.system.dao.Locomotive;
import railway.information.system.dao.OrderInfo;
import railway.information.system.dao.StockInfo;
import railway.information.system.main.AuthFaceController;

/**
 *
 * @author Oleksander
 */


public class DatabaseQueryTF {

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
        String sql = "SELECT carriage_id FROM carriage";
        PreparedStatement stmt = AuthFaceController.conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        List<String> list = new ArrayList();
        while (rs.next()) {
            String carriage_id = rs.getString("carriage_id");
            list.add(carriage_id);
        }
        rs.close();
        return list;
    }

    public static List fillAllStockCarriages(String stockID) throws SQLException {
        String sql = "SELECT carriage_id FROM carriage WHERE fk_rolling_stock_id = '" + stockID + "'";
        PreparedStatement stmt = AuthFaceController.conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        List<String> list = new ArrayList();
        while (rs.next()) {
            String carriage_id = rs.getString("carriage_id");
            list.add(carriage_id);
        }
        rs.close();
        return list;
    }

    public static List<String> fillAllFreeCarriages() throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT carriage_id FROM carriage "
                + "WHERE carriage_id NOT IN (SELECT fk_carriage_id FROM carriage_freight_order_item)");
        List<String> list = new ArrayList();
        while (rs.next()) {
            String carriage_id = rs.getString("carriage_id");
            list.add(carriage_id);
        }
        stmt.close();
        return list;
    }

    public static Carriage getCarriageByID(String carriageId) throws SQLException {
        String sql = "SELECT carriage_id, carriage_mark, fk_rolling_stock_id, "
                + "carriage_type_name, c_t1.carriage_type_name parent_type "
                + " FROM carriage INNER JOIN carriage_type c_t1 ON carriage.fk_carriage_type_id = c_t1.carriage_type_id"
                + " JOIN carriage_type c_t2 ON c_t1.fk_parrent_type_id = c_t2.carriage_type_id "
                + " WHERE carriage_id = ?";
        PreparedStatement stmt = AuthFaceController.conn.prepareStatement(sql);
        stmt.setString(1, carriageId);
        ResultSet rs = stmt.executeQuery();
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

    public static List fillAllCarriageTypes() throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT carriage_type_name FROM carriage_type"
                + " WHERE fk_parrent_type_id is null");
        List<String> list = new ArrayList();
        while (rs.next()) {
            String carriage_type_name = rs.getString("carriage_type_name");
            list.add(carriage_type_name);
        }
        list.add(" ");
        stmt.close();
        return list;

    }

    public static List fillAllCarriageSubtypes() throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT carriage_type_name FROM carriage_type"
                + " WHERE fk_parrent_type_id is not null");
        List<String> list = new ArrayList();
        while (rs.next()) {
            String carriage_type_name = rs.getString("carriage_type_name");
            list.add(carriage_type_name);
        }
        list.add(" ");
        stmt.close();
        return list;

    }

    public static List fillAllCarriageMarks() throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT distinct carriage_mark FROM carriage");
        List<String> list = new ArrayList();
        while (rs.next()) {
            String carriage_mark = rs.getString("carriage_mark");
            list.add(carriage_mark);
        }
        list.add(" ");
        stmt.close();
        return list;

    }

    public static List fillAllCarriageStocks() throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT rolling_stock_id FROM rolling_stock");
        List<String> list = new ArrayList();
        while (rs.next()) {
            String rolling_stock_id = rs.getString("rolling_stock_id");
            list.add(rolling_stock_id);
        }
        list.add("Null");
        list.add(" ");
        stmt.close();
        return list;

    }

    //-----------------------Locomotive
    public static List fillAllLocomotives() throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT locomotive_id FROM locomotive");
        List<String> list = new ArrayList();
        while (rs.next()) {
            String locomotive_id = rs.getString("locomotive_id");
            list.add(locomotive_id);
        }
        rs.close();
        return list;
    }

    public static List fillAllFreeLocomotives() throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT locomotive_id FROM locomotive LEFT JOIN rolling_stock ON rolling_stock.fk_lokomotive_id = locomotive.locomotive_id "
                + " WHERE (ready = 0) or (rolling_stock_id is null)");
        List<String> list = new ArrayList();
        while (rs.next()) {
            String locomotive_id = rs.getString("locomotive_id");
            list.add(locomotive_id);
        }
        stmt.close();
        return list;
    }

    public static Locomotive getLocomotiveByID(String locoId) throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT locomotive_id, number_carriages, locomotive_mark,"
                + " locomotive_type,railroad_type, rolling_stock_id FROM locomotive"
                + " LEFT JOIN rolling_stock ON rolling_stock.fk_lokomotive_id = locomotive.locomotive_id"
                + " WHERE locomotive.locomotive_id = '" + locoId + "'");
        Locomotive locomotive = new Locomotive();
        while (rs.next()) {
            locomotive.setLocoId(rs.getString("locomotive_id"));
            locomotive.setNumberOfCarriages(rs.getString("number_carriages"));
            locomotive.setLocoMark(rs.getString("locomotive_mark"));
            locomotive.setLocoType(rs.getString("locomotive_type"));
            locomotive.setRailRoadType(rs.getString("railroad_type"));
            locomotive.setRollingStock(rs.getString("rolling_stock_id"));
        }
        stmt.close();
        return locomotive;
    }

    public static List fillAllLocomotiveTypes() throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT distinct locomotive_type FROM locomotive");
        List<String> list = new ArrayList();
        while (rs.next()) {
            String locomotive_type = rs.getString("locomotive_type");
            list.add(locomotive_type);
        }
        list.add(" ");
        stmt.close();
        return list;

    }

    public static List fillLocomotiveRailroads() throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT distinct railroad_type FROM locomotive");
        List<String> list = new ArrayList();
        while (rs.next()) {
            String railroad_type = rs.getString("railroad_type");
            list.add(railroad_type);
        }
        list.add(" ");
        stmt.close();
        return list;

    }

    public static List fillAllLocomotiveMarks() throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT distinct locomotive_mark FROM locomotive");
        List<String> list = new ArrayList();
        while (rs.next()) {
            String locomotive_mark = rs.getString("locomotive_mark");
            list.add(locomotive_mark);
        }
        list.add(" ");
        stmt.close();
        return list;

    }

    public static List fillAllLocomotiveStocks() throws SQLException {
        String sql = "SELECT rolling_stock_id FROM rolling_stock";
        PreparedStatement stmt = AuthFaceController.conn.prepareCall(sql);
        ResultSet rs = stmt.executeQuery();
        List<String> list = new ArrayList();
        while (rs.next()) {
            String rolling_stock_id = rs.getString("rolling_stock_id");
            list.add(rolling_stock_id);
        }
        list.add("Null");
        list.add(" ");
        stmt.close();
        return list;

    }

    public static List fillAllLocomotiveNumOfCarraiges() throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT number_carriages FROM locomotive");
        List<String> list = new ArrayList();
        while (rs.next()) {
            String number_carriages = rs.getString("number_carriages");
            list.add(number_carriages);
        }
        list.add(" ");
        stmt.close();
        return list;
    }

    public static List fillOrders() throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT foi_id FROM freight_order_item LEFT JOIN carriage_freight_order_item "
                + " ON carriage_freight_order_item.fk_foi_id = freight_order_item.foi_id "
                + "LEFT JOIN carriage ON carriage.carriage_id = carriage_freight_order_item.fk_carriage_id "
                + "WHERE fk_rolling_stock_id NOT IN ((SELECT rolling_stock_id FROM rolling_stock WHERE ready =1)) or fk_rolling_stock_id is NULL");
        List<String> list = new ArrayList();
        while (rs.next()) {
            String foi_id = rs.getString("foi_id");
            list.add(foi_id);
        }
        stmt.close();
        return list;
    }

    public static List getAllStocks() throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT rolling_stock_id from rolling_stock");
        List<String> list = new ArrayList();
        while (rs.next()) {
            String rolling_stock_id = rs.getString("rolling_stock_id");
            list.add(rolling_stock_id);
        }
        stmt.close();
        return list;
    }

    public static List getAllOutScheduleFreightStocks() throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery(" SELECT distinct fk_rolling_stock_id FROM carriage "
                + " JOIN rolling_stock ON carriage.fk_rolling_stock_id = rolling_stock.rolling_stock_id "
                + " JOIN carriage_type ON carriage_type.carriage_type_id = carriage.fk_carriage_type_id "
                + " WHERE fk_parrent_type_id = 2 and ready = 0");
        List<String> list = new ArrayList();
        while (rs.next()) {
            String rolling_stock_id = rs.getString("fk_rolling_stock_id");
            list.add(rolling_stock_id);
        }
        stmt.close();
        return list;
    }

    public static OrderInfo getOrderInfo(String orderID) throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT foi_id,delivery,mass,origin, goods_type "
                + " From FREIGHT_ORDER_ITEM"
                + " INNER JOIN goods ON goods.goods_name = freight_order_item.fk_goods_name"
                + " WHERE foi_id ='" + orderID + "'");
        OrderInfo oi = new OrderInfo();
        while (rs.next()) {
            oi.setOrderId(rs.getString("foi_id"));
            oi.setDelivery(rs.getString("delivery"));
            oi.setMass(rs.getString("mass"));
            oi.setOrigin(rs.getString("origin"));
            oi.setGoodType(rs.getString("goods_type"));
        }
        stmt.close();
        return oi;
    }

    public static List<StockInfo> getStockInfo(String stockID) throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT fk_lokomotive_id, carriage_id FROM rolling_stock "
                + " LEFT JOIN carriage ON carriage.fk_rolling_stock_id = rolling_stock.rolling_stock_id  "
                + " WHERE rolling_stock.rolling_stock_id = '" + stockID + "'");
        List<StockInfo> s_i = new ArrayList();
        while (rs.next()) {
            StockInfo si = new StockInfo();
            si.setLocomotiveId(rs.getString("fk_lokomotive_id"));
            si.setNumberOfCarriage(rs.getString("carriage_id"));
            s_i.add(si);
        }
        stmt.close();
        return s_i;
    }

    public static void addStock(String locom, List<String> carriages) throws SQLException {
        CallableStatement addCarriagesToStockProc = null;
        AuthFaceController.conn.setAutoCommit(false);
        ifLocHasStock(locom);
        try {
            AuthFaceController.conn.prepareCall("{ call create_new_stock('" + locom + "') }").execute();
            addCarriagesToStockProc = AuthFaceController.conn.prepareCall("{ call add_carriage_to_stock(?) }");
            for (String car : carriages) {
                addCarriagesToStockProc.setInt(1, Integer.parseInt(car));
                addCarriagesToStockProc.addBatch();
            }
            addCarriagesToStockProc.executeBatch();
            AuthFaceController.conn.commit();
            AuthFaceController.conn.setAutoCommit(true);
        } catch (SQLException ex) {
            AuthFaceController.conn.rollback();
        }
    }

    private static void ifLocHasStock(String locId) throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery(" SELECT fk_lokomotive_id FROM rolling_stock "
                + " WHERE fk_lokomotive_id = '" + locId + "'");
        while (rs.next()) {
            String s = rs.getString("fk_lokomotive_id");
            if (s != null) {
                Statement stmt2 = AuthFaceController.conn.createStatement();
                String query = "DELETE FROM rolling_stock"
                        + " WHERE fk_lokomotive_id = '" + s + "'";
                stmt2.executeUpdate(query);
                //deleteStock(s);
            }
        }
    }

    /*public static void deleteStock(String loco) throws SQLException {
     Statement stmt2 = AuthFaceController.conn.createStatement();
     = stmt2.executeUpdate("DELETE FROM rolling_stock "
     + " WHERE fk_lokomotive_id = '" + loco + "'");
     }
     */
    public static boolean isLocInTrain(String locId) throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT fk_lokomotive_id FROM rolling_stock "
                + " WHERE ready = 1");
        while (rs.next()) {
            if (locId.equals(rs.getString("fk_lokomotive_id"))) {
                return true;
            }
        }
        return false;

    }

    public static String lastStock() throws SQLException {
        String s = null;
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT max(rolling_stock_id) FROM rolling_stock");
        rs.next();
        s = rs.getString(1);
        stmt.close();
        return s;
    }

    public static void dropCarriage(String carriageId) throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("UPDATE carriage SET fk_rolling_stock_id = null WHERE carriage_id = '" + carriageId + "'");
    }

    public static void dropLoco(String locoId) throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("DELETE locomotive WHERE locomotive_id = '" + locoId + "'");
    }

    public static void addOrderForCarriage(String carriageId, String orderId) throws SQLException {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        PreparedStatement stmt1 = AuthFaceController.conn.prepareStatement("SELECT fk_carriage_id FROM carriage_freight_order_item WHERE fk_carriage_id = ?");
        stmt1.setString(1, carriageId);
        ResultSet rs1 = stmt1.executeQuery();
        while (rs1.next()) {
            list1.add(rs1.getString("fk_carriage_id"));
        }
        PreparedStatement stmt2 = AuthFaceController.conn.prepareStatement("SELECT fk_foi_id FROM carriage_freight_order_item WHERE fk_foi_id = ?");
        stmt2.setString(1, orderId);
        ResultSet rs2 = stmt2.executeQuery();
        while (rs2.next()) {
            list2.add(rs2.getString("fk_foi_id"));
        }
        if (list1.contains(carriageId)) {
            Statement stmt = AuthFaceController.conn.createStatement();
            stmt.executeQuery("DELETE carriage_freight_order_item WHERE fk_carriage_id = '" + carriageId + "'");
        } else if (list2.contains(orderId)) {
            Statement stmt = AuthFaceController.conn.createStatement();
            stmt.executeQuery("DELETE carriage_freight_order_item WHERE fk_foi_id = '" + orderId + "'");
        }
        Statement stmt = AuthFaceController.conn.createStatement();
        stmt.executeQuery("INSERT INTO carriage_freight_order_item(fk_carriage_id, fk_foi_id ) VALUES('" + carriageId + "','" + orderId + "')");
    }

    public static String getCarraigeTypeById(String carriage) throws SQLException {
        String s = null;
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT fk_parrent_type_id FROM carriage_type"
                + " WHERE carriage_type_id = "
                + " (SELECT fk_carriage_type_id FROM carriage"
                + " WHERE carriage_id = '" + carriage + "')");
        rs.next();
        s = rs.getString(1);
        stmt.close();
        return s;
    }

    public static List<String> getfilterCarriges(String c_mark, String c_stock, String c_type, String c_subtype, boolean isFree) throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = " SELECT carriage_id, fk_rolling_stock_id "
                + " FROM carriage INNER JOIN carriage_type c_t1 ON carriage.fk_carriage_type_id = c_t1.carriage_type_id"
                + " JOIN carriage_type c_t2 ON c_t1.fk_parrent_type_id = c_t2.carriage_type_id"
                + " WHERE (carriage_mark = NVL(?, carriage_mark)) "
                + " and (carriage_type_name = NVL(?, carriage_type_name)) and (c_t1.carriage_type_name = NVL(?, c_t1.carriage_type_name))";
        PreparedStatement stmt = AuthFaceController.conn.prepareStatement(sql);
        stmt.setString(1, c_mark);
        stmt.setString(2, c_type);
        stmt.setString(3, c_subtype);
        ResultSet rs = stmt.executeQuery();
        if ((" ").equalsIgnoreCase(c_stock) || c_stock == null) {
            while (rs.next()) {
                list.add(rs.getString("carriage_id"));
            }
        } else if (("null").equalsIgnoreCase(c_stock)) {
            while (rs.next()) {
                String s = rs.getString("fk_rolling_stock_id");
                if (s == null) {
                    list.add(rs.getString("carriage_id"));
                }
            }
        } else {
            while (rs.next()) {
                String s = rs.getString("fk_rolling_stock_id");
                if (s != null) {
                    if (s.equalsIgnoreCase(c_stock)) {
                        list.add(rs.getString("carriage_id"));
                    }
                }
            }
        }
        if (isFree) {
            List<String> freeCarriages = fillAllFreeCarriages();
            List<String> trueList = new ArrayList<>();
            for (String s : freeCarriages) {
                if (list.contains(s)) {
                    trueList.add(s);
                }
            }
            stmt.close();
            return trueList;
        }

        stmt.close();
        return list;
    }

    public static List<String> getfilterLoc(String c_n_carriages, String c_mark, String c_type, String c_rail_type, String c_stock, boolean isFree) throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = " SELECT locomotive_id, rolling_stock_id FROM locomotive "
                + " LEFT JOIN rolling_stock ON rolling_stock.fk_lokomotive_id = locomotive.locomotive_id"
                + " WHERE (number_carriages = NVL(?, number_carriages))"
                + " and (locomotive_mark = NVL(?, locomotive_mark)) and (locomotive_type = NVL(?, locomotive_type))"
                + " and (railroad_type = NVL(?, railroad_type))";
        PreparedStatement stmt = AuthFaceController.conn.prepareStatement(sql);
        stmt.setString(1, c_n_carriages);
        stmt.setString(2, c_mark);
        stmt.setString(3, c_type);
        stmt.setString(4, c_rail_type);
        ResultSet rs = stmt.executeQuery();
        if ((" ").equalsIgnoreCase(c_stock) || c_stock == null) {
            while (rs.next()) {
                list.add(rs.getString("locomotive_id"));
            }
        } else if (("null").equalsIgnoreCase(c_stock)) {
            while (rs.next()) {
                String s = rs.getString("rolling_stock_id");
                if (s == null) {
                    list.add(rs.getString("locomotive_id"));
                }
            }
        } else {
            while (rs.next()) {
                String s = rs.getString("rolling_stock_id");
                if (s != null) {
                    if (s.equalsIgnoreCase(c_stock)) {
                        list.add(rs.getString("locomotive_id"));
                    }
                }
            }
        }
        if (isFree) {
            List<String> freeLoc = fillAllFreeLocomotives();
            List<String> trueList = new ArrayList<>();
            for (String s : freeLoc) {
                if (list.contains(s)) {
                    trueList.add(s);
                }
            }
            stmt.close();
            return trueList;
        }

        stmt.close();
        return list;
    }

    public static void addStockToSchedule(String rol_id) throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery(" UPDATE rolling_stock SET ready = 1 WHERE rolling_stock_id = '" + rol_id + "'");
    }
    //------------------Edit Stock

    public static List<String> getAllNotReadyStocks() throws SQLException {
        List<String> list = new ArrayList<>();
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery(" SELECT rolling_stock_id FROM rolling_stock WHERE ready = 0 ");
        while (rs.next()) {
            list.add(rs.getString("rolling_stock_id"));
        }
        return list;
    }

    public static List<String> getAllNotReadyLocomotives() throws SQLException {
        List<String> list = new ArrayList<>();
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery(" SELECT locomotive_id FROM locomotive "
                + " WHERE locomotive_id NOT IN ( "
                + " SELECT fk_lokomotive_id FROM rolling_stock "
                + " WHERE ready = 1) ");
        while (rs.next()) {
            list.add(rs.getString("locomotive_id"));
        }
        return list;
    }

    public static List<String> getAllCarriagesForStock(String stock_id) throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = " SELECT carriage_id FROM carriage "
                + " WHERE fk_rolling_stock_id = ? ";
        PreparedStatement stmt = AuthFaceController.conn.prepareStatement(sql);
        stmt.setString(1, stock_id);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            list.add(rs.getString("carriage_id"));
        }
        return list;
    }

    public static List<String> getLocoForStock(String stock_id) throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = " SELECT fk_lokomotive_id from rolling_stock "
                + " WHERE rolling_stock_id = ? ";
        PreparedStatement stmt = AuthFaceController.conn.prepareStatement(sql);
        stmt.setString(1, stock_id);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            list.add(rs.getString("fk_lokomotive_id"));
        }
        return list;
    }

    public static List<String> fillAllNotReadyCarriages() throws SQLException {
        List<String> list = new ArrayList<>();
        Statement stmt = AuthFaceController.conn.createStatement();
        ResultSet rs = stmt.executeQuery(" SELECT carriage_id FROM carriage "
                + " WHERE fk_rolling_stock_id NOT IN "
                + " ((SELECT rolling_stock_id FROM rolling_stock WHERE ready = 1)) or fk_rolling_stock_id is NULL");
        while (rs.next()) {
            list.add(rs.getString("carriage_id"));
        }
        return list;
    }

    public static void changeStockLoc(String stock, String prevLoc, String newLoc) throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        ifLocHasStock(prevLoc);
        stmt.executeQuery(" UPDATE rolling_stock SET fk_lokomotive_id = '" + newLoc + "' WHERE rolling_stock_id = '" + stock + "'");
    }

    public static void addCarriageToStock(String carr, String stock) throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        stmt.executeQuery(" UPDATE carriage SET fk_rolling_stock_id = '" + stock + "' WHERE carriage_id = '" + carr + "'");
    }
    
    public static void dropStock(String stock) throws SQLException {
        Statement stmt = AuthFaceController.conn.createStatement();
        stmt.executeQuery(" DELETE rolling_stock WHERE rolling_stock_id = '" + stock + "'");
    }
    //----------------------------
}
