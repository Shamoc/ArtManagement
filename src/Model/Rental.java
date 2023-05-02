package Model;

public class Rental {

    private String rentalInstitution;
    private double rentalPrice;
    private String rentalStatus;

    public Rental(String rentalInstitution, double rentalPrice, String rentalStatus) {
        this.rentalInstitution = rentalInstitution;
        this.rentalPrice = rentalPrice;
        this.rentalStatus = rentalStatus;
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
