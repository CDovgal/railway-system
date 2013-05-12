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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javax.swing.JOptionPane;
import railway.information.system.dao.MapElement;
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
    private Tab sf_stations_tab;
    @FXML
    private MenuItem sf_logout;
    @FXML
    private ToggleGroup operations;
    @FXML
    private ComboBox<String> stations_cbox;
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

    ObservableList<Train> trainsList;
    
    /// new ---
    ArrayList<MapElement> stations;
    ArrayList<MapElement> cities;
    ArrayList<MapElement> countries;

    
    private List get_name(ArrayList<MapElement> list){
        ArrayList<String> names = new ArrayList<>();
        for(MapElement a: list){
            names.add(a.getElement_name());
        }
        return names;
    }
    /// ---
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stations_cbox.getItems().clear();
        countries_cbox.getItems().clear();
        cities_cbox.getItems().clear();

        tbl_route_station.setCellValueFactory(new PropertyValueFactory<Route,String>("Station"));
        tbl_route_dep_time.setCellValueFactory(new PropertyValueFactory<Route,String>("Departure_time"));
        tbl_route_arr_time.setCellValueFactory(new PropertyValueFactory<Route,String>("Arrival_time"));
    }
// ------
// menubar
    private void CloseButton(ActionEvent event) throws IOException {
        System.exit(0);
    }

    @FXML
    private void sf_logout_click(ActionEvent event) {
        RailwayInformationSystem.formationStage.hide();
        RailwayInformationSystem.authStage.show();
    }
    
