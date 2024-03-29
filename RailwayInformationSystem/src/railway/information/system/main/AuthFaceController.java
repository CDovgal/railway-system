/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package railway.information.system.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import railway.information.system.databasequery.DatabaseQueryTF;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.jar.JarEntry;
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
import javax.swing.JOptionPane;
import railway.information.system.trainformation.TrainFormationController;
import sun.misc.IOUtils;

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
    private TextField connection_ip;
    @FXML
    private TextField connection_port;
    @FXML
    private TextField connection_sid;

    @FXML
    private void buttonAuthOk(ActionEvent event) throws ClassNotFoundException, IOException, URISyntaxException, SQLException {
        try {
            String status = null;
            if ((conn == null) || (conn.isClosed())) {
                //lsnrctl status
                //jdbc:oracle:thin:@192.168.0.101:1521:XE
                conn = DriverManager.getConnection("jdbc:oracle:thin:@" 
                                                    + connection_ip.getText() + ":" 
                                                    + connection_port.getText() + ":" 
                                                    + connection_sid.getText(),
                                                    "TrainFormation", "TrainFormation");
            }
            if (conn == null) {
                labelAuthError.setText("DataBase Error Connection");
            } else if (!conn.isClosed()) {
                status = DatabaseQueryTF.checkAuth(loginField.getText(), passwordField.getText());
                if (status == null) {
                    labelAuthError.setText("Invalid user login or password");
                } else {
                    //status = "TrainFormation";
                    RailwayInformationSystem.authStage.hide();
                    RailwayInformationSystem.formationStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource(status + ".fxml"));

                    Scene scene = new Scene(root);
                    RailwayInformationSystem.formationStage.setScene(scene);
                    RailwayInformationSystem.formationStage.setResizable(false);
                    RailwayInformationSystem.formationStage.setTitle("DDI " + status);
                    RailwayInformationSystem.formationStage.show();
                }
            }
       } catch (Exception ex) {
            labelAuthError.setText("DataBase Error Connection");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection_ip.setText("127.0.0.1");
        connection_port.setText("1521");
        connection_sid.setText("XE");
        Locale.setDefault(new Locale("EN"));
        loginField.setText("Drop");
        passwordField.setText("Drop");
    }

    @FXML
    private void buttonAuthCancel(ActionEvent event) throws IOException {
        System.exit(0);
    }
    
}
