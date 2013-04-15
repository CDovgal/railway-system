/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package railway.information.system.dao;

/**
 *
 * @author cdovgal
 */
public class MapElement {

    public MapElement(String element_id, String element_name) {
        this.element_id = element_id;
        this.element_name = element_name;
    }

    public String getElement_id() {
        return element_id;
    }

    public void setElement_id(String element_id) {
        this.element_id = element_id;
    }

    public String getElement_name() {
        return element_name;
    }

    public void setElement_name(String element_name) {
        this.element_name = element_name;
    }
    
    private String element_id;
    private String element_name;
    
}
