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

    private RentalController() {
        this.instituteList = new LinkedHashMap<>();

    }
    public void setArtworkForRental() {
        ArtworkController artworkControllerInstance = ArtworkController.getInstance();
        ExpositionController expositionControllerInstance = ExpositionController.getInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Artwork for rental: ");
        String artworkName = scanner.next();
        ArtWork artWork = artworkControllerInstance.getArtworkList().get(artworkName);
        System.out.println("Set Institute that will be renting");
        String artworkLocation = scanner.next();
        if (!instituteList.containsKey(artworkLocation)){
            boolean done = false;
            while (!done) {
                System.out.println("Institute not found \n 1. Create Institute? \n 2. Exit");
                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        createInstitute();
                        setArtworkForRental();
                        done = true;
                        break;
                    case 2: Main.rentMenu();
                        break;
                    default:
                        System.out.println("That is not a valid option");
                }
            }
        } else if (expositionControllerInstance.getExpoList().containsKey(artWork.getInventoryLocation())){
            System.out.println(artworkName + " is not available for rental \n Location: " + artWork.getInventoryLocation());
        } else {
            artWork.setInventoryLocation(artworkLocation);
            System.out.println(artworkName + " will be rented by " + artWork.getInventoryLocation());
        }

        boolean done = false;
        while (!done) {
            System.out.println("What is the rent price? No cents");
            int artRentPrice = scanner.nextInt();
            if (artRentPrice > 0) {
                instituteList.get(artWork.getInventoryLocation()).setRentalPrice(artRentPrice);
                instituteList.get(artWork.getInventoryLocation()).setRentalStatus("Unpaid");
                done = true;
            } else {
                System.out.println("Error price can not be 0 or lower. \n Try again. ");
            }
        }
    }

    public boolean checkRentalStatus(ArtWork artWork) {
        String test = artWork.getInventoryLocation();
        if (!instituteList.containsKey(test)) {
            return false;
        }
        Rental rental = instituteList.get(test);
        if (rental.getRentalStatus().equalsIgnoreCase("Unpaid") || rental.getRentalStatus().equalsIgnoreCase("Pending")){
            return true;
        }
        return false;
    }

    public void rentalChangeStatus() {
        ArtworkController artworkControllerInstance = ArtworkController.getInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select the Artwork: ");
        LinkedHashMap<String, ArtWork> artWorkList = artworkControllerInstance.getArtworkList();
        Set<String> key = artWorkList.keySet();
        for (String artworks : key) {
            System.out.println(artworks);
        }
        String artworkName = scanner.next();
        String artworkLocation = artworkControllerInstance.getArtworkList().get(artworkName).getInventoryLocation();
        double artworkRentPrice = instituteList.get(artworkLocation).getRentalPrice();
        boolean done = false;
        while (!done) {
            System.out.println("Total rent debt is: ");
            System.out.println(artworkRentPrice);
            if (artworkRentPrice <= 0){
                System.out.println("Rent has been fully paid");
                return;
            }
            double halfRent = artworkRentPrice / 2;
            if (halfRent % 2 != 0){
                System.out.println("Rent can only be fully paid. No more half rents.");
            }
            System.out.println("Please select how much you want to pay from the following options: \n 1. Full Rent \n 2. Half Rent");
            int userChoice = scanner.nextInt();
            if (halfRent % 2 != 0){
                userChoice = 1;
            }
            switch (userChoice) {
                case 1:
                    done = false;
                    while (!done) {
                        System.out.println("Please enter the amount to pay");
                        double userMoney = scanner.nextDouble();
                        if (userMoney > artworkRentPrice) {
                            System.out.println("Your change is: " + (userMoney - artworkRentPrice));
                            done = true;
                        } else if (userMoney < artworkRentPrice) {
                            System.out.println("Insufficient Payment. Please try again");
                        }
                    }
                    System.out.println("Rent has been paid");
                    instituteList.get(artworkLocation).setRentalStatus("Paid");
                    done = true;
                    break;
                case 2:
                    done = false;
                    while (!done) {
                        System.out.println("Please enter the amount to pay");
                        double userMoney = scanner.nextDouble();
                        if (userMoney > halfRent) {
                            System.out.println("Your change is: " + (userMoney - halfRent));
                            done = true;
                        } else if (userMoney < halfRent) {
                            System.out.println("Insufficient Payment. Please try again");
                        }
                    }
                    System.out.println("Rent has been paid");
                    instituteList.get(artworkLocation).setRentalStatus("Pending");
                    done = true;
                    break;
                default:
                    System.out.println("That is not a valid option");
            }
        }
    }
    public void createInstitute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Rental Institute name: ");
        String rentName = scanner.next();

        Rental rental = new Rental(rentName);
        this.instituteList.put(rentName, rental);

        System.out.println("Success! Institute " + rental.getRentalInstitution() + " was created.");
    }
    public void deleteInstitute() {
        Scanner scanner = new Scanner(System.in);
        LinkedHashMap<String, Rental> invList = instituteList;
        Set<String> key = invList.keySet();
        for (String institute : key) {
            System.out.println(institute);
        }
        System.out.println("Rental Institute to delete: ");
        String expoName = scanner.next();
        getInstituteList().remove(expoName);

        System.out.println("Success! Institute " + expoName + " was deleted");
    }
    public static RentalController getInstance() {
        if (instituteListInstance == null) {
            instituteListInstance = new RentalController();
        }
        return instituteListInstance;
    }
    public LinkedHashMap<String, Rental> getInstituteList() {
        return instituteList;
    }
}
