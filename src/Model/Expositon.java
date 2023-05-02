package Model;

public class Expositon {

    private String expoName;
    private String expoDescription;
    private Boolean expoStatus;

    public Expositon(String expoName, String expoDescription, Boolean expoStatus) {
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
}
