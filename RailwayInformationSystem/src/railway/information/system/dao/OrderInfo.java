/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package railway.information.system.dao;

/**
 *
 * @author Oleksander
 */
public class OrderInfo {

    String orderId;
    String origin;
    String delivery;
    String goodType;
    String mass;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getGoodType() {
        return goodType;
    }

    public void setGoodType(String goodType) {
        this.goodType = goodType;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }
    
}
