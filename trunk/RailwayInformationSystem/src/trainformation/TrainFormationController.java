/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trainformation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseDragEvent;
import javafx.stage.Stage;
import railwayinformationsystem.*;
/**
 * FXML Controller class
 *
 * @author Oleksander
 */
public class TrainFormationController implements Initializable {
    @FXML
    private MenuItem tf_logout;
    @FXML
    private ChoiceBox<String> cs_carriage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void tf_logout_click(ActionEvent event) {
        RailwayInformationSystem.formationStage.hide();
        RailwayInformationSystem.authStage.show();
    }
}
