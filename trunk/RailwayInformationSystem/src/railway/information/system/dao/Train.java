/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package railway.information.system.dao;

/**
 *
 * @author Urgo
 */
public class Train {

    public Train(String train_id, String train_name, String departure_station_id, String arrival_station_id) {
        this.train_id = train_id;
        this.train_name = train_name;
        this.departure_station_id = departure_station_id;
        this.arrival_station_id = arrival_station_id;
    }
    private String train_id;
    private String train_name;
    private String departure_station_id;
    private String arrival_station_id;

    public String getTrain_id() {
        return train_id;
    }

    public void setTrain_id(String train_id) {
        this.train_id = train_id;
    }

    public String getTrain_name() {
        return train_name;
    }

    public void setTrain_name(String train_name) {
        this.train_name = train_name;
    }

    public String getDeparture_station_id() {
        return departure_station_id;
    }

    public void setDeparture_station_id(String departure_station_id) {
        this.departure_station_id = departure_station_id;
    }

    public String getArrival_station_id() {
        return arrival_station_id;
    }

    public void setArrival_station_id(String arrival_station_id) {
        this.arrival_station_id = arrival_station_id;
    }
    
 
}
