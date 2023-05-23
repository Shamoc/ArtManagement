package Controller;

import Model.ArtWork;
import Utils.Utils;

import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;

public class ArtworkController {
    private LinkedHashMap<String, ArtWork> artworkList;

    private static ArtworkController artworkListInstance = null;

    /**
     * Constructor method for ArtworkController class
     *
     * @see ArtWork
     */
    private ArtworkController (){
        this.artworkList = new LinkedHashMap<>();
        ArtWork artWork = new ArtWork("mona","lisa","leonardo da vinci", 1850,"renaissance","storage");
        artworkList.put(artWork.getName(), artWork);
    }

    /**
     *Method to print all the details of an Artwork
     */
    public void artworkDetails() {
        Scanner scanner = new Scanner(System.in);
        showArtworks();
        System.out.println("Select Artwork: ");
        String artworkName = scanner.next();
        if(!Utils.isNumeric(artworkName)) {
            ArtWork artwork = artworkList.get(artworkName);
            if(artwork != null) {
                System.out.println("Name: " + artwork.getName());
                System.out.println("Description: " + artwork.getDescription());
                System.out.println("Author: " + artwork.getAuthor());
                System.out.println("Art style: " + artwork.getArtStyle());
                System.out.println("Adquisition year: " + artwork.getAdquisitionYear());
            } else {
                System.out.println("Artwork not found. Please input full name.");
                artworkDetails();
            }
        } else {
            System.out.println("Provide the name. Please try again.");
            artworkDetails();
        }
    }

    /**
     * Method for the creation of an Artwork Model and to be added to the artworkList.
     * Default inventoryLocation for newly created Artwork is storage
     */
    public void createArtwork(){
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

        if(!artworkList.containsKey(artworkName.toLowerCase())) {
            ArtWork artWork = new ArtWork(artworkName.toLowerCase(), artworkDescription, artworkAuthor, artworkAdquisitionYear, artworkArtStyle, "storage");
            this.artworkList.put(artWork.getName(), artWork);
            System.out.println("Success! Artwork " + artWork.getName() + " created.");
        } else {
            System.out.println("Artwork name already in use. Try again");
            createArtwork();
        }
    }

    /**
     * Method for the removal of an Artwork from the artworkList
     */
    public void deleteArtwork(){
        Scanner scanner = new Scanner(System.in);
        showArtworks();
        System.out.println("Artwork name to delete: ");
        String inventoryName = scanner.next();
        if (artworkList.containsKey(inventoryName.toLowerCase())) {
            artworkList.remove(inventoryName.toLowerCase());
            System.out.println("Success! Artwork " + inventoryName + " deleted.");
        } else {
            System.out.println("Artwork not found");
        }
    }

    /**
     * Method to print all the Artworks in the artworkList
     */
    public void showArtworks() {
        LinkedHashMap<String, ArtWork> artWorkList = artworkList;
        if (!artworkList.isEmpty()) {
            Set<String> key = artWorkList.keySet();
            int index = 1;
            for (String artworks : key) {
                System.out.println(index + ". " + artworks);
                index++;
            }
        } else {
            System.out.println("Empty List");
        }
    }

    /**
     *Singleton for ArtworkController
     *
     * @return The same ArtworkController Instance
     */
    public static ArtworkController getInstance() {
        if (artworkListInstance == null) {
            artworkListInstance = new ArtworkController();
        }
        return artworkListInstance;
    }

    /**
     *Method for the access of the artworkList
     *
     * @return artworkList
     */
    public LinkedHashMap<String, ArtWork> getArtworkList() {
        return artworkList;
    }
}
