package Controller;

import Model.ArtWork;
import Model.Inventory;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;

public class InventoryController {

    private LinkedHashMap<String, Inventory> inventoryList;
    private static InventoryController inventoryListInstance = null;

    private InventoryController(){
        this.inventoryList = new LinkedHashMap<>();
        Inventory defaultInventory = new Inventory("inventory1", "Bodega");
        getInventoryList().put(defaultInventory.getInventoryName(), defaultInventory);
    }
    public void checkInventoryAddress() {
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
            Inventory inventory = inventoryList.get(inventoryName);
            System.out.println("Inventory address: " + inventory.getInventoryAddress());
        }
        System.out.println("Empty List");
    }
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
            if(!inventoryList.isEmpty()) {
                Set<String> invKey = invList.keySet();
                index = 1;
                for (String inventory : invKey) {
                    System.out.println(index + ". " + inventory);
                }
                String artworkLocation = scanner.next();
                if (!inventoryList.containsKey(artworkLocation)) {
                    boolean done = false;
                    while (!done) {
                        System.out.println("Inventory not found \n 1. Create Inventory? \n 2. Exit");
                        int option = scanner.nextInt();
                        switch (option) {
                            case 1:
                                createInventory();
                                setArtworkInventory();
                                done = true;
                                break;
                            case 2:
                                Main.mainMenu();
                                break;
                            default:
                                System.out.println("That is not a valid option");
                        }
                    }
                } else if (rentalControllerInstance.getInstituteList().containsKey(artWork.getInventoryLocation()) || expositionControllerInstance.getExpoList().containsKey(artWork.getInventoryLocation())) {
                    System.out.println(artworkName + " is located in: " + artWork.getInventoryLocation());
                    System.out.println("Do you wish to continue? Y/N");
                    String useryn = scanner.next();
                    if (useryn.equalsIgnoreCase("y")) {
                        if (rentalControllerInstance.checkRentalStatus(artWork) || expositionControllerInstance.checkExpoStatus(artWork)) {
                            System.out.println("Artwork can not be put into inventory \n Check Rental or Expo status");
                        }
                    } else {
                        Main.invMenu();
                    }
                } else {
                    artWork.setInventoryLocation(artworkLocation);
                    System.out.println(artworkName + " will be sent to inventory " + artWork.getInventoryLocation());
                }
            }
            System.out.println("Inventory list is empty");
        }
        System.out.println("Artwork list is empty");
    }
    public void createInventory(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inventory name: ");
        String inventoryName = scanner.next();
        System.out.println("Inventory Address: ");
        String inventoryAddress = scanner.next();

        Inventory inventory = new Inventory(inventoryName, inventoryAddress);
        getInventoryList().put(inventoryName, inventory);

        System.out.println("Success! Inventory " + inventoryName + " created.");

    }
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
            if (inventoryList.containsKey(inventoryName)) {
                getInventoryList().remove(inventoryName);
                System.out.println("Success! Inventory " + inventoryName + " has been deleted.");
            } else {
                System.out.println("Inventory not found");
            }
        }
        System.out.println("Empty List");
    }
    public static InventoryController getInstance() {
        if (inventoryListInstance == null) {
            inventoryListInstance = new InventoryController();
        }
        return inventoryListInstance;
    }
    public LinkedHashMap<String, Inventory> getInventoryList() {
        return inventoryList;
    }
}
