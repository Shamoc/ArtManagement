package Controller;

import Model.ArtWork;
import Model.Inventory;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class InventoryController {
    private LinkedHashMap<String, Inventory> inventoryList;

    private static InventoryController inventoryListInstance = null;
    public void createInventory(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inventory name: ");
        String inventoryName = scanner.next();
        System.out.println("Inventory Address: ");
        String inventoryAddress = scanner.next();

        Inventory inventory = new Inventory(inventoryName,inventoryAddress);
        inventoryList.put(inventoryName, inventory);

    }

    public void deleteInventory(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inventory name to delete: ");
        String inventoryName = scanner.next();
        inventoryList.remove(inventoryName);
    }
    public static InventoryController getInstance() {
        if (inventoryListInstance == null) {
            inventoryListInstance = new InventoryController();
        }
        return inventoryListInstance;
    }
}
