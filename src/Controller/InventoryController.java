package Controller;

import Model.ArtWork;
import Model.Inventory;
import com.sun.tools.javac.Main;

import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;

public class InventoryController {

    private LinkedHashMap<String, Inventory> inventoryList;
    private static InventoryController inventoryListInstance = null;

    /**
     * Method constructor for InventoryController Class
     */
    private InventoryController(){
        this.inventoryList = new LinkedHashMap<>();
        Inventory defaultInventory = new Inventory("storage", "storage");
        getInventoryList().put(defaultInventory.getInventoryName(), defaultInventory);
    }

    /**
     * Method to print all the details of an Inventory
     */
    public void inventoryDetails() {
        Scanner scanner = new Scanner(System.in);
        LinkedHashMap<String, Inventory> invList = inventoryList;
        if(!inventoryList.isEmpty()) {
            Set<String> key = invList.keySet();
            int index = 1;
            for (String inventory : key) {
                System.out.println(index + ". " + inventory);
            }
            System.out.println("Select Inventory: ");
            String inventoryName = scanner.next();
            if(inventoryList.containsKey(inventoryName)) {
                Inventory inventory = inventoryList.get(inventoryName);
                System.out.println("Inventory: " + inventory.getInventoryName());
                System.out.println("Inventory address: " + inventory.getInventoryAddress());
            } else {
                System.out.println("Inventory not found. Please try again");
                inventoryDetails();
            }
        } else {
            System.out.println("Empty List");
        }
    }

    /**
     * Method to set an ArtworkLocation to an Inventory
     */
    public void setArtworkInventory(){

        ArtworkController artworkControllerInstance = ArtworkController.getInstance();
        RentalController rentalControllerInstance = RentalController.getInstance();
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
            ArtWork artWork = artworkControllerInstance.getArtworkList().get(artworkName);
            System.out.println("Select destination: ");
            LinkedHashMap<String, Inventory> invList = inventoryList;
            if (!inventoryList.isEmpty()) {
                Set<String> invKey = invList.keySet();
                index = 1;
                for (String inventory : invKey) {
                    System.out.println(index + ". " + inventory);
                    }
                    String artworkLocation = scanner.next();
                if (inventoryList.containsKey(artworkLocation)) {
                    if (rentalControllerInstance.getInstituteList().containsKey(artWork.getInventoryLocation()) ||
                            expositionControllerInstance.getExpoList().containsKey(artWork.getInventoryLocation())) {
                        System.out.println(artworkName + " is located in: " + artWork.getInventoryLocation());
                        System.out.println("Do you wish to continue? Y/N");
                        String useryn = scanner.next();
                        if (useryn.equalsIgnoreCase("y")) {
                            if (rentalControllerInstance.onActiveRent(artWork) || expositionControllerInstance.onActiveExpo(artWork)) {
                                System.out.println("Artwork can not be put into inventory \n Check Rental or Expo status");
                            } else {
                                return;
                            }
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
                System.out.println("Inventory list is empty");
            }
        } else {
            System.out.println("Artwork list is empty");
        }
    }

    public void artworkInInventory() {
        Scanner scanner = new Scanner(System.in);
        LinkedHashMap<String, Inventory> invList = inventoryList;
        if (!inventoryList.isEmpty()) {
            Set<String> key = invList.keySet();
            int index = 1;
            for (String inventory : key) {
                System.out.println(index + ". " + inventory);
            }
            System.out.println("Select Inventory");
            String inventoryName = scanner.next();
            ArtworkController artworkInstance = ArtworkController.getInstance();
            LinkedHashMap<String, ArtWork> artWorkList = artworkInstance.getArtworkList();
            index = 1;
            if (!artWorkList.isEmpty()) {
                Set<String> artKey = artWorkList.keySet();
                for (String artworks : artKey) {
                    if (artWorkList.get(artworks).getInventoryLocation().equalsIgnoreCase(inventoryName)) {
                        System.out.println(index + ". " + artworks);
                    }
                }
            }
        } else {
            System.out.println("Empty inventory List");
        }
    }

    /**
     * Method for the creation of an Inventory
     */
    public void createInventory(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inventory name: ");
        String inventoryName = scanner.next();
        System.out.println("Inventory Address: ");
        String inventoryAddress = scanner.next();
        if(!inventoryList.containsKey(inventoryName.toLowerCase())) {
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
     */
    public void deleteInventory(){
        Scanner scanner = new Scanner(System.in);
        LinkedHashMap<String, Inventory> invList = inventoryList;
        if(!inventoryList.isEmpty()) {
            Set<String> key = invList.keySet();
            int index = 1;
            for (String inventory : key) {
                System.out.println(index + ". " + inventory);
            }
            System.out.println("Inventory name to delete: ");
            String inventoryName = scanner.next();
            ArtworkController artworkInstance = ArtworkController.getInstance();
            LinkedHashMap<String, ArtWork> artWorkList = artworkInstance.getArtworkList();
            index = 1;
            if (!artWorkList.isEmpty()) {
                Set<String> artKey = artWorkList.keySet();
                for (String artworks : artKey) {
                    if(artWorkList.get(artworks).getInventoryLocation().equalsIgnoreCase(inventoryName)){
                        System.out.println(index + ". " + artworks + "is being storaged in this inventory");
                        System.out.println("Can not continue until the inventory is empty");
                    }
                }
            } else {
                if (!inventoryName.equalsIgnoreCase("bodega")) {
                    if (inventoryList.containsKey(inventoryName.toLowerCase())) {
                        getInventoryList().remove(inventoryName.toLowerCase());
                        System.out.println("Success! Inventory " + inventoryName + " has been deleted.");
                    } else {
                        System.out.println("Inventory not found. Type the exact inventory name");
                        deleteInventory();
                    }
                } else {
                    System.out.println("Bodega is the default inventory. Can not be deleted.");
                }
            }
        } else {
            System.out.println("Empty List");
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
}
