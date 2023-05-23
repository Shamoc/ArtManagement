import Controller.ArtworkController;
import Controller.ExpositionController;
import Controller.InventoryController;
import Controller.RentalController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    /**
     * Method for the Main Menu
     */
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
    /**
     * Method for the Artwork Menu
     */
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
                        break;
                    default:
                        System.out.println("That is not a valid option");
                }
            }
        }
    /**
     * Method for the Inventory Menu
     */
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
                        invInstance.showInventories();
                        System.out.println("Select Inventory");
                        String invName = scanner.next();
                        invInstance.artworkInInventory(invName);
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
    /**
     * Method for the Rent Menu
     */
    public static void rentMenu() {
            boolean done = false;
            while (!done) {
                InventoryController invInstance = InventoryController.getInstance();
                RentalController rentInstance = RentalController.getInstance();
                Scanner scanner = new Scanner(System.in);
                System.out.println("Rental Options: \n 1. Check Rental Status of an Artwork \n 2. Set Artwork for Rental \n 3. Pay Rental" +
                        "\n 4. Add Institute \n 5. Delete Institute \n 6. Check Rented price of an Artwork \n 7. Artwork in Rent \n 8. Go back");
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
                        rentInstance.showInstitutes();
                        System.out.println("Select Institute");
                        String invName = scanner.next();
                        invInstance.artworkInInventory(invName);
                        break;
                    case 8:
                        mainMenu();
                        break;
                    default:
                        System.out.println("That is not a valid option");
                }
            }
        }
    /**
     * Method for the Exposition Menu
     */
    public static void expoMenu() {
            boolean done = false;
            while (!done) {
                ExpositionController expoInstance = ExpositionController.getInstance();
                InventoryController invInstance = InventoryController.getInstance();
                Scanner scanner = new Scanner(System.in);
                System.out.println("Exposition Options: \n 1. Create Exposition \n 2. Artwork in Exposition \n 3. Add Artwork to Exposition \n 4. Delete Exposition \n 5. Expo status \n 6. Go back");
                int usrartnum = scanner.nextInt();
                switch (usrartnum) {
                    case 1:
                        expoInstance.createExpo();
                        break;
                    case 2:
                        expoInstance.showExpositions();
                        System.out.println("Select Exposition");
                        String invName = scanner.next();
                        invInstance.artworkInInventory(invName);
                        break;
                    case 3:
                        expoInstance.setArtworkExposition();
                        break;
                    case 4:
                        expoInstance.deleteExpo();
                        break;
                    case 5:
                        expoInstance.checkExpoStatus();
                        break;
                    case 6:
                        mainMenu();
                        break;
                    default:
                        System.out.println("That is not a valid option");
                }
            }
        }
        public static void main(String[] args) {
            Connection connection = null;
            try {
                // below two lines are used for connectivity.
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/artcodb",
                        "root", "wjcXWo69^1ez2S#hMShCh$WeLxIJXz");

                // mydb is database
                // mydbuser is name of database
                // mydbuser is password of database

                Statement statement;
                statement = connection.createStatement();
                ResultSet resultSet;
                resultSet = statement.executeQuery(
                        "select * from arts");
            }
            catch (Exception exception) {
                System.out.println(exception);
            }
            mainMenu();
        }
}