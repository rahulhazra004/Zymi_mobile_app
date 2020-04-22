package com.zymiapp.apps.Model;

import java.util.List;

public class Main_Order {

    private String order_id;
    private String tracking_id;
    private String seller_name;
    private String delivery_address;
    private  String tracking_url;
    private List<Order> orders;

    public Main_Order(String order_id, String res_margin, List<Order> orders,String tracking_id,String seller_name,String delivery_address,String tracking_url) {
        this.order_id = order_id;
        this.orders = orders;
        this.tracking_id = tracking_id;
        this.seller_name=seller_name;
        this.delivery_address= delivery_address;
        this.tracking_url= tracking_url;
    }

    public String getOrder_id() {
        return order_id;
    }


    public List<Order> getOrders() {
        return orders;
    }

    public String getTracking_id() {
        return tracking_id;
    }
    public String getSeller_name() {
        return seller_name;
    }
    public String getDelivery_address() {
        return delivery_address;
    }
    public String getTracking_url(){return tracking_url;}
}
