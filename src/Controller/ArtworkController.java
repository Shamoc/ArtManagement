package Controller;

import DB_Implementation.ArtworkDaoImplementation;
import Model.Artwork;
import Utils.Utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;

public class ArtworkController {
    private LinkedHashMap<String, Artwork> artworkList;
    private static RentalController instituteListInstance;
    private static ExpositionController expoListInstance;
    private static ArtworkDaoImplementation artDao;

    private static ArtworkController artworkListInstance = null;

    /**
     * Constructor method for ArtworkController class
     *
     * @see Artwork
     */
    private ArtworkController (){
        artDao = new ArtworkDaoImplementation();
        this.artworkList = new LinkedHashMap<>();
        Artwork artWork = new Artwork("mona","lisa","leonardo da vinci", 1850,"renaissance",1);
        artworkList.put(artWork.getName(), artWork);
    }

    /**
     *Method to print all the details of an Artwork
     */
    public void artworkDetails() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        if (!artDao.getArtwork().isEmpty()) {
            showArtworks();
            System.out.println("Select Artwork: ");
            int artworkName = scanner.nextInt();
            Artwork artwork = artDao.getArtwork(artworkName);
            if (artwork != null) {
                System.out.println("Name: " + artwork.getName());
                System.out.println("Description: " + artwork.getDescription());
                System.out.println("Author: " + artwork.getAuthor());
                System.out.println("Art style: " + artwork.getArtStyle());
                System.out.println("Adquisition year: " + artwork.getAcquisitionYear());
            } else {
                System.out.println("Artwork not found. Please input full name.");
                artworkDetails();
            }
        } else {
            System.out.println("Empty List");
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
            Artwork artWork = new Artwork(artworkName.toLowerCase(), artworkDescription, artworkAuthor, artworkAdquisitionYear, artworkArtStyle, 1);
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
    public void deleteArtwork() {
        Scanner scanner = new Scanner(System.in);
        instituteListInstance = RentalController.getInstance();
        expoListInstance = ExpositionController.getInstance();
        if (!artworkList.isEmpty()) {
            //showArtworks();
            System.out.println("Artwork name to delete: ");
            String artName = scanner.next();
            Artwork artWork = artworkList.get(artName);
            if (artworkList.containsKey(artName.toLowerCase())) {
                if (!expoListInstance.onActiveExpo(artWork)) {
                    boolean isOnRent = instituteListInstance.isOnRent(artWork);
                    boolean isRentPaid = isOnRent ? instituteListInstance.isRentPaid(artWork) : true;
                    if (isRentPaid) {
                        artworkList.remove(artName.toLowerCase());
                        System.out.println("Success! Artwork " + artName + " deleted.");
                    } else {
                        System.out.println("Can not delete an Artwork while is being rented.");
                    }
                } else {
                    System.out.println("Can not delete an Artwork while it is in an exposition.");
                }
            } else {
                System.out.println("Artwork not found");
            }
        } else {
            System.out.println("Empty List");
        }
    }

    /**
     * Method to print all the Artworks in the artworkList
     */
    public void showArtworks() throws SQLException {
        if (!artworkList.isEmpty()) {
            int index = 1;
            for (Artwork artworks : artDao.getArtwork()) {
                    System.out.println(index + ". " + artworks.getName());
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
    public LinkedHashMap<String, Artwork> getArtworkList() {
        return artworkList;
    }
}
