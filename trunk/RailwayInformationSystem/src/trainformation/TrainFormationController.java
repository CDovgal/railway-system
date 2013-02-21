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
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import railwayinformationsystem.*;
/**
 * FXML Controller class
 *
 * @author Oleksander
 */
public class TrainFormationController implements Initializable {
    public static Stage trainFormationStage;  
    @FXML
    private MenuItem tf_logout;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void tf_logout_click(ActionEvent event) {
        TrainFormationController.trainFormationStage.hide();
        RailwayInformationSystem.authStage.show();
    }
}
