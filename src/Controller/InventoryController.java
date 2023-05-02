package Controller;

import Model.ArtWork;
import Model.Inventory;
import java.util.LinkedHashMap;
import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Artwork name: ");
        String artworkName = scanner.next();
        ArtWork artWork = artworkControllerInstance.getArtworkList().get(artworkName);
        System.out.println("New Inventory Location: ");
        String artworkLocation = scanner.next();

        //Crear flow si existe setearlo si no crearlo.
        Inventory inventory = inventoryList.get(artworkLocation);
        artWork.setInventoryLocation(inventory);

    }

    public void createInventory(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inventory name: ");
        String inventoryName = scanner.next();
        System.out.println("Inventory Address: ");
        String inventoryAddress = scanner.next();

        Inventory inventory = new Inventory(inventoryName, inventoryAddress);
        getInventoryList().put(inventoryName, inventory);

    }

    public void deleteInventory(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inventory name to delete: ");
        String inventoryName = scanner.next();
        getInventoryList().remove(inventoryName);
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
