/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package railway.information.system.dao;

/**
 *
 * @author Urgo
 */
public class Route {

    public Route() {
    }
    private String station;
    private String arrival_time; 
    private String departure_time;


    public Route(String i_station, String i_arrival_time, String i_departure_time) {
         station = i_station;
         arrival_time = i_arrival_time;
         departure_time = i_departure_time;
         
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }
}
