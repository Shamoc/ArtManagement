package Model;

public class Rental {

    private String rentalInstitution;
    private int rentalPrice;
    private String rentalStatus;

    public Rental(String rentalInstitution) {
        this.rentalInstitution = rentalInstitution;
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

    public void setRentalPrice(int rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public String getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(String rentalStatus) {
        this.rentalStatus = rentalStatus;
    }
}
