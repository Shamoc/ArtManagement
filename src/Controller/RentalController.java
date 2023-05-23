package Controller;

import Model.ArtWork;
import Model.Expositon;
import Model.Inventory;
import Model.Rental;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class RentalController {

    private LinkedHashMap<String, Rental> instituteList;
    private static RentalController instituteListInstance = null;
    private static ArtworkController artworkControllerInstance;
    private LinkedHashMap<String, ArtWork> artworkList = artworkControllerInstance.getArtworkList();

    /**
     * Constructor for RentalController
     *
     * @see #artworkControllerInstance
     */
    private RentalController() {
        this.instituteList = new LinkedHashMap<>();
        artworkControllerInstance = ArtworkController.getInstance();
        Rental rental = new Rental("Default");
        rental.setRentalStatus("Paid");
        rental.setRentalPrice(200);
        instituteList.put(rental.getRentalInstitution(),rental);

    }

    /**
     * Method to set an Artwork for Rental
     *
     * @see #showInstitutes()
     * @see #setRentPrice(ArtWork) 
     */
    public void setArtworkForRental() {
        ExpositionController expositionControllerInstance = ExpositionController.getInstance();
        Scanner scanner = new Scanner(System.in);
        artworkControllerInstance.showArtworks();
        System.out.println("Select Artwork: ");
        String artworkName = scanner.next();
        ArtWork artWork = artworkList.get(artworkName.toLowerCase());
        if (artWork != null) {
            showInstitutes();
            System.out.println("Select Institute: ");
            String artworkLocation = scanner.next();
            if (instituteList.containsKey(artworkLocation.toLowerCase())) {
                boolean expoListTest = expositionControllerInstance.getExpoList().containsKey(artWork.getInventoryLocation());
                if (expoListTest) {
                    expoListTest = expositionControllerInstance.onActiveExpo(artWork);
                    if (!expoListTest) {
                        artWork.setInventoryLocation(artworkLocation);
                        System.out.println(artworkName + " will be rented by " + artWork.getInventoryLocation());
                        setRentPrice(artWork);
                    } else {
                        System.out.println(artworkName + " is not available for rental \n Location: " + artWork.getInventoryLocation());
                        System.out.println("Artwork can not be sent for rental");
                    }
                } else {
                    artWork.setInventoryLocation(artworkLocation);
                    System.out.println(artworkName + " will be rented by " + artWork.getInventoryLocation());
                    setRentPrice(artWork);
                }
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
    public boolean onActiveRent(ArtWork artWork) {
        if (artWork != null) {
            String instContainsArt = artWork.getInventoryLocation();
            if (instContainsArt != null) {
                if (!instituteList.containsKey(instContainsArt)) {
                    return false;
                }
                Rental rental = instituteList.get(instContainsArt);
                if (rental.getRentalStatus().equalsIgnoreCase("unpaid") || rental.getRentalStatus().equalsIgnoreCase("pending")) {
                    return true;
                }
            }
        }
        return true;
    }

    /**
     * Method to determine if an Artwork is being rented
     *
     * @see #showInstitutes()
     */
    public void checkRentalStatus() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select artwork: ");
        showInstitutes();
        String selectedArt = scanner.next();
        ArtWork artWork = artworkList.get(selectedArt);
        if(artWork != null) {
            if (onActiveRent(artWork)) {
                String rentalStatus = getInstituteList().get(artWork.getInventoryLocation()).getRentalStatus();
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
        artworkControllerInstance.showArtworks();
        String artworkName = scanner.next();
        ArtWork artwork = artworkList.get(artworkName.toLowerCase());
        String artworkLocation = artwork.getInventoryLocation();
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
                int halfRent = artworkRentPrice / 2;
                if (halfRent % 2 != 0) {
                    System.out.println("Rent can only be fully paid. No more half rents.");
                }
                System.out.println("Please select how much you want to pay from the following options: \n 1. Full Rent \n 2. Half Rent");
                int userChoice = scanner.nextInt();
                if (halfRent % 2 != 0) {
                    userChoice = 1;
                }
                switch (userChoice) {
                    case 1:
                        done = false;
                        while (!done) {
                            System.out.println("Please enter the amount to pay");
                            int userMoney = scanner.nextInt();
                            if (userMoney > artworkRentPrice) {
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
                        while (!done) {
                            System.out.println("Please enter the amount to pay");
                            int userMoney = scanner.nextInt();
                            if (userMoney > halfRent) {
                                System.out.println("Your change is: " + (userMoney - halfRent));
                                done = true;
                            } else if (userMoney < halfRent) {
                                System.out.println("Insufficient Payment. Please try again");
                            }
                        }
                        System.out.println("Rent has been paid");
                        instituteList.get(artworkLocation).setRentalStatus("pending");
                        int nwDebt = halfRent / 2;
                        instituteList.get(artworkLocation).setPendingRental(nwDebt);
                        done = true;
                        break;
                    default:
                        System.out.println("That is not a valid option");
                }
            }
        } else {
            System.out.println("Artwork not found. Please try again");
            rentalChangeStatus();
        }
    }

    /**
     * Method to print the defined rented prince of an Artwork
     */
    public void rentedPrice() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select the Artwork: ");
        artworkControllerInstance.showArtworks();
        String artworkName = scanner.next();
        ArtWork artwork = artworkList.get(artworkName.toLowerCase());
        String artworkLocation = artwork.getInventoryLocation();
        if (artworkLocation != null) {
            System.out.println("Renting Institution: " + artworkLocation);
            int artworkRentPrice = instituteList.get(artworkLocation).getRentalPrice();
            System.out.println("Original rented price: " + artworkRentPrice);
        } else {
            System.out.println("Artwork not found. Please try again");
            rentedPrice();
        }
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
     * Method to delete an Institute and remove it from instituteList
     *
     * @see #showInstitutes()
     */
    public void deleteInstitute() {
        Scanner scanner = new Scanner(System.in);
            System.out.println("Institute to delete: ");
            showInstitutes();
            String instName = scanner.next();
        if (instituteList.containsKey(instName.toLowerCase())) {
            instituteList.remove(instName.toLowerCase());
            System.out.println("Success! Institute " + instName.toLowerCase() + " was deleted");
        } else {
            System.out.println("Exposition not found. Please type the exact name");
            deleteInstitute();
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
    public void showInstitutes() {
        if (!instituteList.isEmpty()) {
            Set<String> instkey = instituteList.keySet();
            int index = 1;
            for (String artworks : instkey) {
                System.out.println(index + ". " + artworks);
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
    private void setRentPrice(ArtWork artWork) {
        Scanner scanner = new Scanner(System.in);
        boolean done = false;
        while (!done) {
            System.out.println("Set rent price. (No cents)");
            int artRentPrice = scanner.nextInt();
            if (artRentPrice > 0) {
                instituteList.get(artWork.getInventoryLocation()).setRentalPrice(artRentPrice);
                instituteList.get(artWork.getInventoryLocation()).setPendingRental(artRentPrice);
                instituteList.get(artWork.getInventoryLocation()).setRentalStatus("unpaid");
                done = true;
            } else {
                System.out.println("Error price can not be 0 or lower. \n Try again. ");
            }
        }
    }
}
