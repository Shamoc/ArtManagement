package Model;

public class ArtWork {
    private String name;
    private String description;
    private String author;
    private int adquisitionYear;
    private String artStyle;
    private Inventory inventoryLocation;

    public ArtWork(String name, String description, String author, int adquisitionYear, String artStyle) {
        this.setName(name);
        this.description = description;
        this.author = author;
        this.adquisitionYear = adquisitionYear;
        this.artStyle = artStyle;
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

    public int getAdquisitionYear() {
        return adquisitionYear;
    }

    public void setAdquisitionYear(int adquisitionYear) {
        this.adquisitionYear = adquisitionYear;
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
}
