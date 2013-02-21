/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduleformation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.text.Font;
import railwayinformationsystem.RailwayInformationSystem;
import trainformation.TrainFormationController;

/**
 * FXML Controller class
 *
 * @author Oleksander
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
        TrainFormationController.trainFormationStage.hide();
        RailwayInformationSystem.authStage.show();
    }
}
