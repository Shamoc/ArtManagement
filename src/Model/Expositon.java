package Model;

import java.util.Date;

public class Expositon {

    private String expoName;
    private String expoDescription;
    private boolean expoStatus;
    private Date startDate;
    private Date endDate;

    public Expositon(String expoName, String expoDescription) {
        this.expoName = expoName;
        this.expoDescription = expoDescription;
        this.setExpoStatus(expoStatus);
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

    public Boolean getExpoStatus() {
        return expoStatus;
    }

    public void setExpoStatus(Boolean expoStatus) {
        this.expoStatus = expoStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
