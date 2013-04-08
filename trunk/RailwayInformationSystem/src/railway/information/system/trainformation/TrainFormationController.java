/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package railway.information.system.trainformation;

import railway.information.system.main.RailwayInformationSystem;
import railway.information.system.databasequery.DatabaseQueryTF;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import railway.information.system.dao.Carriage;
import railway.information.system.dao.Locomotive;
import railway.information.system.dao.OrderInfo;
import railway.information.system.dao.StockInfo;
import railway.information.system.main.AuthFaceController;

/**
 * FXML Controller class
 *
 * @author Oleksander
 */
public class TrainFormationController implements Initializable {

    @FXML
    private MenuItem tf_logout;
    @FXML
    private ComboBox<String> tf_carriage;
    @FXML
    private ListView<String> tf_carriage_info;
    @FXML
    private ComboBox<String> tf_carriage_type;
    @FXML
    private ComboBox<String> tf_carriage_mark;
    @FXML
    private ComboBox<String> tf_carriage_stock;
    @FXML
    private ComboBox<String> tf_carriage_subtype;
    @FXML
    private ComboBox<String> tf_loco;
    @FXML
    private ListView<String> tf_loco_info;
    @FXML
    private ComboBox<String> tf_loco_type;
    @FXML
    private ComboBox<String> tf_loco_mark;
    @FXML
    private ComboBox<String> tf_loco_stock;
    @FXML
    private ComboBox<String> tf_loco_num_carr;
    @FXML
    private Button tf_add_button;
    @FXML
    private ListView<String> tf_carraige_preview;
    @FXML
    private Button tf_remove_button;
    @FXML
    private ComboBox<String> tf_loco_railroad;
    @FXML
    private Button tf_create_new_stock;
    @FXML
    private ListView<String> tf_loco_added;
    @FXML
    private Label tf_carr_al_add;
    @FXML
    private Button tf_add_loco;
    @FXML
    private ListView<String> tf_pack_order_info;
    @FXML
    private ListView<String> tf_pack_stock_info;
    @FXML
    private ComboBox<String> tf_choose_pack_order;
    @FXML
    private ComboBox<String> tf_choose_pack_stock;
    @FXML
    private Label tf_new_stock_created;
    @FXML
    private ListView<String> tf_pack_carriage_info;
    @FXML
    private ComboBox<String> tf_choose_pack_carriage;
    @FXML
    private Button pack_order;
    @FXML
    private ComboBox<String> tf_carriage_free;
    @FXML
    private ComboBox<String> tf_loco_free;
    @FXML
    private ComboBox<String> es_stock_name;
    @FXML
    private ComboBox<String> es_loco;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            refresh();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "No connection!", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void tf_logout_click(ActionEvent event) throws SQLException {
        AuthFaceController.conn.close();
        RailwayInformationSystem.formationStage.hide();
        RailwayInformationSystem.authStage.show();
    }

