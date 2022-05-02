package com.example.busseatreserve;

public class LeaveModel {

    String name;
    String endDate;
    String startDate;
    String pno;
    String reason;

    //constructor
    public LeaveModel() {

    }

    //overloaded constructor
    public LeaveModel(String name, String endDate, String startDate, String pno, String reason) {
        this.name = name;
        this.endDate = endDate;
        this.startDate = startDate;
        this.pno = pno;
        this.reason = reason;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


}
