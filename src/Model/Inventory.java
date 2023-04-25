package Model;

import java.util.LinkedHashMap;

public class Inventory {
   // private LinkedHashMap<String, ArtWork> inventoryArtWorkList;
    private String inventoryName;
    private String inventoryAddress;

    public Inventory(String inventoryName, String inventoryAddress) {
        this.inventoryName = inventoryName;
        this.inventoryAddress = inventoryAddress;
    }

   /* public LinkedHashMap<String, ArtWork> getInventoryArtWorkList() {
        return inventoryArtWorkList;
    }

    */

    /*public void setInventoryArtWorkList(LinkedHashMap<String, ArtWork> inventoryArtWorkList) {
        this.inventoryArtWorkList = inventoryArtWorkList;
    }

     */

    public String getInventoryName() {
        return inventoryName;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public String getInventoryAddress() {
        return inventoryAddress;
    }

    public void setInventoryAddress(String inventoryAddress) {
        this.inventoryAddress = inventoryAddress;
    }
}
