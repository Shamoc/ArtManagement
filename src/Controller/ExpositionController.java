package Controller;

import Model.ArtWork;
import Model.Expositon;

import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;

public class ExpositionController {

    private LinkedHashMap<String, Expositon> expoList;
    private static ExpositionController ExpoListInstance = null;

    /**
     * ExpositionController constructor
     */
    private ExpositionController(){
        this.expoList = new LinkedHashMap<>();
        Expositon expositon = new Expositon("the great exhibition","World Fair",true);
        expoList.put(expositon.getExpoName(), expositon);
    }

    /**
     * Method to set an Artwork to an Exposition
     */
    public void setArtworkExposition() {
        ArtworkController artworkControllerInstance = ArtworkController.getInstance();
        RentalController rentalControllerInstance = RentalController.getInstance();
        Scanner scanner = new Scanner(System.in);
        LinkedHashMap<String, ArtWork> artworkList = artworkControllerInstance.getArtworkList();
        if (!artworkList.isEmpty()) {
            Set<String> key = artworkList.keySet();
            int index = 1;
            for (String artworks : key) {
                System.out.println(index + ". " + artworks);
                index++;
            }
            System.out.println("Select Artwork: ");
            String artworkName = scanner.next();
            ArtWork artWork = artworkControllerInstance.getArtworkList().get(artworkName.toLowerCase());
            System.out.println("Select Exposition: ");
            LinkedHashMap<String, Expositon> invList = expoList;
            if (!expoList.isEmpty()) {
                Set<String> expoKey = invList.keySet();
                index = 1;
                for (String exposition : expoKey) {
                    System.out.println(index + ". " + exposition);
                }
                String expoName = scanner.next();
                if (expoList.containsKey(expoName.toLowerCase())) {
                    if (rentalControllerInstance.getInstituteList().containsKey(artWork.getInventoryLocation())) {
                        System.out.println(artworkName + " is not available for exhibition \n Location: " + artWork.getInventoryLocation());
                        if (rentalControllerInstance.onActiveRent(artWork)) {
                            System.out.println("Artwork can not be exhibit \n Check Rental status");
                        } else {
                            return;
                        }
                    }
            } else {
                artWork.setInventoryLocation(expoName.toLowerCase());
                System.out.println(artworkName + " will be added to the exposition " + artWork.getInventoryLocation());
                    }
            } else {
                System.out.println("Exposition List is empty");
            }
        } else {
            System.out.println("Artwork List is empty");
        }
    }

    /**
     * Method to determine if an Artwork is exhibited in an exposition
     * and the exposition status of the Exposition the Artwork is in
     *
     * @param artWork
     * @return Determines if the Exposition of an Artwork is true or false
     */
    public boolean onActiveExpo(ArtWork artWork) {
        String test = artWork.getInventoryLocation();
        if (!expoList.containsKey(test)) {
            return false;
        }
        Expositon expositon = expoList.get(test);
        if (expositon.getExpoStatus()){
            return true;
        }
        return false;
    }

    /**
     * Method to determine the status of an Exposition
     */
    public void checkExpoStatus() {
        Scanner scanner = new Scanner(System.in);
        ArtworkController artInstance =ArtworkController.getInstance();
        System.out.println("Please select artwork: ");
        LinkedHashMap<String, ArtWork> artWorkList = artInstance.getArtworkList();
        Set<String> key = artWorkList.keySet();
        int index = 1;
        for (String artworks : key) {
            System.out.println(index + ". " + artworks);
            index++;
        }
        String selectedArt = scanner.next();
        ArtWork artWork = artWorkList.get(selectedArt.toLowerCase());
        if (onActiveExpo(artWork)){
            getExpoList().get(artWork).getExpoStatus();
        } else {
            System.out.println("Artwork is not in an Exposition.");
        }
    }

    /**
     * Method to create an Exposition
     */
    public void createExpo(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Exposition name: ");
        String expoName = scanner.next();
        System.out.println("Exposition description: ");
        String expoDes = scanner.next();

        Expositon expo = new Expositon(expoName, expoDes, true);
        this.getExpoList().put(expoName, expo);

        System.out.println("Success! Exposition " + expoName + " created.");

    }

    /**
     * Method for the deletion of an Exposition
     */
    public void deleteExpo(){
        Scanner scanner = new Scanner(System.in);
        LinkedHashMap<String, Expositon> invList = expoList;
        if(!expoList.isEmpty()) {
            Set<String> key = invList.keySet();
            int index = 1;
            for (String exposition : key) {
                System.out.println(index + ". " + exposition);
            }
            System.out.println("Exposition name to delete: ");
            String expoName = scanner.next();
            try {
                    Expositon expositon = expoList.get(expoName);
                    getExpoList().remove(expoName);
                    System.out.println("Exposition " + expoName + " has been deleted.");
            }catch (NullPointerException e){
                System.out.println("Exposition not found. Please type the exact name");
                deleteExpo();
            }
        } else {
            System.out.println("Empty List");
        }
    }

    /**
     * Singleton for the ExpositionController
     * @return The same ExpositionController instance
     */
    public static ExpositionController getInstance() {
        if (ExpoListInstance == null) {
            ExpoListInstance = new ExpositionController();
        }
        return ExpoListInstance;
    }

    /**
     * Method to access the expoList
     * @return expoList
     */
    public LinkedHashMap<String, Expositon> getExpoList() {
        return expoList;
    }
}
