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

    public void artworkDetails() {
        Scanner scanner = new Scanner(System.in);
        LinkedHashMap<String, ArtWork> artWorkList = artworkList;
        if (!artworkList.isEmpty()) {
            Set<String> key = artWorkList.keySet();
            int index = 1;
            for (String artworks : key) {
                System.out.println(index + ". " + artworks);
                index++;
            }
            System.out.println("Select Artwork: ");
            String artworkName = scanner.next();
            ArtWork artwork = artworkList.get(artworkName);
            System.out.println("Name: " + artwork.getName());
            System.out.println("Description: " + artwork.getDescription());
            System.out.println("Author: " + artwork.getAuthor());
            System.out.println("Art style: " + artwork.getArtStyle());
            System.out.println("Adquisition year: " + artwork.getAdquisitionYear());
        }
        System.out.println("Empty list");
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
        System.out.println("Artwork art style: ");
        String artworkArtStyle = scanner.next();


        ArtWork artWork = new ArtWork(artworkName, artworkDescription, artworkAuthor, artworkAdquisitionYear, artworkArtStyle, "Bodega");
        this.artworkList.put(artWork.getName(), artWork);
        System.out.println("Success! Artwork " + artWork.getName() + " created.");
    }

    public void deleteArtwork(){
        Scanner scanner = new Scanner(System.in);
        LinkedHashMap<String, ArtWork> artWorkList = artworkList;
        if (!artworkList.isEmpty()) {
            Set<String> key = artWorkList.keySet();
            int index = 1;
            for (String artworks : key) {
                System.out.println(index + ". " + artworks);
                index++;
            }
            System.out.println("Artwork name to delete: ");
            String inventoryName = scanner.next();
            if (artworkList.containsKey(inventoryName)) {
                getArtworkList().remove(inventoryName);
                System.out.println("Success! Artwork " + inventoryName + " deleted.");
            } else {
                System.out.println("Artwork not found");
            }
        }
        System.out.println("Empty List");
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
