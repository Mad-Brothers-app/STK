package com.example.busseatreserve;

public class ModelDriver {
    String name;
    String Email;
    String type;
    String image;
    String reason;
    String startDate;
    String endDate;
    String pno;
    String route;


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public ModelDriver(String name, String reason, String pno, String startDate, String endDate, String route) {
        this.name = name;
        this.reason = reason;
        this.pno = pno;
        this.startDate = startDate;
        this.endDate = endDate;
        this.route = route;
    }


    public ModelDriver(String name, String email, String type, String image) {
        this.name = name;
        this.Email = email;
        this.type = type;
        this.image = image;
    }

    public ModelDriver() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return Email;
    }

    public String getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
