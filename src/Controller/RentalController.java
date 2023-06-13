package Controller;

import DB_Implementation.InstitutionDaoImplementation;
import DB_Implementation.InventoryDaoImplementation;
import Model.Artwork;
import Model.Institution;
import Model.Inventory;
import Model.Rental;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;

public class RentalController {

    private LinkedHashMap<String, Rental> instituteList;
    private static RentalController instituteListInstance;
    private static ArtworkController artworkControllerInstance;
    private LinkedHashMap<String, Artwork> artworkList;
    private InstitutionDaoImplementation instDao;

    /**
     * Constructor for RentalController
     *
     * @see #artworkControllerInstance
     */
    private RentalController() {
        this.instituteList = new LinkedHashMap<>();
        instDao = new InstitutionDaoImplementation();
        artworkControllerInstance = ArtworkController.getInstance();
        artworkList = artworkControllerInstance.getArtworkList();
        Rental rental = new Rental("default");
        rental.setRentalStatus("Paid");
        rental.setRentalPrice(200);
        instituteList.put(rental.getRentalInstitution(),rental);

    }

    /**
     * Method to set an Artwork for Rental
     *
     * @see #setRentPrice(Artwork)
     */
    public void setArtworkForRental() {
        ExpositionController expositionControllerInstance = ExpositionController.getInstance();
        Scanner scanner = new Scanner(System.in);
        //artworkControllerInstance.showArtworks();
        System.out.println("Select Artwork: ");
        String artworkName = scanner.next();
        Artwork artWork = artworkList.get(artworkName.toLowerCase());
        if (artWork != null) {
            //showInstitutes();
            System.out.println("Select Institute: ");
            String artworkLocation = scanner.next();
            if (instituteList.containsKey(artworkLocation.toLowerCase())) {
               /* boolean expoListTest = expositionControllerInstance.getExpoList().containsKey(artWork.getInv_id());
                if (expoListTest) {
                    expoListTest = expositionControllerInstance.onActiveExpo(artWork);
                    if (!expoListTest) {
                        //artWork.setInv_id(artworkLocation);
                        System.out.println(artworkName + " will be rented by " + artWork.getInv_id());
                        setRentPrice(artWork);
                    } else {
                        System.out.println(artworkName + " is not available for rental \n Location: " + artWork.getInv_id());
                        System.out.println("Artwork can not be sent for rental");
                    }
                } else {
                    //artWork.setInv_id(artworkLocation);
                    System.out.println(artworkName + " will be rented by " + artWork.getInv_id());
                    setRentPrice(artWork);
                } */
            }
        } else {
            System.out.println("Artwork not found. Try again");
            setArtworkForRental();
        }
    }

    /**
     * Method to determine an institution rent status
     *
     * @param artWork Artwork to retrieve location of the Institute
     * @return The boolean flag of an Artwork rent status
     */
    public boolean isOnRent(Artwork artWork) {
        if (artWork != null) {
           /* String instContainsArt = artWork.getInv_id();
            if (instContainsArt != null) {
                if (instituteList.containsKey(instContainsArt)) {
                    return true;
                }
            } */
        }
        return false;
    }

    /**
     *
      */
    public boolean isRentPaid(Artwork artWork) {
        if (artWork != null){
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
     *
     */
    public void checkRentalStatus() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select Artwork: ");
        //artworkControllerInstance.showArtworks();
        String selectedArt = scanner.next();
        Artwork artWork = artworkList.get(selectedArt);
        if(artWork != null) {
            if (isOnRent(artWork)) {
               String rentalStatus = getInstituteList().get(artWork.getInv_id()).getRentalStatus();
                System.out.println(rentalStatus);

            } else {
                System.out.println("Artwork is not rented.");
            }
        }
        else {
            System.out.println("Artwork not found. Try again");
            checkRentalStatus();
        }
    }

    /**
     * Method to change the rental status of an Institution
     */
    public void rentalChangeStatus() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select the Artwork: ");
        //artworkControllerInstance.showArtworks();
        String artworkName = scanner.next();
        Artwork artwork = artworkList.get(artworkName.toLowerCase());
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
        String artworkName = scanner.next();
        Artwork artwork = artworkList.get(artworkName.toLowerCase());
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
     * Method to create an Institute and add it to instituteList
     */
    public void createInstitute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Rental Institute name: ");
        String rentName = scanner.next();
        if (!instituteList.containsKey(rentName.toLowerCase())) {
            Rental rental = new Rental(rentName.toLowerCase());
            this.instituteList.put(rental.getRentalInstitution(), rental);
            System.out.println("Success! Institute " + rental.getRentalInstitution() + " was created.");
        } else {
            System.out.println("Name already in use. Try again");
            createInstitute();
        }
    }

    /**
     * Method to print Institution details.
     *
     * @throws SQLException
     */
    public void instDetails() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        showInstitutes();
        System.out.println("Select Institution: ");
        int instID = scanner.nextInt();
        Institution inv = instDao.getInst(instID);
        if (inv!=null) {
            System.out.println("Institution: " + inv.getInstName());
            System.out.println("Institution address: " + inv.getInstAddress());
        } else {
            System.out.println("Institution not found. Please try again");
            instDetails();
        }
    }

    /**
     * Method to delete an Institute and remove it from instituteList
     *
     */
    public void deleteInstitute() {
        Scanner scanner = new Scanner(System.in);
        if (!instituteList.isEmpty()) {
            System.out.println("Institute to delete: ");
            //showInstitutes();
            String instName = scanner.next();
            if (instituteList.containsKey(instName.toLowerCase())) {
                instituteList.remove(instName.toLowerCase());
                System.out.println("Success! Institute " + instName.toLowerCase() + " was deleted");
            } else {
                System.out.println("Exposition not found. Please type the exact name");
                deleteInstitute();
            }
        } else {
            System.out.println("Empty list");
        }
    }

    /**
     * Singleton for RentalController
     * @return The RentalController instance
     */
    public static RentalController getInstance() {
        if (instituteListInstance == null) {
            instituteListInstance = new RentalController();
        }
        return instituteListInstance;
    }

    /**
     * Method to access instituteList
     *
     * @return The instituteList
     */
    public LinkedHashMap<String, Rental> getInstituteList() {
        return instituteList;
    }

    /**
     * Method to print all the Institutes in instituteList
     */
    public void showInstitutes() throws SQLException {
        if (!instDao.getInstitution().isEmpty()) {
            int index = 1;
            for (Institution inst : instDao.getInstitution()) {
                System.out.println(index + ". " + inst.getInstName());
                index++;
            }
        } else {
            System.out.println("Institutions List is empty");
        }
    }

    /**
     * Method to set the rent price and rental status of an Artwork
     * 
     * @param artWork Determines which artwork to set
     */
    private void setRentPrice(Artwork artWork) {
        Scanner scanner = new Scanner(System.in);
        boolean done = false;
        while (!done) {
            System.out.println("Set rent price. (No cents)");
            int artRentPrice = scanner.nextInt();
            if (artRentPrice > 0) {
                instituteList.get(artWork.getInv_id()).setRentalPrice(artRentPrice);
                instituteList.get(artWork.getInv_id()).setPendingRental(artRentPrice);
                instituteList.get(artWork.getInv_id()).setRentalStatus("unpaid");
                done = true;
            } else {
                System.out.println("Error price can not be 0 or lower. \n Try again. ");
            }
        }
    }
}
