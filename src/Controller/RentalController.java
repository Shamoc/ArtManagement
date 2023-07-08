package Controller;

import DB_Implementation.ArtworkDaoImplementation;
import DB_Implementation.ExpositionDaoImplementation;
import DB_Implementation.InstitutionDaoImplementation;
import DB_Implementation.RentalDaoImplementation;
import Model.Artwork;
import Model.Institution;
import Model.Rental;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class RentalController {

    private List<Rental> rentList;
    private static RentalController rentInstance;
    private static ArtworkController artworkInstance;
    private static InstitutionController instInstance;
    private static InventoryController invInstance;
    private static ArtworkDaoImplementation artDao;
    private RentalDaoImplementation rentDao;
    private InstitutionDaoImplementation instDao;
    private ExpositionDaoImplementation expoDao;

    /**
     * Constructor for RentalController
     *
     * @see #artworkInstance
     */
    private RentalController()
            throws SQLException {
        rentDao = RentalDaoImplementation.getInstance();
        instDao = InstitutionDaoImplementation.getInstance();
        artDao = ArtworkDaoImplementation.getInstance();
        expoDao = ExpositionDaoImplementation.getInstance();
        invInstance = InventoryController.getInstance();
        artworkInstance = ArtworkController.getInstance();
        instInstance = InstitutionController.getInstance();
        this.rentList = rentDao.getRental();
    }

    /**
     * Method to create a Rental
     *
     *
     */
    public void createRental()
            throws SQLException {
        Scanner scanner = new Scanner(System.in);
        invInstance.showArtInInventories();
        System.out.println("Select Artwork: ");
        int artworkID = scanner.nextInt();
        List <Artwork> artList = artDao.getArtInInventories();
        Artwork artwork = artList.get(artworkID - 1);
        artworkID = artwork.getArt_id();
        if (artwork != null) {
            instInstance.showInstitutes();
            System.out.println("Select Institute: ");
            int selectedInst = scanner.nextInt();
            Institution inst = instDao.getInst(selectedInst - 1);
            if (inst != null) {
                if (!rentDao.getIsOnRentArt(artworkID) && !expoDao.getIsOnExpo(artworkID)) {
                    Rental rental = new Rental(inst.getInstName(), inst.getInst_id(), artworkID);
                    setRentPrice(rental);
                    rentDao.add(rental);
                    System.out.println(artwork.getName() + " will be rented by " + inst.getInstName());
                } else {
                    System.out.println("Artwork can not be sent for rental");
                }
            }
        } else {
            System.out.println("Artwork not found. Try again");
            createRental();
        }
    }

    /**
     * Method to print a Rent Details
     *
     */
    public void rentDetails()
            throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("RENT DETAILS");
        List<Institution> institutions = instInstance.showRentedInstitutions();
        System.out.println("Please select an Institution: ");
        int selectedRent = scanner.nextInt();
        Institution inst = institutions.get(selectedRent - 1);
        List<Rental> rents = rentDao.getRentsByInstitution(inst.getInst_id());
        int index = 1;
        for (Rental rent : rents) {
            Artwork art = artDao.getArtwork(rent.getArt_id());
            System.out.println(index + ". " + rent.getRentalPrice());
            System.out.println(rent.getRentalStatus());
            System.out.println(rent.getPendingRental());
            if (art != null) {
                System.out.println(art.getName());
            }
            index++;
        }

    }

    /**
     * Method to change Status and Pending Rent values of a Rent
     *
     *
     */
    public void payRental()
            throws SQLException {
        Scanner scanner = new Scanner(System.in);
        List<Institution> institutions = instInstance.showRentedInstitutions();
        System.out.println("Please select an Institution: ");
        int selectedRent = scanner.nextInt();
        Institution inst = institutions.get(selectedRent - 1);
        showRentsToPay(inst.getInst_id());
        List <Rental> rentList = rentDao.getRentToPay(inst.getInst_id());
        System.out.println("Please select a Rent: ");
        int userRent = scanner.nextInt();
        Rental rent = rentList.get(userRent - 1);

        System.out.println("Please input amount to pay: \n NO CENTS");
        int userMoney = scanner.nextInt();
        int amountDue = rent.getPendingRental();
        if (userMoney > 0 ){
            if (userMoney >= amountDue) {
                int change = amountDue - userMoney;
                rentDao.updateRentStatusPendingRent(rent.getRent_id());
                System.out.println("Success Rent has been paid. \n Your change is: " + change);
            } else {
                System.out.println("Insufficient payment");
            }
        } else {
            System.out.println("Error. Please input an amount more than 0");
        }
    }

    /**
     * Singleton for RentalController
     *
     * @return The RentalController instance
     */
    public static RentalController getInstance() {
        if (rentInstance == null) {
            try {
                rentInstance = new RentalController();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return rentInstance;
    }

    /**
     * Method to print all Unpaid/Pending Rents of a selected Institution
     *
     * @param id Institution ID
     * @throws SQLException
     */
    private void showRentsToPay(int id)
            throws SQLException {
        List<Rental> rentList = rentDao.getRentToPay(id);
        if (!rentList.isEmpty()) {
            int index = 1;
            for (Rental rent : rentList) {
                Artwork art = artDao.getArtwork(rent.getArt_id());
                if (art != null) {
                    System.out.println("Artwork: " + art.getName());
                    System.out.println("Rent price: " + rent.getRentalPrice());
                    System.out.println("Pending Rent: " + rent.getPendingRental());
                }
                index++;
            }
        } else {
            System.out.println("Empty List");
        }
    }

    /**
     * Method to set the rent price and rental status of an Artwork
     *
     * @param rental Determines which rent to set
     */
    private void setRentPrice(Rental rental) {
        Scanner scanner = new Scanner(System.in);
        boolean done = false;
        while (!done) {
            System.out.println("Set rent price (NO CENTS): ");
            int artRentPrice = scanner.nextInt();
            if (artRentPrice > 0) {
                rental.setRentalPrice(artRentPrice);
                rental.setPendingRental(artRentPrice);
                rental.setRentalStatus("Unpaid");
                done = true;
            } else {
                System.out.println("Error price can not be 0 or lower. \n Try again. ");
            }
        }
    }
}
