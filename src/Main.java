import Controller.ArtworkController;
import Controller.ExpositionController;
import Controller.InventoryController;
import Controller.RentalController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
        public static void mainMenu() {

            boolean done = false;
            while (!done) {
                System.out.println("Welcome to ArtCo. Your Art professional manager");
                Scanner scanner = new Scanner(System.in);
                System.out.println("Please select an option \n 1. Artwork \n 2. Inventory \n 3. Rentals \n 4. Expositions \n 5. Exit");
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
                        System.exit(0);
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
                System.out.println("Artwork Options: \n 1. Artwork details \n 2. Add Artwork \n 3. Delete Artwork \n 4. Go back");
                int usrartnum = scanner.nextInt();
                switch (usrartnum) {
                    case 1:
                        artInstance.artworkDetails();
                        break;
                    case 2:
                        artInstance.createArtwork();
                        break;
                    case 3:
                        artInstance.deleteArtwork();
                        break;
                    case 4:
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
                System.out.println("Inventory Options: \n 1. Artwork in Inventory \n 2. Check Inventory details \n 3. Send Artwork to Inventory " +
                        "\n 4. Add Inventory " + "\n 5. Delete Inventory \n 6. Go back");
                int usrartnum = scanner.nextInt();
                switch (usrartnum) {
                    case 1:
                        invInstance.artworkInInventory();
                        break;
                    case 2:
                        invInstance.inventoryDetails();
                        break;
                    case 3:
                        invInstance.setArtworkInventory();
                        break;
                    case 4:
                        invInstance.createInventory();
                        break;
                    case 5:
                        invInstance.deleteInventory();
                        break;
                    case 6:
                        mainMenu();
                        break;
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
                System.out.println("Rental Options: \n 1. Check Rental Status of an Artwork \n 2. Set Artwork for Rental \n 3. Pay Rental" +
                        "\n 4. Add Institute \n 5. Delete Institute \n 6. Check Rented price of an Artwork \n 7. Go back");
                int usrartnum = scanner.nextInt();
                switch (usrartnum) {
                    case 1:
                        rentInstance.checkRentalStatus();
                        break;
                    case 2:
                        rentInstance.setArtworkForRental();
                        break;
                    case 3:
                        rentInstance.rentalChangeStatus();
                        break;
                    case 4:
                        rentInstance.createInstitute();
                        break;
                    case 5:
                        rentInstance.deleteInstitute();
                        break;
                    case 6:
                        rentInstance.rentedPrice();
                        break;
                    case 7:
                        mainMenu();
                        break;
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
                System.out.println("Exposition Options: \n 1. Create Exposition \n 2. Add Artwork to Exposition \n 3. Delete Exposition \n 4. Go back");
                int usrartnum = scanner.nextInt();
                switch (usrartnum) {
                    case 1:
                        expoInstance.createExpo();
                        break;
                    case 2:
                        expoInstance.setArtworkExposition();
                        break;
                    case 3:
                        expoInstance.deleteExpo();
                        break;
                    case 4:
                        mainMenu();
                        break;
                    default:
                        System.out.println("That is not a valid option");
                }
            }
        }
        public static void main(String[] args) {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("myfile.txt"));
                bufferedWriter.write("queso");
            } catch (IOException e){
                System.out.println("su queso");
            }
            mainMenu();
        }
}