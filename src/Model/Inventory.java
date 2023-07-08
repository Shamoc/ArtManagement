package Model;

public class Inventory {
    private int inv_id;
    private String inventoryName;
    private String inventoryAddress;

    public Inventory() {}
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

    public int getInv_id() {
        return inv_id;
    }

    public void setInv_id(int inv_id) {
        this.inv_id = inv_id;
    }
}
