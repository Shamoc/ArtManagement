package Controller;

import Model.ArtWork;
import Model.Inventory;

import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;

public class InventoryController {

    private LinkedHashMap<String, Inventory> inventoryList;
    private static InventoryController inventoryListInstance = null;
    private static ArtworkController artworkControllerInstance;
    private static RentalController rentalControllerInstance;
    private static ExpositionController expositionControllerInstance;
    private LinkedHashMap<String, ArtWork> artworkList;


    /**
     * Method constructor for InventoryController Class
     */
    private InventoryController() {
        this.inventoryList = new LinkedHashMap<>();
        artworkControllerInstance = ArtworkController.getInstance();
        rentalControllerInstance = RentalController.getInstance();
        expositionControllerInstance = ExpositionController.getInstance();
        artworkList = artworkControllerInstance.getArtworkList();
        Inventory defaultInventory = new Inventory("storage", "storage");
        getInventoryList().put(defaultInventory.getInventoryName(), defaultInventory);
    }

    /**
     * Method to print all the details of an Inventory
     *
     * @see #showInventories()
     */
    public void inventoryDetails() {
        Scanner scanner = new Scanner(System.in);
        showInventories();
        System.out.println("Select Inventory: ");
        String inventoryName = scanner.next();
        if (inventoryList.containsKey(inventoryName)) {
            Inventory inventory = inventoryList.get(inventoryName);
            System.out.println("Inventory: " + inventory.getInventoryName());
            System.out.println("Inventory address: " + inventory.getInventoryAddress());
        } else {
            System.out.println("Inventory not found. Please try again");
            inventoryDetails();
        }
    }

    /**
     * Method to set an ArtworkLocation to an Inventory
     *
     * @see #showInventories()
     * @see #artworkControllerInstance
     */
    public void setArtworkInventory() {
        Scanner scanner = new Scanner(System.in);
        artworkControllerInstance.showArtworks();
        System.out.println("Select Artwork: ");
        String artworkName = scanner.next();
        ArtWork artWork = artworkList.get(artworkName.toLowerCase());
        if(artWork!=null) {
            System.out.println("Select destination: ");
            showInventories();
            String artworkLocation = scanner.next();
            if (inventoryList.containsKey(artworkLocation)) {
                boolean isInstituteLocation = rentalControllerInstance.getInstituteList().containsKey(artWork.getInventoryLocation());
                boolean isExpoLocation = expositionControllerInstance.getExpoList().containsKey(artWork.getInventoryLocation());
                if (isInstituteLocation || isExpoLocation) {
                    boolean isOnRent = rentalControllerInstance.isOnRent(artWork);
                    boolean isExpoActive = expositionControllerInstance.onActiveExpo(artWork);
                    boolean isRentPaid = isOnRent ? rentalControllerInstance.isRentPaid(artWork) : true;
                    if (isRentPaid && !isExpoActive) {
                        artWork.setInventoryLocation(artworkLocation);
                        System.out.println(artworkName + " will be sent to inventory " + artWork.getInventoryLocation());
                    } else {
                        System.out.println(artworkName + " is located in: " + artWork.getInventoryLocation());
                        System.out.println("Artwork can not be put into inventory \n Check Rental or Expo status");
                    }
                } else {
                    artWork.setInventoryLocation(artworkLocation);
                    System.out.println(artworkName + " will be sent to inventory " + artWork.getInventoryLocation());
                }
            } else {
                System.out.println("Inventory not found. Please input the exact name");
                setArtworkInventory();
            }
        } else {
            System.out.println("Artwork not found. Try again");
            setArtworkInventory();
        }
    }

    /**
     * Method to print the artwork located in the selected inventory
     *
     * @param inventoryName String to determine the inventory from where to look from.
     */
    public void artworkInInventory(String inventoryName) {
        int index = 1;
        boolean isInInventory = inventoryList.containsKey(inventoryName);
        boolean isInExpo = expositionControllerInstance.getExpoList().containsKey(inventoryName);
        boolean isInInstitute = rentalControllerInstance.getInstituteList().containsKey(inventoryName);
        if (isInInventory || isInInstitute || isInExpo) {
            if (!artworkList.isEmpty()) {
                Set<String> artKey = artworkList.keySet();
                for (String artworks : artKey) {
                    if (artworkList.get(artworks).getInventoryLocation().equalsIgnoreCase(inventoryName.toLowerCase())) {
                        System.out.println(index + ". " + artworks);
                    }
                }
            } else {
                System.out.println("Artwork List is empty");
            }
        } else {
            System.out.println("Location not found");
        }
    }

    /**
     * Method for the creation of an Inventory
     */
    public void createInventory() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inventory name: ");
        String inventoryName = scanner.next();
        System.out.println("Inventory Address: ");
        String inventoryAddress = scanner.next();
        if (!inventoryList.containsKey(inventoryName.toLowerCase())) {
            Inventory inventory = new Inventory(inventoryName.toLowerCase(), inventoryAddress);
            getInventoryList().put(inventory.getInventoryName(), inventory);
            System.out.println("Success! Inventory " + inventoryName + " created.");
        } else {
            System.out.println("Name already in use. Try again");
            createInventory();
        }
    }

    /**
     * Method for the deletion of an Inventory
     *
     * @see #inventoryHasArtwork(String)
     * @see #showInventories()
     */
    public void deleteInventory() {
        Scanner scanner = new Scanner(System.in);
        showInventories();
        System.out.println("Inventory name to delete: ");
        String inventoryName = scanner.next();
        boolean test = inventoryHasArtwork(inventoryName);
        if (test) {
            System.out.println("Artwork in inventory: ");
            artworkInInventory(inventoryName);
            System.out.println("Can not continue until the inventory is empty");
        } else {
            if (!inventoryName.equalsIgnoreCase("storage")) {
                if (inventoryList.containsKey(inventoryName.toLowerCase())) {
                    inventoryList.remove(inventoryName.toLowerCase());
                    System.out.println("Success! Inventory " + inventoryName + " has been deleted.");
                } else {
                    System.out.println("Inventory not found. Type the exact inventory name");
                    deleteInventory();
                }
            } else {
                System.out.println("Storage is the default inventory. Can not be deleted.");
            }
        }
    }

    /**
     * Method to print all the inventories in the inventoryList
     */
    public void showInventories() {
        if (!inventoryList.isEmpty()) {
            Set<String> invKey = inventoryList.keySet();
            int index = 1;
            for (String inventory : invKey) {
                System.out.println(index + ". " + inventory);
                index++;

            }
        } else {
            System.out.println("Inventory list is empty");
        }
    }

    /**
     * Singleton for the InventoryController
     *
     * @return The same InventoryController instance
     */
    public static InventoryController getInstance() {
        if (inventoryListInstance == null) {
            inventoryListInstance = new InventoryController();
        }
        return inventoryListInstance;
    }

    /**
     * Method to access inventoryList
     *
     * @return inventoryList
     */
    public LinkedHashMap<String, Inventory> getInventoryList() {
        return inventoryList;
    }

    /**
     * Method to determine if an Inventory has Artworks
     *
     * @param inventory String to determine the inventory from where it will look in.
     * @return Flag when an Artwork is found in the provided inventory
     */
    private boolean inventoryHasArtwork(String inventory) {
        Set<String> artKey = artworkList.keySet();
        for (String artworks : artKey) {
            if (artworkList.get(artworks).getInventoryLocation().equalsIgnoreCase(inventory)) {
                return true;
            }
        }
        return false;
    }
}