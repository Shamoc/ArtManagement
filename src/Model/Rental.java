package Model;

public class Rental {

    private String rentalInstitution;
    private int rentalPrice;
    private String rentalStatus;
    private int pendingRental;

    /**
     * Constructor method for Rental Class
     *
     * @param rentalInstitution
     */
    public Rental(String rentalInstitution) {
        this.rentalInstitution = rentalInstitution;
    }

    /**
     * Method to access rentalInstitution
     *
     * @return
     */
    public String getRentalInstitution() {
        return rentalInstitution;
    }

    public void setRentalInstitution(String rentalInstitution) {
        this.rentalInstitution = rentalInstitution;
    }

    public int getRentalPrice() {
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

    public int getPendingRental() {
        return pendingRental;
    }

    public void setPendingRental(int pendingRental) {
        this.pendingRental = pendingRental;
    }
}
