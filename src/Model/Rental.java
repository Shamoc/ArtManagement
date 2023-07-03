package Model;

public class Rental {

    private int rent_id;
    private String rentalInstitution;
    private int rentalPrice;
    private String rentalStatus;
    private int pendingRental;
    private int inst_id;
    private int art_id;

    public Rental() {}

    public Rental(String rentalStatus, int inst_id) {
        this.rentalStatus = rentalStatus;
        this.inst_id = inst_id;
    }

    /**
     * Constructor method for Rental Class
     *
     * @param rentalInstitution
     */
    public Rental(String rentalInstitution, int inst_id, int art_id) {
        this.rentalInstitution = rentalInstitution;
        this.inst_id = inst_id;
        this.art_id = art_id;
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

    public int getRent_id() {
        return rent_id;
    }

    public void setRent_id(int rent_id) {
        this.rent_id = rent_id;
    }

    public int getInst_id() {
        return inst_id;
    }

    public void setInst_id(int inst_id) {
        this.inst_id = inst_id;
    }

    public int getArt_id() {
        return art_id;
    }

    public void setArt_id(int art_id) {
        this.art_id = art_id;
    }
}
