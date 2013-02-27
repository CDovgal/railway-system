package railway.information.system.dao;

/**
 * Created with IntelliJ IDEA.
 * User: Urgo
 * Date: 2/28/13
 * Time: 12:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class Station {
    String station;
    String city;
    String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {

        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStation() {

        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
