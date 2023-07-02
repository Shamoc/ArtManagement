import Controller.*;
import DB_Implementation.ArtworkDaoImplementation;
import Model.Artwork;

import java.sql.*;
import java.util.*;

public class Main {

    /**
     * Method for the Main Menu
     */
    public static void mainMenu() throws SQLException {

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
    public static void artworkMenu() throws SQLException {
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
                        //artInstance.deleteArtwork();
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
    public static void invMenu() throws SQLException {
            boolean done = false;
            while (!done) {
                InventoryController invInstance = InventoryController.getInstance();
                Scanner scanner = new Scanner(System.in);
                System.out.println("Inventory Options: \n 1. Create Inventory \n 2. Inventory details \n 3.  " +
                        "\n 4. " + "\n 5.  \n 6. Go back");
                int usrartnum = scanner.nextInt();
                switch (usrartnum) {
                    case 1:
                       invInstance.createInventory();
                        break;
                    case 2:
                        invInstance.inventoryDetails();
                        break;
                    case 3:
                       // invInstance.setArtworkInventory();
                        break;
                    case 4:
                       // invInstance.createInventory();
                        break;
                    case 5:
                       // invInstance.deleteInventory();
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
    public static void rentMenu() throws SQLException {
            boolean done = false;
            while (!done) {
                InstitutionController instInstance =  InstitutionController.getInstance();
                RentalController rentInstance = RentalController.getInstance();
                Scanner scanner = new Scanner(System.in);
                System.out.println("Rent Options: \n 1. Create Institution \n 2. Create Rent \n 3. Institution details " +
                        "\n 4. Rental details  \n 5. Prueba  \n 6.  \n 7.  \n 8. Go back");
                int usrartnum = scanner.nextInt();
                switch (usrartnum) {
                    case 1:
                        instInstance.createInstitute();
                        break;
                    case 2:
                       rentInstance.createRental();
                        break;
                    case 3:
                       instInstance.instDetails();
                        break;
                    case 4:
                       rentInstance.rentDetails();
                        break;
                    case 5:
                       //rentInstance.isOnRent();
                        break;
                    case 6:
                       // rentInstance.rentedPrice();
                        break;
                    case 7:
                       /* rentInstance.showInstitutes();
                        System.out.println("Select Institute");
                        String invName = scanner.next();
                        invInstance.artworkInInventory(invName); */
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
    public static void expoMenu() throws SQLException {
            boolean done = false;
            while (!done) {
                ExpositionController expoInstance = ExpositionController.getInstance();
                InventoryController invInstance = InventoryController.getInstance();
                Scanner scanner = new Scanner(System.in);
                System.out.println("Exposition Options: \n 1. Create Exposition \n 2. Exposition Details \n 3. Set Artwork for Expo  \n 4. Delete Exposition  \n 5.  \n 6.  \n 7. Go back");
                int usrartnum = scanner.nextInt();
                switch (usrartnum) {
                    case 1:
                        expoInstance.createExpo();
                        break;
                    case 2:
                        expoInstance.expoDetails();
                        break;
                    case 3:
                        expoInstance.setArtworkExposition();
                        break;
                    case 4:
                        expoInstance.deleteExpo();
                        break;
                    case 5:
                       // expoInstance.checkExpoStatus();
                        break;
                    case 6:
                        break;
                    case 7:
                        mainMenu();
                        break;
                    default:
                        System.out.println("That is not a valid option");
                }
            }
        }
        public static void main(String[] args) throws SQLException {
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
          /*  ArtworkDaoImplementation artDao = new ArtworkDaoImplementation();
            Artwork artwork = new Artwork("Ron5", "Da error5", "Pixar5", 2025, "Barroco5", 1);
            artDao.add(artwork); */

            expoMenu();

    }
}