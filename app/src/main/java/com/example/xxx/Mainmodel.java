package com.example.xxx;

public class Mainmodel {
    String travel_name,surl;
    String bus_no;
    String seat_no;

    Mainmodel(){

    }
    public Mainmodel(String travel_name, String surl, String bus_no, String seat_no) {
        this.travel_name = travel_name;
        this.surl = surl;
        this.bus_no = bus_no;
        this.seat_no = seat_no;
    }

    public String getTravel_name() {
        return travel_name;
    }

    public void setTravel_name(String travel_name) {
        this.travel_name = travel_name;
    }

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }

    public String getBus_no() {
        return bus_no;
    }

    public void setBus_no(String bus_no) {
        this.bus_no = bus_no;
    }

    public String getSeat_no() {
        return seat_no;
    }

    public void setSeat_no(String seat_no) {
        this.seat_no = seat_no;
    }
}


