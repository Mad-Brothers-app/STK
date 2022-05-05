package com.example.busseatreserve;
//creating variable
public class ModelLeave {
    private String leaveID;
    private String leaveType;
    private String route;
    private String Sdate;
    private String Edate;
    private String name;
    private String pno;
    private String timeStamp;
    private String uid;

    public ModelLeave() {
    }

    //overloaded constructor
    public ModelLeave(String leaveID, String leaveType, String route, String sdate, String edate, String name, String pno, String timeStamp, String uid) {
        this.leaveID = leaveID;
        this.leaveType = leaveType;
        this.route = route;
        Sdate = sdate;
        Edate = edate;
        this.name = name;
        this.pno = pno;
        this.timeStamp = timeStamp;
        this.uid = uid;
    }


    public String getLeaveID() {
        return leaveID;
    }

    public void setLeaveID(String leaveID) {
        this.leaveID = leaveID;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getSdate() {
        return Sdate;
    }

    public void setSdate(String sdate) {
        Sdate = sdate;
    }

    public String getEdate() {
        return Edate;
    }

    public void setEdate(String edate) {
        Edate = edate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
