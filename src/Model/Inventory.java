package Model;

import java.util.LinkedHashMap;

public class Inventory {
    private String inventoryName;
    private String inventoryAddress;

    public Inventory(String inventoryName, String inventoryAddress) {
        this.inventoryName = inventoryName;
        this.inventoryAddress = inventoryAddress;
    }

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
