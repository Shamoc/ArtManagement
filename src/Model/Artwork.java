package Model;

public class Artwork {

    private int art_id;
    private String name;
    private String description;
    private String author;
    private int acquisitionYear;
    private String artStyle;
    private Integer inv_id;
    private Integer expo_id;

    public Artwork() {}

    public Artwork(int art_id, String name) {
        this.art_id = art_id;
        this.name = name;
    }
    public Artwork(String name, String description, String author, int acquisitionYear, String artStyle, int inv_id) {
        this.setName(name);
        this.description = description;
        this.author = author;
        this.acquisitionYear = acquisitionYear;
        this.artStyle = artStyle;
        this.inv_id = inv_id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAcquisitionYear() {
        return acquisitionYear;
    }

    public void setAcquisitionYear(int acquisitionYear) {
        this.acquisitionYear = acquisitionYear;
    }

    public String getArtStyle() {
        return artStyle;
    }

    public void setArtStyle(String artStyle) {
        this.artStyle = artStyle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInv_id() {
        return inv_id;
    }

    public void setInv_id(int inv_id) {
        this.inv_id = inv_id;
    }

    public int getArt_id() {
        return art_id;
    }

    public void setArt_id(int art_id) {
        this.art_id = art_id;
    }

    public Integer getExpo_id() {
        return expo_id;
    }

    public void setExpo_id(Integer expo_id) {
        this.expo_id = expo_id;
    }
}
