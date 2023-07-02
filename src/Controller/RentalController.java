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
    private static ArtworkDaoImplementation artDao;
    private List<Artwork> artworkList;
    private List<Institution> instList;
    private RentalDaoImplementation rentDao;
    private InstitutionDaoImplementation instDao;
    private ExpositionDaoImplementation expoDao;

    /**
     * Constructor for RentalController
     *
     * @see #artworkInstance
     */
    private RentalController() throws SQLException {
        rentDao = RentalDaoImplementation.getInstance();
        instDao = InstitutionDaoImplementation.getInstance();
        artDao = ArtworkDaoImplementation.getInstance();
        expoDao = ExpositionDaoImplementation.getInstance();
        artworkInstance = ArtworkController.getInstance();
        artworkList = artworkInstance.getArtworkList();
        instInstance = InstitutionController.getInstance();
        this.rentList = rentDao.getRental();
        this.instList = instDao.getInstitution();
    }

    /**
     * Method to set a Rental
     */
    public void createRental() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        artworkInstance.showArtworks();
        System.out.println("Select Artwork: ");
        int artworkID = scanner.nextInt();
        Artwork artwork = artworkList.get(artworkID - 1);
        artworkID = artwork.getArt_id();
        if (artwork != null) {
            instInstance.showInstitutes();
            System.out.println("Select Institute: ");
            int selectedInst = scanner.nextInt();
            Institution inst = instList.get(selectedInst - 1);
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
     * Method to determine an institution rent status
     *
     * @return The boolean flag of an Artwork rent status
     */
    public void isOnRent() throws SQLException {
    }

    /**
     * Method to determine if a Rent has been paid.
     */
    public boolean isRentPaid(Artwork artWork) {
        if (artWork != null) {
           /* String instContainsArt = artWork.getInv_id();
            if (instContainsArt != null) {
                Rental rental = instituteList.get(instContainsArt);
                if (rental.getRentalStatus().equalsIgnoreCase("paid")) {
                    return true;
                }
            }*/
        }
        return false;
    }

    /**
     * Method to determine if an Artwork is being rented
     */
    public void rentDetails() throws SQLException {
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
     * Method to change the rental status of an Institution
     */
    public void rentalChangeStatus() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select the Artwork: ");
        //artworkControllerInstance.showArtworks();
        int artworkID = scanner.nextInt();
        Artwork artwork = artworkList.get(artworkID);
       /* String artworkLocation = artwork.getInv_id();
        if (artworkLocation != null) {
            int artworkRentPrice = instituteList.get(artworkLocation).getPendingRental();
            boolean done = false;
            while (!done) {
                System.out.println("Total pending rent debt is: ");
                System.out.println(artworkRentPrice);
                if (artworkRentPrice <= 0) {
                    System.out.println("Rent has been fully paid");
                    return;
                }
                System.out.println("Please select how much you want to pay from the following options: \n 1. Full Rent \n 2. Partial Payment");
                int userChoice = scanner.nextInt();
                switch (userChoice) {
                    case 1:
                        done = false;
                        while (!done) {
                            System.out.println("Please enter funds: ");
                            int userMoney = scanner.nextInt();
                            if (userMoney >= artworkRentPrice) {
                                System.out.println("Your change is: " + (userMoney - artworkRentPrice));
                                done = true;
                            } else if (userMoney < artworkRentPrice) {
                                System.out.println("Insufficient Payment. Please try again");
                            }
                        }
                        System.out.println("Rent has been paid");
                        instituteList.get(artworkLocation).setRentalStatus("paid");
                        instituteList.get(artworkLocation).setPendingRental(0);
                        done = true;
                        break;
                    case 2:
                        done = false;
                        int userPayment = 0;
                        while (!done) {
                            while (!done) {
                                System.out.println("Please enter the amount to pay: \n Can not be full or more than debt.");
                                userPayment = scanner.nextInt();
                                if (userPayment >= artworkRentPrice) {
                                    System.out.println("Please enter a valid amount. Try again");
                                } else {
                                    done = true;
                                }
                            }
                            done = false;
                            System.out.println("Please enter funds: ");
                            int userMoney = scanner.nextInt();
                            if (userMoney > userPayment) {
                                System.out.println("Your change is: " + (userMoney - userPayment));
                                done = true;
                            } else if (userMoney < userPayment) {
                                System.out.println("Insufficient Payment. Please try again");
                            }

                        }
                        System.out.println("Rent has been paid");
                        instituteList.get(artworkLocation).setRentalStatus("pending");
                        int remainingDebt = artworkRentPrice - userPayment;
                        instituteList.get(artworkLocation).setPendingRental(remainingDebt);
                        done = true;
                        break;
                    default:
                        System.out.println("That is not a valid option");
                }
            }
        } else {
            System.out.println("Artwork not found. Please try again");
            rentalChangeStatus();
        } */
    }

    /**
     * Method to print the defined rented prince of an Artwork
     */
    public void rentedPrice() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select the Artwork: ");
        //artworkControllerInstance.showArtworks();
        int artworkID = scanner.nextInt();
        Artwork artwork = artworkList.get(artworkID);
       /* String artworkLocation = artwork.getInv_id();
        if (artworkLocation != null) {
            System.out.println("Renting Institution: " + artworkLocation);
            int artworkRentPrice = instituteList.get(artworkLocation).getRentalPrice();
            System.out.println("Original rented price: " + artworkRentPrice);
        } else {
            System.out.println("Artwork not found. Please try again");
            rentedPrice();
        } */
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
     * Method to access instituteList
     *
     * @return The instituteList
     */
    public List<Rental> getInstituteList() {
        return rentList;
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

    /**
     * Method to determine if the instList contains an Institution name.
     *
     * @param inst
     * @return
     */
    public boolean instListContains(Institution inst) {
        for (Institution insti : instList) {
            if (insti.getInstName().equalsIgnoreCase(inst.getInstName())) {
                return true;
            }
        }
        return false;
    }
}
