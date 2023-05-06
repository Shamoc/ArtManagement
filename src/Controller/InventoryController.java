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

    public void setArtworkInventory(){
        ArtworkController artworkControllerInstance = ArtworkController.getInstance();
        RentalController rentalControllerInstance = RentalController.getInstance();
        ExpositionController expositionControllerInstance = ExpositionController.getInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Artwork for exposition: ");
        String artworkName = scanner.next();
        ArtWork artWork = artworkControllerInstance.getArtworkList().get(artworkName);
        System.out.println("Exposition name");
        String artworkLocation = scanner.next();
        if (!inventoryList.containsKey(artworkLocation)){
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
                    case 2: Main.mainMenu();
                        break;
                    default:
                        System.out.println("That is not a valid option");
                }
            }
        } else if (rentalControllerInstance.getInstituteList().containsKey(artWork.getInventoryLocation()) || expositionControllerInstance.getExpoList().containsKey(artWork.getInventoryLocation())){
            System.out.println(artworkName + " is located in: " + artWork.getInventoryLocation());
            System.out.println("Do you wish to continue? Y/N");
            String useryn = scanner.next();
            if (useryn.equalsIgnoreCase("y")){
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
        Set<String> key = invList.keySet();
        for (String inventory : key) {
            System.out.println(inventory);
        }
        System.out.println("Inventory name to delete: ");
        String inventoryName = scanner.next();
        getInventoryList().remove(inventoryName);

        System.out.println("Success! Inventory " + inventoryName + " has been deleted.");
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
