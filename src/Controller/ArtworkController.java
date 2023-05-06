package Controller;

import Model.ArtWork;

import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;

public class ArtworkController {
    private LinkedHashMap<String, ArtWork> artworkList;

    private static ArtworkController artworkListInstance = null;

    private ArtworkController (){
        this.artworkList = new LinkedHashMap<>();
    }

    public void createArtwork(){
        InventoryController inventoryInstance = InventoryController.getInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Artwork name: ");
        String artworkName = scanner.next();
        System.out.println("Artwork Description: ");
        String artworkDescription = scanner.next();
        System.out.println("Artwork author: ");
        String artworkAuthor = scanner.next();
        System.out.println("Artwork adquisition year: ");
        int artworkAdquisitionYear = scanner.nextInt();
        System.out.println("Artwork artstyle: ");
        String artworkArtStyle = scanner.next();


        ArtWork artWork = new ArtWork(artworkName, artworkDescription, artworkAuthor, artworkAdquisitionYear, artworkArtStyle, "Bodega");
        this.artworkList.put(artWork.getName(), artWork);
    }

    public void deleteArtwork(){
        Scanner scanner = new Scanner(System.in);
        LinkedHashMap<String, ArtWork> artWorkList = artworkList;
        Set<String> key = artWorkList.keySet();
        for (String artworks : key) {
            System.out.println(artworks);
        }
        System.out.println("Artwork name to delete: ");
        String inventoryName = scanner.next();
        getArtworkList().remove(inventoryName);
    }

    public static ArtworkController getInstance() {
        if (artworkListInstance == null) {
            artworkListInstance = new ArtworkController();
        }
        return artworkListInstance;
    }

    public LinkedHashMap<String, ArtWork> getArtworkList() {
        return artworkList;
    }
}
