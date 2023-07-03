package Controller;

import DB_Implementation.ArtworkDaoImplementation;
import DB_Implementation.InventoryDaoImplementation;
import Model.Artwork;
import Model.Inventory;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class InventoryController {

    private List<Inventory> inventoryList;
    private static InventoryController inventoryListInstance = null;
    private static RentalController rentalControllerInstance;
    private static ExpositionController expositionControllerInstance;
    private List<Artwork> artworkList;
    private static InventoryDaoImplementation invDao;
    private static ArtworkDaoImplementation artDao;


    /**
     * Method constructor for InventoryController Class
     */
    private InventoryController()
            throws SQLException {
        invDao = InventoryDaoImplementation.getInstance();
        artDao = ArtworkDaoImplementation.getInstance();
        rentalControllerInstance = RentalController.getInstance();
        expositionControllerInstance = ExpositionController.getInstance();
        artworkList = artDao.getArtwork();
        this.inventoryList = invDao.getInventory();
    }

    /**
     * Method to print all the details of an Inventory
     *
     * @see #showInventories()
     */
    public void inventoryDetails()
            throws SQLException {
        Scanner scanner = new Scanner(System.in);
        showInventories();
        System.out.println("Select Inventory: ");
        int invID = scanner.nextInt();
        Inventory inv = inventoryList.get(invID - 1);
        if (inv!=null) {
            System.out.println("Inventory: " + inv.getInventoryName());
            System.out.println("Inventory address: " + inv.getInventoryAddress());
        } else {
            System.out.println("Inventory not found. Please try again");
            inventoryDetails();
        }
    }

    /**
     * Method to set an ArtworkLocation to an Inventory
     *
     * @see #showInventories()
     */
    public void setArtworkInventory()
            throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Available Artwork: ");
        showArtInInventories();
        System.out.println("Select Artwork: ");
        int artworkID = scanner.nextInt();
        List<Artwork> artList = artDao.getArtInInventory();
        Artwork artwork = artList.get(artworkID - 1);
        if(artwork!=null) {
            System.out.println("Select destination: ");
            showInventories();
            int userInv = scanner.nextInt();
            Inventory inv = inventoryList.get(userInv - 1);
            if (inv != null) {
                if (artwork.getInv_id() != inv.getInv_id()) {
                    invDao.updateArtInvID(inv.getInv_id(), artwork.getArt_id());
                    System.out.println("Success " + artwork.getName() + " will be sent to " + inv.getInventoryName());
                } else {
                    System.out.println("Artwork is already at selected destination");
                }
            }
        }
    }

    /**
     * Method to print the artwork located in the selected inventory
     *
     */
    public void artworkInInventory()
            throws SQLException {
        Scanner scanner = new Scanner(System.in);
        showInventories();
        System.out.println("Please select an Inventory: ");
        int userOption = scanner.nextInt();
        Inventory inventory = inventoryList.get(userOption - 1);
        showArtInInventory(inventory.getInv_id());

    }

    /**
     * Method for the creation of an Inventory
     */
    public void createInventory()
            throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("CREATE INVENTORY \n Inventory name: ");
        String inventoryName = scanner.next();
        System.out.println("Inventory Address: ");
        String inventoryAddress = scanner.next();

        Inventory inventory = new Inventory(inventoryName, inventoryAddress);

        if (!invDao.inventoryNameFlag(inventory.getInventoryName())) {
            invDao.add(inventory);
            System.out.println("Success! Inventory " + inventory.getInventoryName() + " created.");
        } else {
            System.out.println("Name already in use. Try again");
            createInventory();
        }
    }

    /**
     * Method for the deletion of an Inventory
     *
     * @see #showInventories()
     */
    public void deleteInventory() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        showInventories();
        System.out.println("Inventory name to delete: ");
        int userInv = scanner.nextInt();
        Inventory inventory = inventoryList.get(userInv - 1);
        if (inventory != null) {
            if (invDao.inventoryHasArtworkFlag(inventory.getInv_id())) {
                System.out.println("Artwork in inventory: ");
                showArtInInventory(inventory.getInv_id());
                System.out.println("Can not continue until the inventory is empty");
            } else {
                if (inventory.getInv_id() != 1) {
                    invDao.delete(inventory.getInv_id());
                    System.out.println("Success! Inventory " + inventory.getInventoryName() + " has been deleted.");

                } else {
                    System.out.println("Bodega is the default inventory. Can not be deleted.");
                }
            }
        }
    }

    /**
     * Method to print all the inventories in the inventoryList
     *
     */
    public void showInventories() throws SQLException {
        if (!invDao.getInventory().isEmpty()) {
            int index = 1;
            for (Inventory inv : invDao.getInventory()) {
                System.out.println(index + ". " + inv.getInventoryName());
                index++;

            }
        } else {
            System.out.println("Inventory list is empty");
        }
    }

    /**
     * Method to print all the Artwork in a given Inventory
     *
     * @param id Inventory ID
     * @throws SQLException
     */
    public void showArtInInventory(int id) throws SQLException {
        if (!invDao.getArtInInventory(id).isEmpty()) {
            int index = 1;
            for (Artwork artwork : invDao.getArtInInventory(id)) {
                System.out.println(index + ". " + artwork.getName());
                index++;
            }
        } else {
            System.out.println("List is empty");
        }
    }

    /**
     * Method to show all Artworks across all Inventories.
     *
     * @throws SQLException
     */
    public void showArtInInventories() throws SQLException {
        if (!artDao.getArtInInventory().isEmpty()) {
            int index = 1;
            for (Artwork artwork : artDao.getArtInInventory()) {
                System.out.println(index + ". " + artwork.getName());
                index++;
            }
        } else {
            System.out.println("List is empty");
        }
    }

    /**
     * Singleton for the InventoryController
     *
     * @return The same InventoryController instance
     */
    public static InventoryController getInstance() {
        if (inventoryListInstance == null) {
            try {
                inventoryListInstance = new InventoryController();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return inventoryListInstance;
    }

    /**
     * Method to access inventoryList
     *
     * @return inventoryList
     */
    public List<Inventory> getInventoryList() {
        return inventoryList;
    }
}