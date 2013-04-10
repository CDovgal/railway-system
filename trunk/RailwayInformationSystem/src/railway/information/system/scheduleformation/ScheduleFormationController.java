/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package railway.information.system.scheduleformation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.TouchEvent;
import javafx.scene.text.Font;
import javax.swing.JOptionPane;
import railway.information.system.dao.Route;
import railway.information.system.dao.Train;
import railway.information.system.databasequery.DatabaseQuerySF;
import railway.information.system.main.AuthFaceController;
import railway.information.system.main.RailwayInformationSystem;

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
    @FXML
    private TableView<Route> tbl_route;
    @FXML
    private TableColumn<Route,String> tbl_route_station;
    @FXML
    private TableColumn<Route,String> tbl_route_arr_time;
    @FXML
    private TableColumn<Route,String> tbl_route_dep_time;
    @FXML
    private ComboBox<String> train_cbox;

    ArrayList<String> map_commit;
    ObservableList<Train> trainsList;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        map_commit = new ArrayList<>();

         tbl_route_station.setCellValueFactory(new PropertyValueFactory<Route,String>("Station"));
        tbl_route_dep_time.setCellValueFactory(new PropertyValueFactory<Route,String>("Departure_time"));
        tbl_route_arr_time.setCellValueFactory(new PropertyValueFactory<Route,String>("Arrival_time"));
        

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
    private void sf_logout_click(ActionEvent event) {
        RailwayInformationSystem.formationStage.hide();
        RailwayInformationSystem.authStage.show();
    }

    @FXML
    private void station_cbox(ActionEvent event) {
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
            init_map();
        }
    }
    
    @FXML
    private void delete_element_from_map(ActionEvent event) throws SQLException {
        String deleteFromTable = null;
        String deleteName = null;

        boolean flag = true;
        int selected_table = -1;
        int selected_item = -1;
        if (rbStation.isSelected()) {
            selected_table = 1;
            selected_item = station_cbox.getSelectionModel().getSelectedIndex();
            
            deleteFromTable = new String("station");
            deleteName = new String(station_cbox.getValue().toString());

        } else if (rbCity.isSelected()) {
            selected_table = 2;
            selected_item = cities_cbox.getSelectionModel().getSelectedIndex();
            
            deleteFromTable = new String("city");
            deleteName = new String(cities_cbox.getValue().toString());
        } else if (rbCountry.isSelected()) {
            selected_table = 3;
            selected_item = countries_cbox.getSelectionModel().getSelectedIndex();
            
            deleteFromTable = new String("country");
            deleteName = new String(countries_cbox.getValue().toString());
        } else {
            flag = false;
            JOptionPane.showMessageDialog(null,
                    "No selection!", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }

        if (flag) {
            int n = JOptionPane.showConfirmDialog(
                        null,
                        "Delete "+deleteName+"?",
                        "Delete from "+deleteFromTable,            
                        JOptionPane.YES_NO_OPTION);
            
            if ( n == 0 )
            {
                String query = "{ call delete_" + deleteFromTable + "('" + deleteName + "') }";
                map_commit.add(query);
                
                switch(selected_table){
                    case 1: delete_element_from_cbox(station_cbox, selected_item); break;
                    case 2: delete_element_from_cbox(cities_cbox, selected_item); break;
                    case 3: delete_element_from_cbox(countries_cbox, selected_item); break;
                }
                //AuthFaceController.conn.prepareCall(query).execute();
                //init_map();
            }
        }
    }

    @FXML
    private void discard_map_changes(ActionEvent event) throws SQLException {
        init_map();
    }
        
    @FXML
    private void commit_map(ActionEvent event) throws SQLException {
        DatabaseQuerySF.commit_map(map_commit);
        init();
    }
    
// end of map
// ---------------------------------------------------------
// start train
    
    @FXML
    private void select_trains_route(ActionEvent event) throws SQLException {
            int selected_index = train_cbox.getSelectionModel().getSelectedIndex();
            
            tbl_route.setItems(FXCollections.observableArrayList(DatabaseQuerySF.fillRoute(
                trainsList.get(selected_index).getTrain_id(),
                trainsList.get(selected_index).getDeparture_station_id(),
                trainsList.get(selected_index).getArrival_station_id()))
                );
    }

    @FXML
    private void update_trains_tab(ActionEvent event) throws SQLException {
        //train_cbox.getSelectionModel().selectFirst();
        update_train_cbox();
    }
    
    @FXML
    private void add_station_to_route(ActionEvent event) throws SQLException {
        int selected_train_index = train_cbox.getSelectionModel().getSelectedIndex();
        String train_id = trainsList.get(selected_train_index).getTrain_id();
        
        int selected_route_index = tbl_route.getSelectionModel().getSelectedIndex();
        if (selected_route_index == tbl_route.getItems().size()-1)
            return;
        String departure_station_name = tbl_route.getItems().get(selected_route_index).getStation();
        String arrival_station_name = tbl_route.getItems().get(selected_route_index+1).getStation();
        String new_station_name = JOptionPane.showInputDialog("Enter new station between "+departure_station_name+" and " + arrival_station_name);
        String new_travel_time  = JOptionPane.showInputDialog("Enter travel time to "+new_station_name+". Example: HH:MM.");
        String new_stop_time    = JOptionPane.showInputDialog("Enter stop time at "  +new_station_name+". Example: HH:MM.");
        
        if ( new_station_name != null 
          && new_travel_time  != null
          && new_stop_time    != null )
            DatabaseQuerySF.addStationToRoute(train_id,departure_station_name,arrival_station_name,new_station_name,new_travel_time,new_stop_time);
        update_trains_tab(null);
    }

    @FXML
    private void delete_station_from_route(ActionEvent event) throws SQLException {
        // here must be a bug
        // or not
        int size = tbl_route.getItems().size();
        int selected_index = train_cbox.getSelectionModel().getSelectedIndex();
        int selected_index_route = tbl_route.getSelectionModel().getSelectedIndex();
        
        String train_id = trainsList.get(selected_index).getTrain_id();
        String deleted_time = tbl_route.getSelectionModel().getSelectedItem().getDeparture_time();
        String departure_time1 = tbl_route.getItems().get(0).getDeparture_time();
        String departure_time2 = tbl_route.getItems().get(size-1).getDeparture_time();
        String delete_station_name = tbl_route.getItems().get(selected_index_route).getStation();
        
        if ( deleted_time.equals(departure_time1) || deleted_time.equals(departure_time2)   )
        {
            DatabaseQuerySF.deleteTrain(train_id); 
        }
        else
        {
            DatabaseQuerySF.deleteStationFromRoute(train_id, delete_station_name);
        }

        update_trains_tab(null);
    }
    
    @FXML
    private void change_train_name(ActionEvent event) throws SQLException {
        int selected_index = train_cbox.getSelectionModel().getSelectedIndex();
        String new_name = JOptionPane.showInputDialog(
                "Change name from "+trainsList.get(selected_index).getTrain_name()+" to ");
        String train_id = trainsList.get(selected_index).getTrain_id();
        DatabaseQuerySF.ChangeTrainName(train_id,new_name);
        update_trains_tab(null);
    }

    private void init() throws SQLException {
        init_map();
        update_train_cbox();        
    }
    
    private void clear_train_window() {
        if (tbl_route != null)
            tbl_route.getItems().clear();
        
        if (train_cbox != null){
            //train_cbox.getSelectionModel().clearSelection();
            train_cbox.getItems().clear();            
        }
        
        if (trainsList != null)
            trainsList.clear();
    }
    
    private void update_train_cbox() throws SQLException {
        //clear_train_window();
        
        trainsList = FXCollections.observableArrayList(DatabaseQuerySF.fillTrains());
        List<String> trainsName = new ArrayList();
        for(Train t: trainsList)
        {
           if (t.getTrain_name() != null)
            trainsName.add(t.getTrain_name());
           else
            trainsName.add("id: "+t.getTrain_id());
        }
        
        train_cbox.setItems(FXCollections.observableArrayList(trainsName));
    }    
    
    private void delete_element_from_cbox(ComboBox box, int index) {
        box.getItems().remove(index);
    }
    
    private void init_map() throws SQLException {
        station_cbox.setItems(FXCollections.observableArrayList(DatabaseQuerySF.fillMap("station")));
        cities_cbox.setItems(FXCollections.observableArrayList(DatabaseQuerySF.fillMap("city")));
        countries_cbox.setItems(FXCollections.observableArrayList(DatabaseQuerySF.fillMap("country")));
    }

}
