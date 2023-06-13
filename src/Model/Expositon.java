package Model;

import java.sql.Date;

public class Expositon {

    private int expo_id;
    private String expoName;
    private String expoDescription;
    private boolean expoCompleteStatus;
    private java.sql.Date startDate;
    private java.sql.Date endDate;

    public Expositon() {}
    public Expositon(String expoName, String expoDescription) {
        this.expoName = expoName;
        this.expoDescription = expoDescription;
        this.setExpoCompleteStatus(expoCompleteStatus);
    }

    public String getExpoName() {
        return expoName;
    }

    public void setExpoName(String expoName) {
        this.expoName = expoName;
    }

    public String getExpoDescription() {
        return expoDescription;
    }

    public void setExpoDescription(String expoDescription) {
        this.expoDescription = expoDescription;
    }

    public Boolean getExpoCompleteStatus() {
        return expoCompleteStatus;
    }

    public void setExpoCompleteStatus(Boolean expoCompleteStatus) {
        this.expoCompleteStatus = expoCompleteStatus;
    }

    public java.sql.Date getStartDate() {
        return startDate;
    }

    public void setStartDate(java.sql.Date startDate) {
        this.startDate = startDate;
    }

    public java.sql.Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getExpo_id() {
        return expo_id;
    }

    public void setExpo_id(int expo_id) {
        this.expo_id = expo_id;
    }
}
