package Controller;

import Model.ArtWork;

import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void mainMenu() {
        ArtworkController artInstance = ArtworkController.getInstance();
        RentalController rentInstance = RentalController.getInstance();
        ExpositionController expoInstance = ExpositionController.getInstance();
        InventoryController invInstance = InventoryController.getInstance();

        boolean done = false;
        while (!done) {
            System.out.println("Welcome to ArtCo. Your Art professional manager");
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please select an option \n 1. Artwork \n 2. Inventory \n 3. Institutions \n 4. Expositions \n 5. Exit");
            int usernum = scanner.nextInt();
            switch (usernum) {
                case 1:
                    artworkMenu();
                    break;
                case 2:
                    invMenu();
                    break;
                case 3:
                    rentMenu();
                    break;
                case 4:
                    expoMenu();
                    break;
                case 5:
                    done = true;
                    break;
                default:
                    System.out.println("That is not a valid option");
            }
        }
    }
    public static void artworkMenu() {
        boolean done = false;
        while (!done) {
            ArtworkController artInstance = ArtworkController.getInstance();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Artwork Options: \n 1. Add Artwork \n 2. Delete Artwork \n 3. Go back");
            int usrartnum = scanner.nextInt();
            switch (usrartnum) {
                case 1:
                    artInstance.createArtwork();
                    break;
                case 2:
                    artInstance.deleteArtwork();
                    break;
                case 3:
                    mainMenu();
                default:
                    System.out.println("That is not a valid option");
            }
        }
    }
    public static void invMenu() {
        boolean done = false;
        while (!done) {
            InventoryController invInstance = InventoryController.getInstance();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Inventory Options: \n 1. Add Inventory \n 2. Delete Inventory \n 3. Go back");
            int usrartnum = scanner.nextInt();
            switch (usrartnum) {
                case 1:
                    invInstance.createInventory();
                    break;
                case 2:
                    invInstance.deleteInventory();
                    break;
                case 3:
                    mainMenu();
                default:
                    System.out.println("That is not a valid option");
            }
        }
    }
    public static void rentMenu() {
        boolean done = false;
        while (!done) {
            RentalController rentInstance = RentalController.getInstance();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Institutions Options: \n 1. Add Institute \n 2. Delete Institute \n 3. Check rental status");
            int usrartnum = scanner.nextInt();
            switch (usrartnum) {
                case 1:
                    rentInstance.createInstitute();
                    break;
                case 2:
                    rentInstance.deleteInstitute();
                    break;
                case 3:
                    ArtworkController artInstance =ArtworkController.getInstance();
                    System.out.println("Please select artwork: ");
                    LinkedHashMap<String, ArtWork> artWorkList = artInstance.getArtworkList();
                    Set<String> key = artWorkList.keySet();
                    for (String artworks : key) {
                        System.out.println(artworks);
                    }
                    String selectedart = scanner.next();
                    ArtWork artWork = artWorkList.get(selectedart);
                    if (rentInstance.checkRentalStatus(artWork)){
                        rentInstance.getInstituteList().get().getRentalStatus();
                    }



                default:
                    System.out.println("That is not a valid option");
            }
        }
    }
    public static void expoMenu() {
        boolean done = false;
        while (!done) {
            ExpositionController expoInstance = ExpositionController.getInstance();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Exposition Options: \n 1. Add Exposition \n 2. Delete Exposition \n 3. Go back");
            int usrartnum = scanner.nextInt();
            switch (usrartnum) {
                case 1:
                    expoInstance.createExpo();
                    break;
                case 2:
                    expoInstance.deleteExpo();
                    break;
                case 3:
                    mainMenu();
                default:
                    System.out.println("That is not a valid option");
            }
        }
    }
    public static void main(String[] args) {
        mainMenu();
    }
}
