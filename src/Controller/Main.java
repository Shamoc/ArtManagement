package Controller;

import Model.ArtWork;

import java.util.LinkedHashMap;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        InventoryController inventoryInstance = InventoryController.getInstance();
        ArtworkController artworkInstance = ArtworkController.getInstance();

        System.out.println("Create Artwork");
        artworkInstance.createArtwork();

        /*LinkedHashMap<String, ArtWork> artWorkList =artworkInstance.getArtworkList();
        Set<String> key = artWorkList.keySet();
        for (String artworks : key) {
            System.out.println(artworks);
        }
         */

        inventoryInstance.setArtworkInventory();
    }
}
