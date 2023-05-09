package Controller;

import Model.ArtWork;
import Model.Expositon;
import Model.Inventory;
import Model.Rental;

import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;

public class RentalController {

    private LinkedHashMap<String, Rental> instituteList;
    private static RentalController instituteListInstance = null;

    /**
     * Constructor for RentalController
     */
    private RentalController() {
        this.instituteList = new LinkedHashMap<>();
        Rental rental = new Rental("Default");
        rental.setRentalStatus("Paid");
        rental.setRentalPrice(200);
        instituteList.put(rental.getRentalInstitution(),rental);

    }

    /**
     * Method to set an Artwork for Rental
     */
    public void setArtworkForRental() {
            ArtworkController artworkControllerInstance = ArtworkController.getInstance();
            ExpositionController expositionControllerInstance = ExpositionController.getInstance();
            Scanner scanner = new Scanner(System.in);
            LinkedHashMap<String, ArtWork> artworkList = artworkControllerInstance.getArtworkList();
            if (!artworkList.isEmpty()) {
                Set<String> key = artworkList.keySet();
                int index = 1;
                for (String artworks : key) {
                    System.out.println(index + ". " + artworks);
                    index++;
                }
                System.out.println("Select Artwork: ");
                String artworkName = scanner.next();
                ArtWork artWork = artworkControllerInstance.getArtworkList().get(artworkName.toLowerCase());
                System.out.println("Select Institute: ");
                LinkedHashMap<String, Rental> invList = instituteList;
                if (!instituteList.isEmpty()) {
                    Set<String> instkey = invList.keySet();
                    index = 1;
                    for (String artworks : instkey) {
                        System.out.println(index + ". " + artworks);
                    }
                    String artworkLocation = scanner.next();
                    if (instituteList.containsKey(artworkLocation.toLowerCase())) {
                        if (expositionControllerInstance.getExpoList().containsKey(artWork.getInventoryLocation())) {
                            System.out.println(artworkName + " is not available for rental \n Location: " + artWork.getInventoryLocation());
                            if ((expositionControllerInstance.onActiveExpo(artWork))){
                                System.out.println("Artwork can not be sent for rental. Check exposition status");
                            }
                        } else {
                            artWork.setInventoryLocation(artworkLocation);
                            System.out.println(artworkName + " will be rented by " + artWork.getInventoryLocation());
                        }

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
                } else {
                    System.out.println("Institutions List is empty");
                }
            } else {
                System.out.println("Artwork List is empty");
            }
        }
    }

    /**
     * Method to determine if an institutions rent status
     *
     * @param artWork
     * @return The boolean flag of an Artwork rent status
     */
    public boolean onActiveRent(ArtWork artWork) {
        String test = artWork.getInventoryLocation();
        if (!instituteList.containsKey(test)) {
            return false;
        }
        Rental rental = instituteList.get(test);
        if (rental.getRentalStatus().equalsIgnoreCase("unpaid") || rental.getRentalStatus().equalsIgnoreCase("pending")){
            return true;
        }
        return false;
    }

    /**
     * Method to determine if an Artwork is being rented
     */
    public void checkRentalStatus() {
        Scanner scanner = new Scanner(System.in);
        ArtworkController artInstance =ArtworkController.getInstance();
        System.out.println("Please select artwork: ");
        LinkedHashMap<String, ArtWork> artWorkList = artInstance.getArtworkList();
        Set<String> key = artWorkList.keySet();
        int index = 1;
        for (String artworks : key) {
            System.out.println(index + ". " + artworks);
            index++;
        }
        String selectedart = scanner.next();
        ArtWork artWork = artWorkList.get(selectedart);
        if (onActiveRent(artWork)){
           String rentalStatus = getInstituteList().get(artWork.getInventoryLocation()).getRentalStatus();
            System.out.println(rentalStatus);

        } else {
            System.out.println("Artwork is not rented.");
        }
    }

    /**
     * Method to change the rental status of an Institution
     */
    public void rentalChangeStatus() {
        ArtworkController artworkControllerInstance = ArtworkController.getInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select the Artwork: ");
        LinkedHashMap<String, ArtWork> artWorkList = artworkControllerInstance.getArtworkList();
        Set<String> key = artWorkList.keySet();
        int index = 1;
        for (String artworks : key) {
            System.out.println(index + ". " + artworks);
        }
        String artworkName = scanner.next();
        String artworkLocation = artworkControllerInstance.getArtworkList().get(artworkName.toLowerCase()).getInventoryLocation();
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
                    //Agregar monto en saldo pendiente.
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
                    int nwDebt = halfRent/2;
                    instituteList.get(artworkLocation).setPendingRental(nwDebt);
                    done = true;
                    break;
                default:
                    System.out.println("That is not a valid option");
            }
        }
    }

    public void rentedPrice() {
        ArtworkController artworkControllerInstance = ArtworkController.getInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select the Artwork: ");
        LinkedHashMap<String, ArtWork> artWorkList = artworkControllerInstance.getArtworkList();
        Set<String> key = artWorkList.keySet();
        int index = 1;
        for (String artworks : key) {
            System.out.println(index + ". " + artworks);
        }
        String artworkName = scanner.next();
        String artworkLocation = artworkControllerInstance.getArtworkList().get(artworkName.toLowerCase()).getInventoryLocation();
        System.out.println("Renting Institution: " + artworkLocation);
        int artworkRentPrice = instituteList.get(artworkLocation).getRentalPrice();
        System.out.println("Original rented price: " + artworkRentPrice);
    }

    /**
     * Method to create an Institute and add it to instituteList
     */
    public void createInstitute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Rental Institute name: ");
        String rentName = scanner.next();

        Rental rental = new Rental(rentName);
        this.instituteList.put(rentName, rental);

        System.out.println("Success! Institute " + rental.getRentalInstitution() + " was created.");
    }

    /**
     * Method to delete an Institute and remove it from instituteList
     */
    public void deleteInstitute() {
        Scanner scanner = new Scanner(System.in);
        LinkedHashMap<String, Rental> invList = instituteList;
        if(!instituteList.isEmpty()) {
            System.out.println("List of Institutes: ");
            Set<String> key = invList.keySet();
            int index = 1;
            for (String artworks : key) {
                System.out.println(index + ". " + artworks);
            }
            System.out.println("Institute to delete: ");
            String instName = scanner.next();
            try {
                    Rental rental = instituteList.get(instName);
                    getInstituteList().remove(instName);
                    System.out.println("Success! Institute " + instName + " was deleted");
            } catch (NullPointerException e) {
                System.out.println("Institute not found. Please type the exact name");
                deleteInstitute();
            }
        } else {
            System.out.println("Empty List");
        }
    }

    /**
     * Singleton for RentalController
     * @return The same RentalController instance
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
}
