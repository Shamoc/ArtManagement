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

    private RentalController(){
        this.instituteList = new LinkedHashMap<>();

    }
    public void setArtworkForRental(){
        ArtworkController artworkControllerInstance = ArtworkController.getInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Artwork for rental: ");
        String artworkName = scanner.next();
        ArtWork artWork = artworkControllerInstance.getArtworkList().get(artworkName);
        System.out.println("Set Institute that will be renting");
        String artworkLocation = scanner.next();

        if (!instituteList.containsKey(artworkLocation)){
            System.out.println("Institute not found \n Create Institute?");
        } else {
            artWork.setInventoryLocation(artworkLocation);
        }

        System.out.println("What is the rent price?");
        double artRentPrice = scanner.nextDouble();
        instituteList.get(artworkLocation).setRentalPrice(artRentPrice);
        instituteList.get(artworkLocation).setRentalStatus("Unpaid");
    }

    public void rentalChangeStatus(){
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
        System.out.println("Total rent debt is: ");
        System.out.println(artworkRentPrice);
        System.out.println("Please select how much you want to pay from the following options: \n 1. Full Rent \n 2. Half Rent");
        int userChoice = scanner.nextInt();
        switch (userChoice) {
            case 1:
                boolean done = false;
                while (!done){
                    System.out.println("Please enter the amount to pay");
                    double userMoney = scanner.nextDouble();
                    if (userMoney > artworkRentPrice){
                        System.out.println("Your change is: " + (userMoney-artworkRentPrice));
                        done = true;
                    } else if (userMoney < artworkRentPrice){
                    System.out.println("Insufficient Payment. Please try again");
                    }
                }
                System.out.println("Rent has been paid");
            case 2:
                double halfRent = artworkRentPrice/2;
                done = false;
                while (!done){
                    System.out.println("Please enter the amount to pay");
                    double userMoney = scanner.nextDouble();
                    if (userMoney > halfRent){
                        System.out.println("Your change is: " + (userMoney-halfRent));
                        done = true;
                    } else if (userMoney < halfRent){
                        System.out.println("Insufficient Payment. Please try again");
                    }
                }
                System.out.println("Rent has been paid");
        }
    }
    public void createInstitute(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Rental Institute name: ");
        String rentName = scanner.next();

        Rental rental = new Rental(rentName);
        this.instituteList.put(rentName, rental);
    }
    public void deleteInstitute(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Rental Institute to delete: ");
        String expoName = scanner.next();
        getInstituteList().remove(expoName);
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
