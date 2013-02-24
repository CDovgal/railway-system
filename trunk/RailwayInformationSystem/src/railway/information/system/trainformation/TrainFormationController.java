/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package railway.information.system.trainformation;

import railway.information.system.main.RailwayInformationSystem;
import railway.information.system.databasequery.DatabaseQuery;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import railway.information.system.dao.Carriage;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            tf_carriage.setItems(FXCollections.observableArrayList(DatabaseQuery.fillAllCarriages()));
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
                tf_carriage.setItems(FXCollections.observableArrayList(DatabaseQuery.fillAllFreeCarriages()));
            } else {
                tf_carriage.setItems(FXCollections.observableArrayList(DatabaseQuery.fillAllCarriages()));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "No connection!", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
        Text t = new Text("dsd");
    }

    @FXML
    private void tf_carriage_select(ActionEvent event) {
        try {
            if (tf_carriage.getValue() != null) {
                Carriage carriage = DatabaseQuery.getCarriageByID(tf_carriage.getValue());
                ObservableList<String> list = FXCollections.observableArrayList(
                        "[Number]:  " + carriage.getCarriageId(),
                        "[Mark]:  " + carriage.getCarriageMark(),
                        "[Stock Number]:  " + carriage.getRollingStock(),
                        "[General Type]:  " + carriage.getCarriageParentType(),
                        "[Carriage Type]:  " + carriage.getCarriageType());
                tf_carriage_info.setItems(FXCollections.observableArrayList(list));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "No connection!", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
