package Model;

public class Rental {

    private ArtWork artWork;
    private String rentalInstitution;
    private double rentalPrice;
    private String rentalStatus;

    public Rental(ArtWork artWork, String rentalInstitution, double rentalPrice, String rentalStatus) {
        this.artWork = artWork;
        this.rentalInstitution = rentalInstitution;
        this.rentalPrice = rentalPrice;
        this.rentalStatus = rentalStatus;
    }

    public ArtWork getArtWork() {
        return artWork;
    }

    public void setArtWork(ArtWork artWork) {
        this.artWork = artWork;
    }

    public String getRentalInstitution() {
        return rentalInstitution;
    }

    public void setRentalInstitution(String rentalInstitution) {
        this.rentalInstitution = rentalInstitution;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public String getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(String rentalStatus) {
        this.rentalStatus = rentalStatus;
    }
}