// end menubar
// -----
// start map
        
    void countries_init() throws SQLException {
        countries = DatabaseQuerySF.get_all_stations();
    }
    
    void cities_init(String country_id) throws SQLException {
        cities = DatabaseQuerySF.get_cities(country_id);
    }
    
    void stations_init(String city_id) throws SQLException {
        stations = DatabaseQuerySF.get_stations(city_id);
    }
    
    @FXML
    private void ClickOnCountry(MouseEvent event) {
        try {
            countries_init();
            countries_cbox.setItems(FXCollections.observableArrayList(get_name(countries)));

            cities_cbox.getItems().clear();            
            
            stations_cbox.getItems().clear();
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleFormationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void ClickOnCity(MouseEvent event) {
        int country_id_index = countries_cbox.getSelectionModel().getSelectedIndex();
        if (country_id_index == -1)
            return;
        
        String country_id = countries.get(country_id_index).getElement_id();
        try {
            cities_init(country_id);
            cities_cbox.setItems(FXCollections.observableArrayList(get_name(cities)));
            stations_cbox.getItems().clear();
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleFormationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    @FXML
    private void ClickOnStation(MouseEvent event) {
        int city_id_index = cities_cbox.getSelectionModel().getSelectedIndex();
        if (city_id_index == -1)
            return;
        
        String city_id = cities.get(city_id_index).getElement_id();
        try {
            stations_init(city_id);
            stations_cbox.setItems(FXCollections.observableArrayList(get_name(stations)));
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleFormationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void add_element_to_map(ActionEvent event) throws SQLException {
        String tableToAdd = null;
        String nameToFind = null;

        boolean flag = true;

        if (rbStation.isSelected()) {
            tableToAdd = "station";
            nameToFind = cities_cbox.getValue().toString();
        } else if (rbCity.isSelected()) {
            tableToAdd = "city";
            nameToFind = countries_cbox.getValue().toString();
        } else if (rbCountry.isSelected()) {
            tableToAdd = "country";
            nameToFind = "useless_argument";
        } else {
            flag = false;
            JOptionPane.showMessageDialog(null,
                    "No selection!", "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }

        if (flag) {
            String n = JOptionPane.showInputDialog("Input " + tableToAdd + " name to " + nameToFind);
            String query = "{ call add_" + tableToAdd + "('" + n + "','" + nameToFind + "') }";
            AuthFaceController.conn.prepareCall(query).execute();
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
            selected_item = stations_cbox.getSelectionModel().getSelectedIndex();
            
            deleteFromTable = "station";
            deleteName = stations_cbox.getValue().toString();

        } else if (rbCity.isSelected()) {
            selected_table = 2;
            selected_item = cities_cbox.getSelectionModel().getSelectedIndex();
            
            deleteFromTable = "city";
            deleteName = cities_cbox.getValue().toString();
        } else if (rbCountry.isSelected()) {
            selected_table = 3;
            selected_item = countries_cbox.getSelectionModel().getSelectedIndex();
            
            deleteFromTable = "country";
            deleteName = countries_cbox.getValue().toString();
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
                AuthFaceController.conn.prepareCall(query).execute();
            }
        }
    }
    
// end of map
// ---------------------------------------------------------
// start train
    private void select_trains_route() throws SQLException {
            int selected_index = train_cbox.getSelectionModel().getSelectedIndex();
            if (selected_index != -1)
            tbl_route.setItems(FXCollections.observableArrayList(DatabaseQuerySF.fillRoute(
                trainsList.get(selected_index).getTrain_id(),
                trainsList.get(selected_index).getDeparture_station_id(),
                trainsList.get(selected_index).getArrival_station_id()))
                );
    }
    
    @FXML
    private void add_station_to_route(ActionEvent event) throws SQLException {
        int selected_train_index = train_cbox.getSelectionModel().getSelectedIndex();
        if (selected_train_index == -1)
            return;
        String train_id = trainsList.get(selected_train_index).getTrain_id();
        
        int selected_route_index = tbl_route.getSelectionModel().getSelectedIndex();
        if (selected_route_index == tbl_route.getItems().size()-1)
            return;
        if (selected_route_index == -1)
            return;
        String departure_station_name = tbl_route.getItems().get(selected_route_index).getStation();
        String arrival_station_name = tbl_route.getItems().get(selected_route_index+1).getStation();
        
        String new_station_name = JOptionPane.showInputDialog("Enter new station between "+departure_station_name+" and " + arrival_station_name);
        if ( new_station_name == null )
            return;
        
        String new_travel_time  = JOptionPane.showInputDialog("Enter travel time to "+new_station_name+". Format: HH24:MM.");
        if ( new_travel_time == null )
            return;
        
        String new_stop_time    = JOptionPane.showInputDialog("Enter stop time at "  +new_station_name+". FOrmat: HH24:MM.");
        if ( new_stop_time == null )
            return;

        DatabaseQuerySF.addStationToRoute(train_id,departure_station_name,arrival_station_name,new_station_name,new_travel_time,new_stop_time);
        
        select_trains_route();
    }

    @FXML
    private void delete_station_from_route(ActionEvent event) throws SQLException {
        int size = tbl_route.getItems().size();
        int selected_index = train_cbox.getSelectionModel().getSelectedIndex();
        if (selected_index == -1)
            return;
        int selected_index_route = tbl_route.getSelectionModel().getSelectedIndex();
        if (selected_index_route == -1)
            return;
        
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
        select_trains_route();
    }
    
    @FXML
    private void change_train_name(ActionEvent event) throws SQLException {
        int selected_index = train_cbox.getSelectionModel().getSelectedIndex();
        if ( selected_index == -1)
            return;
        String new_name = JOptionPane.showInputDialog(
                "Change name from "+trainsList.get(selected_index).getTrain_name()+" to ");
        String train_id = trainsList.get(selected_index).getTrain_id();
        DatabaseQuerySF.ChangeTrainName(train_id,new_name);
    }
    
    private void fill_train_cbox() throws SQLException {        
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

    @FXML
    private void change_arrival_time(ActionEvent event) throws SQLException {
        int selected_index = train_cbox.getSelectionModel().getSelectedIndex();
        if (selected_index == -1)
            return;
        String train_id = trainsList.get(selected_index).getTrain_id();
        
        selected_index = tbl_route.getSelectionModel().getSelectedIndex();
        if (selected_index == -1)
            return;
        String station_name = tbl_route.getItems().get(selected_index).getStation();
        
        String new_time = JOptionPane.showInputDialog(
                "Enter new time. Format: HH24:MM");
        
        if (new_time != null)
        {
            DatabaseQuerySF.change_departure_time(train_id, station_name, new_time);
            select_trains_route();
        }
    }

    @FXML
    private void ClickOnTrain(MouseEvent event) {
        try {
            fill_train_cbox();
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleFormationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void HiddingBox(Event event) {
        try {
            select_trains_route();
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleFormationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void change_travel_time(ActionEvent event) {
        int selected_index = train_cbox.getSelectionModel().getSelectedIndex();
        if (selected_index == -1)
            return;
        String train_id = trainsList.get(selected_index).getTrain_id();
        
        selected_index = tbl_route.getSelectionModel().getSelectedIndex();
        System.out.println(selected_index);
        if ( selected_index < 1 )
            return;
        String station_name = tbl_route.getItems().get(selected_index-1).getStation();
        
        String new_travel_time = JOptionPane.showInputDialog(
                "Enter new travel time. Format: HH24:MM");
        
        if (new_travel_time != null)
            try {
                DatabaseQuerySF.change_travel_time(train_id, station_name, new_travel_time);
                select_trains_route();
            } catch (SQLException ex) {
                Logger.getLogger(ScheduleFormationController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
