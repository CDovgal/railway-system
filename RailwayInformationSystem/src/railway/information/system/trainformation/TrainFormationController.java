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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import railway.information.system.dao.Carriage;
import railway.information.system.dao.Locomotive;

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
    private CheckBox tf_only_free;
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
    private CheckBox tf_loco_free;
    @FXML
    private ComboBox<String> tf_loco_type;
    @FXML
    private ComboBox<String> tf_loco_mark;
    @FXML
    private ComboBox<String> tf_loco_stock;
    @FXML
    private ComboBox<String> tf_loco_num_carr;
    @FXML
    private TextField tf_stock_name;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
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
            tf_choose_pack_order.setItems(FXCollections.observableArrayList(DatabaseQueryTF.fillOrders()));
            tf_choose_pack_stock.setItems(FXCollections.observableArrayList(DatabaseQueryTF.getAllStocks()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "No connection!", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void tf_logout_click(ActionEvent event) {
        RailwayInformationSystem.formationStage.hide();
        RailwayInformationSystem.authStage.show();
    }

    @FXML
    private void tf_only_free_click(MouseEvent event) {
        try {
            if (tf_only_free.isSelected()) {
                tf_carriage.setItems(FXCollections.observableArrayList(DatabaseQueryTF.fillAllFreeCarriages()));
            } else {
                tf_carriage.setItems(FXCollections.observableArrayList(DatabaseQueryTF.fillAllCarriages()));
            }
            tf_carriage.getSelectionModel().selectFirst();
            tf_carriage.setValue(tf_carriage.getSelectionModel().getSelectedItem());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "No connection!", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
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
    private void tfCarriageTypeClick(ActionEvent event) {
    }

    @FXML
    private void tfCarriageMarkClick(ActionEvent event) {
    }

    @FXML
    private void tfCarriageStockClick(ActionEvent event) {
    }

    @FXML
    private void tfCarriageSubtypeClick(ActionEvent event) {
    }

    @FXML
    private void tfLocoFree(ActionEvent event) {
        try {
            if (tf_loco_free.isSelected()) {
                tf_loco.setItems(FXCollections.observableArrayList(DatabaseQueryTF.fillAllFreeLocomotives()));
            } else {
                tf_loco.setItems(FXCollections.observableArrayList(DatabaseQueryTF.fillAllLocomotives()));
            }
            tf_loco.getSelectionModel().selectFirst();
            tf_loco.setValue(tf_loco.getSelectionModel().getSelectedItem());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "No connection!", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void tfLocoType(ActionEvent event) {
    }

    @FXML
    private void tfLocoMark(ActionEvent event) {
    }

    @FXML
    private void tfLocoStock(ActionEvent event) {
    }

    @FXML
    private void tfLocoNumberOfCarriages(ActionEvent event) {
    }

    @FXML
    private void tfAddButtonClick(ActionEvent event) {
        if (tf_carraige_preview.getItems().contains(tf_carriage.getSelectionModel().getSelectedItem())) {
            tf_carr_al_add.setText("Already added!");
        } else {
            tf_carraige_preview.getItems().add(tf_carriage.getSelectionModel().getSelectedItem());
            tf_carr_al_add.setText("");
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
    private void tfLocoRailroadType(ActionEvent event) {
    }

    @FXML
    private void tfCreateNewStock(ActionEvent event) {
    }

    @FXML
    private void tfAddLocoClick(ActionEvent event) {
        if (tf_loco_added.getItems().contains(tf_loco.getSelectionModel().getSelectedItem())) {
            tf_carr_al_add.setText("Already added!");
        } else {
            tf_loco_added.setItems(FXCollections.observableArrayList(tf_loco.getSelectionModel().getSelectedItem()));
            tf_carr_al_add.setText("");
        }
    }

    @FXML
    private void tfChoosePackOrderClick(ActionEvent event) {
    }

    @FXML
    private void tfChoosePackStockClick(ActionEvent event) {
    }
}
