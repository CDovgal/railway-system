/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package railway.information.system.main;

import railway.information.system.databasequery.DatabaseQueryTF;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import railway.information.system.trainformation.TrainFormationController;

/**
 *
 * @author Oleksander
 */
public class AuthFaceController implements Initializable {
    public static Connection conn;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;
    @FXML
    private Font x1;
    @FXML
    private Font x2;
    @FXML
    private Label labelAuthError;

    @FXML
    private void buttonAuthOk(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        String status = null;
        if (conn == null) {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE", "TrainFormation", "TrainFormation");
        }
        if (conn == null) {
            labelAuthError.setText("DataBase Error Connection");
        } else {
            //status = DatabaseQueryTF.checkAuth(loginField.getText(), passwordField.getText());
           // if (status == null) {
           //    labelAuthError.setText("Invalid user login or password");
           // } else {
            status = "TrainFormation";
                RailwayInformationSystem.authStage.hide();
                RailwayInformationSystem.formationStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource(status + ".fxml"));
                
                Scene scene = new Scene(root);
                RailwayInformationSystem.formationStage.setScene(scene);
                RailwayInformationSystem.formationStage.setResizable(false);
                RailwayInformationSystem.formationStage.setTitle("DDI " + status);
                RailwayInformationSystem.formationStage.show();
          //  }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Locale.setDefault(new Locale("EN"));
        loginField.setText("");
        passwordField.setText("");
    }

    @FXML
    private void buttonAuthCancel(ActionEvent event) throws IOException {
        System.exit(0);
    }
}
