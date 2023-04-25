package Model;

import java.util.LinkedHashMap;

public class Expositon {

    private LinkedHashMap<String, ArtWork> expoArtWorkList;
    private String expoName;
    private String expoDescription;

    public Expositon(LinkedHashMap<String, ArtWork> expoArtWorkList, String expoName, String expoDescription) {
        this.expoArtWorkList = expoArtWorkList;
        this.expoName = expoName;
        this.expoDescription = expoDescription;
    }

    public LinkedHashMap<String, ArtWork> getExpoArtWorkList() {
        return expoArtWorkList;
    }

    public void setExpoArtWorkList(LinkedHashMap<String, ArtWork> expoArtWorkList) {
        this.expoArtWorkList = expoArtWorkList;
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
}
