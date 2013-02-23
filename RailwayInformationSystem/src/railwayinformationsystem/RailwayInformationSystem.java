/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package railwayinformationsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Oleksander
 */
public class RailwayInformationSystem extends Application {
    public static Stage authStage;
    public static Stage formationStage;
    @Override
    public void start(Stage stage) throws Exception {
        RailwayInformationSystem.authStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("AuthFace.fxml"));
        
        Scene scene = new Scene(root);
        RailwayInformationSystem.authStage.setScene(scene);
        RailwayInformationSystem.authStage.setResizable(false);
        RailwayInformationSystem.authStage.setTitle("DDI System");
        RailwayInformationSystem.authStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