    @FXML
    private void tf_carriage_select(ActionEvent event) {
        try {
            if (tf_carriage.getSelectionModel().getSelectedItem() != null) {
                Carriage carriage = DatabaseQueryTF.getCarriageByID(tf_carriage.getValue());
                ObservableList<String> list = FXCollections.observableArrayList(
                        "[Number]:  " + carriage.getCarriageId(),
                        "[Mark]:  " + carriage.getCarriageMark(),
                        "[Stock Number]:  " + carriage.getRollingStock(),
                        "[General Type]:  " + carriage.getCarriageParentType(),
                        "[Carriage Type]:  " + carriage.getCarriageType());
                tf_carriage_info.setItems(FXCollections.observableArrayList(list));
            } else {
                tf_carriage_info.setItems(FXCollections.observableArrayList(""));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "No connection!", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void tfAddButtonClick(ActionEvent event) throws SQLException {
        String carriage = tf_carriage.getSelectionModel().getSelectedItem();
        if (checkCarriagesType(carriage) && DatabaseQueryTF.fillAllFreeCarriages().contains(carriage)) {
            if (tf_carraige_preview.getItems().contains(tf_carriage.getSelectionModel().getSelectedItem())) {
                tf_carr_al_add.setText("Already added!");
            } else {
                tf_carraige_preview.getItems().add(tf_carriage.getSelectionModel().getSelectedItem());
                tf_carr_al_add.setText("");
            }
        } else {
            JOptionPane.showMessageDialog(null,
                    "Stock carriages should have the same type and be free", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void tfRemoveButtonClick(ActionEvent event) {
        if (!tf_carraige_preview.getSelectionModel().getSelectedItems().isEmpty()
                && !tf_carraige_preview.getItems().isEmpty()) {
            tf_carraige_preview.getItems().remove(tf_carraige_preview.getSelectionModel().getSelectedIndex());
        }
    }

    @FXML
    private void tfLocoSelect(ActionEvent event) {
        try {
            if (tf_loco.getSelectionModel().getSelectedItem() != null) {
                Locomotive loco = DatabaseQueryTF.getLocomotiveByID(tf_loco.getValue());
                ObservableList<String> list = FXCollections.observableArrayList(
                        "[Number]:  " + loco.getLocoId(),
                        "[Type]:  " + loco.getLocoType(),
                        "[Mark]:  " + loco.getLocoMark(),
                        "[Stock]:  " + loco.getRollingStock(),
                        "[Carriages]:  " + loco.getNumberOfCarriages(),
                        "[Railroad]:  " + loco.getRailRoadType());
                tf_loco_info.setItems(FXCollections.observableArrayList(list));
            } else {
                tf_loco_info.setItems(FXCollections.observableArrayList(""));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "No connection!", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    @FXML
    private void tfCreateNewStock(ActionEvent event) {
        try {
            if (!tf_loco_added.getItems().isEmpty()) {
                DatabaseQueryTF.addStock(tf_loco_added.getItems().get(0), tf_carraige_preview.getItems());
                tf_new_stock_created.setText("New Rolling Stock Number " + DatabaseQueryTF.lastStock() + " "
                        + "was added");
                tf_carraige_preview.getItems().clear();
                tf_loco_added.getItems().clear();
                refresh();
            } else {
                tf_new_stock_created.setText("Select locomotive!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "No connection", "Error!",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    @FXML
    private void tfAddLocoClick(ActionEvent event) {
        try {
            if (!DatabaseQueryTF.isLocInTrain(tf_loco.getSelectionModel().getSelectedItem())) {
                if (tf_loco_added.getItems().contains(tf_loco.getSelectionModel().getSelectedItem())) {
                    tf_carr_al_add.setText("Already added!");
                } else {
                    tf_loco_added.setItems(FXCollections.observableArrayList(tf_loco.getSelectionModel().getSelectedItem()));
                    tf_carr_al_add.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "The locomotive is already in schedule!", "Error!",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "No connection", "Error!",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    @FXML
    private void tfChoosePackOrderClick(ActionEvent event) {
        try {
            if (tf_choose_pack_order.getSelectionModel().getSelectedItem() != null) {
                OrderInfo oi = DatabaseQueryTF.getOrderInfo(tf_choose_pack_order.getValue());
                ObservableList<String> list = FXCollections.observableArrayList(
                        "[Number]:  " + oi.getOrderId(),
                        "[Type]:  " + oi.getGoodType(),
                        "[Origin]:  " + oi.getOrigin(),
                        "[Delivery]:  " + oi.getDelivery());
                //"[Mass]:  " + oi.getMass());
                tf_pack_order_info.setItems(FXCollections.observableArrayList(list));
            } else {
                tf_pack_order_info.setItems(FXCollections.observableArrayList(""));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "No connection!", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void tfChoosePackStockClick(ActionEvent event) {
        try {
            if (tf_choose_pack_stock.getSelectionModel().getSelectedItem() != null) {
                List<StockInfo> si = DatabaseQueryTF.getStockInfo(tf_choose_pack_stock.getValue());
                ObservableList<String> list = FXCollections.observableArrayList(
                        "[Locomotive]:  " + si.get(0).getLocomotiveId(),
                        "[Carriages]:  ");
                for (StockInfo stock : si) {
                    list.add(stock.getNumberOfCarriage());
                }
                tf_pack_stock_info.setItems(FXCollections.observableArrayList(list));
            } else {
                tf_pack_stock_info.setItems(FXCollections.observableArrayList(""));
            }
            if (tf_choose_pack_stock.getSelectionModel().getSelectedItem() != null) {
                tf_choose_pack_carriage.setItems(FXCollections.observableArrayList(DatabaseQueryTF.fillAllStockCarriages(tf_choose_pack_stock.getValue())));
            } else {
                tf_choose_pack_carriage.setItems(FXCollections.observableArrayList(""));
            }
            tf_choose_pack_carriage.getSelectionModel().selectFirst();
            tf_choose_pack_carriage.setValue(tf_choose_pack_carriage.getSelectionModel().getSelectedItem());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "No connection!", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refresh() throws SQLException {
        tf_carriage.setItems(FXCollections.observableArrayList(DatabaseQueryTF.fillAllCarriages()));      
        tf_carriage_type.setItems(FXCollections.observableArrayList(DatabaseQueryTF.fillAllCarriageTypes()));
        tf_carriage_subtype.setItems(FXCollections.observableArrayList(DatabaseQueryTF.fillAllCarriageSubtypes()));
        tf_carriage_mark.setItems(FXCollections.observableArrayList(DatabaseQueryTF.fillAllCarriageMarks()));
        tf_carriage_stock.setItems(FXCollections.observableArrayList(DatabaseQueryTF.fillAllCarriageStocks()));
        tf_loco.setItems(FXCollections.observableArrayList(DatabaseQueryTF.fillAllLocomotives()));
        tf_loco_type.setItems(FXCollections.observableArrayList(DatabaseQueryTF.fillAllLocomotiveTypes()));
        tf_loco_mark.setItems(FXCollections.observableArrayList(DatabaseQueryTF.fillAllLocomotiveMarks()));
        tf_loco_stock.setItems(FXCollections.observableArrayList(DatabaseQueryTF.fillAllLocomotiveStocks()));
        tf_loco_num_carr.setItems(FXCollections.observableArrayList(DatabaseQueryTF.fillAllLocomotiveNumOfCarraiges()));
        tf_loco_railroad.setItems(FXCollections.observableArrayList(DatabaseQueryTF.fillLocomotiveRailroads()));
        tf_carriage.getSelectionModel().selectFirst();
        tf_carriage.setValue(tf_carriage.getSelectionModel().getSelectedItem());
        tf_loco.getSelectionModel().selectFirst();
        tf_loco.setValue(tf_loco.getSelectionModel().getSelectedItem());
        //----pack
        tf_choose_pack_order.setItems(FXCollections.observableArrayList(DatabaseQueryTF.fillOrders()));
        tf_choose_pack_stock.setItems(FXCollections.observableArrayList(DatabaseQueryTF.getAllOutScheduleFreightStocks()));
        //---
        tf_carriage_free.setItems(FXCollections.observableArrayList("Free", " "));
        tf_loco_free.setItems(FXCollections.observableArrayList("Free", " "));
        //----Edit Sock
        es_stock_name.setItems(FXCollections.observableArrayList(DatabaseQueryTF.getAllNotReadyStocks()));//!!!!!!!!!
        es_loco.setItems(FXCollections.observableArrayList(DatabaseQueryTF.fillAllLocomotives()));
        //------------
    }

    @FXML
    private void tfChoosePackCarriageClick(ActionEvent event) {
        try {
            if (tf_choose_pack_carriage.getSelectionModel().getSelectedItem() != null) {
                Carriage carriage = DatabaseQueryTF.getCarriageByID(tf_choose_pack_carriage.getValue());
                ObservableList<String> list = FXCollections.observableArrayList(
                        "[Number]:  " + carriage.getCarriageId(),
                        "[Mark]:  " + carriage.getCarriageMark(),
                        "[Stock Number]:  " + carriage.getRollingStock(),
                        "[General Type]:  " + carriage.getCarriageParentType(),
                        "[Carriage Type]:  " + carriage.getCarriageType());
                tf_pack_carriage_info.setItems(FXCollections.observableArrayList(list));
            } else {
                tf_pack_carriage_info.setItems(FXCollections.observableArrayList(""));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "No connection!", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    @FXML
    private void packOrderToCarriage(ActionEvent event) {
        try {
            if ((tf_choose_pack_order.getSelectionModel().getSelectedItem() != null) && (tf_choose_pack_carriage.getSelectionModel().getSelectedItem() != null)) {
                DatabaseQueryTF.addOrderForCarriage(tf_choose_pack_carriage.getSelectionModel().getSelectedItem(),
                        tf_choose_pack_order.getSelectionModel().getSelectedItem());
                tf_choose_pack_order.setItems(FXCollections.observableArrayList(DatabaseQueryTF.fillOrders()));
                tf_choose_pack_stock.setItems(FXCollections.observableArrayList(DatabaseQueryTF.getAllStocks()));
            } else {
                System.out.println("empty");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "No connection!", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean checkCarriagesType(String carriage) {
        try {
            if (tf_carraige_preview.getItems().size() != 0) {
                if (!DatabaseQueryTF.getCarraigeTypeById(tf_carraige_preview.getItems().get(0)).
                        equals(DatabaseQueryTF.getCarraigeTypeById(carriage))) {
                    return false;
                }
            } else {
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "No connection!", "Error!",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void updatePacking() {
    }

    @FXML
    private void tfFilterCarClick(ActionEvent event) {
        try {
            boolean isFree = "Free".equals(tf_carriage_free.getSelectionModel().getSelectedItem()) ? true : false;
            String mark = tf_carriage_mark.getSelectionModel().getSelectedItem();
            String stock = tf_carriage_stock.getSelectionModel().getSelectedItem();
            String type = tf_carriage_type.getSelectionModel().getSelectedItem();
            String subtype = tf_carriage_subtype.getSelectionModel().getSelectedItem();
            List<String> list = DatabaseQueryTF.getfilterCarriges((" ").equals(mark) ? null : mark,
                    (" ").equals(stock) ? null : stock,
                    (" ").equals(type) ? null : type,
                    (" ").equals(subtype) ? null : subtype,
                    isFree);
            if (!list.isEmpty()) {
                tf_carriage.setItems(FXCollections.observableArrayList(list));
                tf_carriage.getSelectionModel().selectFirst();
                tf_carriage.setValue(tf_carriage.getSelectionModel().getSelectedItem());
            } else {
                tf_carriage.setItems(FXCollections.observableArrayList(""));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "No connection!", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void tfFilterLocClick(ActionEvent event) {
        try {
            boolean isFree = "Free".equals(tf_loco_free.getSelectionModel().getSelectedItem()) ? true : false;
            String n_carriages = tf_loco_num_carr.getSelectionModel().getSelectedItem();
            String mark = tf_loco_mark.getSelectionModel().getSelectedItem();
            String stock = tf_loco_stock.getSelectionModel().getSelectedItem();
            String type = tf_loco_type.getSelectionModel().getSelectedItem();
            String railtype = tf_loco_railroad.getSelectionModel().getSelectedItem();
            List<String> list = DatabaseQueryTF.getfilterLoc((" ").equals(n_carriages) ? null : n_carriages,
                    (" ").equals(mark) ? null : mark,
                    (" ").equals(type) ? null : type,
                    (" ").equals(railtype) ? null : railtype,
                    (" ").equals(stock) ? null : stock,
                    isFree);
            if (!list.isEmpty()) {
                tf_loco.setItems(FXCollections.observableArrayList(list));
                tf_loco.getSelectionModel().selectFirst();
                tf_loco.setValue(tf_loco.getSelectionModel().getSelectedItem());
            } else {
                tf_loco.setItems(FXCollections.observableArrayList(""));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "No connection!", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void addStockToSchedule(ActionEvent event) {
        try {
            if (tf_choose_pack_stock.getSelectionModel().getSelectedItem() != null) {
                String s = tf_choose_pack_stock.getSelectionModel().getSelectedItem();
                DatabaseQueryTF.addStockToSchedule(tf_choose_pack_stock.getSelectionModel().getSelectedItem());
                tf_choose_pack_stock.setItems(FXCollections.observableArrayList(DatabaseQueryTF.getAllOutScheduleFreightStocks()));
                tf_choose_pack_carriage.setItems(FXCollections.observableArrayList(""));
                JOptionPane.showMessageDialog(null,
                    "Train added!", "Good news!",
                    JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null,
                    "Choose stock!", "Error!",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "No connection!", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    //--------------------------------------------------------------------------------------------------------------------------------
    //Edit stock

    @FXML
    private void esStockSelect(ActionEvent event) {
        
        
    }

    @FXML
    private void tfLocSelect(ActionEvent event) {
    }



    
}
