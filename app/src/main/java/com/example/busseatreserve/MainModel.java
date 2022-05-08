package com.example.last;

public class MainModel {

    String name, from, to, surl, contact;

    MainModel()
    {

    }

    public MainModel(String name, String from, String to, String surl, String contact) {
        this.name = name;
        this.from = from;
        this.to = to;
        this.surl = surl;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}


