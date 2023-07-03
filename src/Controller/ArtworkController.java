package Controller;

import DB_Implementation.ArtworkDaoImplementation;
import Model.Artwork;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ArtworkController {
    private List<Artwork> artworkList;
    private static ArtworkDaoImplementation artDao;

    private static ArtworkController artworkListInstance = null;

    /**
     * Constructor method for ArtworkController class
     *
     * @see Artwork
     */
    private ArtworkController () throws SQLException {
        artDao = ArtworkDaoImplementation.getInstance();
        this.artworkList = artDao.getArtwork();
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
            List<Artwork> artList = artDao.getArtwork();
            Artwork artwork = artList.get(artworkName - 1);
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
    public void createArtwork() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("CREATE ARTWORK \n Artwork name: ");
        String artworkName = scanner.next();
        System.out.println("Artwork Description: ");
        String artworkDescription = scanner.next();
        System.out.println("Artwork author: ");
        String artworkAuthor = scanner.next();
        System.out.println("Artwork adquisition year: ");
        int artworkAdquisitionYear = scanner.nextInt();
        System.out.println("Artwork art style: ");
        String artworkArtStyle = scanner.next();

        Artwork artwork = new Artwork(artworkName, artworkDescription, artworkAuthor, artworkAdquisitionYear, artworkArtStyle, 1);

        if (!artworkList.contains(artwork)) {
            artDao.add(artwork);
            System.out.println("Success! Artwork " + artwork.getName() + " created.");
        } else {
            System.out.println("Artwork name already in use. Try again");
            createArtwork();
        }
    }

    /**
     * Method to delete an Artwork from the Database
     */
    public void deleteArtwork() throws SQLException {
       Scanner scanner = new Scanner(System.in);
        if (!artworkList.isEmpty()) {
            showArtworks();
            System.out.println("Artwork to delete: ");
            int artName = scanner.nextInt();
            Artwork artwork = artworkList.get(artName - 1);
            if (artwork != null) {
                if (artwork.getInv_id() > 0) {
                        artDao.delete(artwork.getArt_id());
                        System.out.println("Success! Artwork " + artName + " deleted.");
                    } else {
                        System.out.println("Artwork needs to be in an Inventory to be deleted");
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
            try {
                artworkListInstance = new ArtworkController();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return artworkListInstance;
    }

    /**
     *Method for the access of the artworkList
     *
     * @return artworkList
     */
    public List<Artwork> getArtworkList() {
        return artworkList;
    }
}
