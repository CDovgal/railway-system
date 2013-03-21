/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package railway.information.system.scheduleformation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Font;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import railway.information.system.databasequery.DatabaseQuerySF;
import railway.information.system.databasequery.DatabaseQueryTF;
import railway.information.system.main.AuthFaceController;
import railway.information.system.main.RailwayInformationSystem;
import railway.information.system.trainformation.TrainFormationController;

/**
 * FXML Controller class
 *
 * @author cdovgal
 */
public class ScheduleFormationController implements Initializable {

    @FXML
    private Menu sf_file;
    @FXML
    private Tab sf_trains_tab;
    @FXML
    private Font x1;
    @FXML
    private Tab sf_schedule_tab;
    @FXML
    private Tab sf_stations_tab;
    @FXML
    private MenuItem sf_logout;
    @FXML
    private ToggleGroup operations;
    @FXML
    private ComboBox<String> station_cbox;
    @FXML
    private ComboBox<String> cities_cbox;
    @FXML
    private ComboBox<String> countries_cbox;
    @FXML
    private RadioButton rbStation;
    @FXML
    private RadioButton rbCity;
    @FXML
    private RadioButton rbCountry;
    ArrayList<String> hash;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hash = new ArrayList<String>();
        try {
            init();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "No connection!", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void CloseButton(ActionEvent event) throws IOException {
        System.exit(0);
    }

    @FXML
    private void sf_trains_tab_selection(Event event) {
    }

    @FXML
    private void sf_schedule_tab_selection(Event event) {
    }

    @FXML
    private void sf_stations_tab_selection(Event event) {
    }

    @FXML
    private void sf_logout_click(ActionEvent event) {
        RailwayInformationSystem.formationStage.hide();
        RailwayInformationSystem.authStage.show();
    }

    @FXML
    private void station_cbox(ActionEvent event) {
    }

    private void init() throws SQLException {
        //station_cbox.setItems(FXCollections.observableArrayList(""));
        station_cbox.setItems(FXCollections.observableArrayList(DatabaseQuerySF.fillMap(new String("station"))));
        cities_cbox.setItems(FXCollections.observableArrayList(DatabaseQuerySF.fillMap(new String("city"))));
        countries_cbox.setItems(FXCollections.observableArrayList(DatabaseQuerySF.fillMap(new String("country"))));
    }

    @FXML
    private void add_element_to_map(ActionEvent event) throws SQLException {
        String tableToAdd = null;
        String nameToFind = null;

        boolean flag = true;

        if (rbStation.isSelected()) {
            tableToAdd = new String("station");
            nameToFind = new String(cities_cbox.getValue().toString());
        } else if (rbCity.isSelected()) {
            tableToAdd = new String("city");
            nameToFind = new String(countries_cbox.getValue().toString());
        } else if (rbCountry.isSelected()) {
            tableToAdd = new String("country");
            nameToFind = new String("useless_argument");
        } else {
            flag = false;
            JOptionPane.showMessageDialog(null,
                    "No selection!", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }

        if (flag) {
            String n = JOptionPane.showInputDialog("Input " + tableToAdd + " name to " + nameToFind);
            String query = new String("{ call add_" + tableToAdd + "('" + n + "','" + nameToFind + "') }");
            AuthFaceController.conn.prepareCall(query).execute();
            init();
        }
    }

    @FXML
    private void delete_element_from_map(ActionEvent event) throws SQLException {
        String deleteFromTable = null;
        String deleteName = null;

        boolean flag = true;

        if (rbStation.isSelected()) {
            deleteFromTable = new String("station");
            deleteName = new String(station_cbox.getValue().toString());
        } else if (rbCity.isSelected()) {
            deleteFromTable = new String("city");
            deleteName = new String(cities_cbox.getValue().toString());
        } else if (rbCountry.isSelected()) {
            deleteFromTable = new String("country");
            deleteName = new String(countries_cbox.getValue().toString());
        } else {
            flag = false;
            JOptionPane.showMessageDialog(null,
                    "No selection!", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }

        if (flag) {
            String n = JOptionPane.showInputDialog("Delete " + deleteName + " from " + deleteFromTable);
            if (n.equals(new String(""))) {
                n = deleteName;
            }
            String query = new String("{ call delete_" + deleteFromTable + "('" + n + "') }");
            hash.add(query);
            //AuthFaceController.conn.prepareCall(query).execute();
            init();
        }
    }

    @FXML
    private void commit(ActionEvent event) throws SQLException {
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
        init();
    }
}
