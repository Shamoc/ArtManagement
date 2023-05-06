package Controller;

import Model.ArtWork;
import Model.Expositon;
import Model.Inventory;
import Model.Rental;

import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;

public class ExpositionController {

    private LinkedHashMap<String, Expositon> expoList;
    private static ExpositionController ExpoListInstance = null;

    private ExpositionController(){
        this.expoList = new LinkedHashMap<>();
    }
    public void setArtworkExposition() {
        ArtworkController artworkControllerInstance = ArtworkController.getInstance();
        RentalController rentalControllerInstance = RentalController.getInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Artwork for exposition: ");
        String artworkName = scanner.next();
        ArtWork artWork = artworkControllerInstance.getArtworkList().get(artworkName);
        System.out.println("Exposition name");
        String artworkLocation = scanner.next();
        if (!expoList.containsKey(artworkLocation)){
            boolean done = false;
            while (!done) {
                System.out.println("Exposition not found \n 1. Create Exposition? \n 2. Exit");
                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        createExpo();
                        setArtworkExposition();
                        done = true;
                        break;
                    case 2: Main.expoMenu();
                        break;
                    default:
                        System.out.println("That is not a valid option");
                }
            }
        } else if (rentalControllerInstance.getInstituteList().containsKey(artWork.getInventoryLocation())){
            System.out.println(artworkName + " is not available for rental \n Location: " + artWork.getInventoryLocation());
        } else {
            artWork.setInventoryLocation(artworkLocation);
            System.out.println(artworkName + " will be added to the exposition " + artWork.getInventoryLocation());
        }
    }
    public boolean checkExpoStatus(ArtWork artWork) {
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
    public void deleteExpo(){
        Scanner scanner = new Scanner(System.in);
        LinkedHashMap<String, Expositon> invList = expoList;
        Set<String> key = invList.keySet();
        for (String exposition : key) {
            System.out.println(exposition);
        }
        System.out.println("Exposition name to delete: ");
        String expoName = scanner.next();
        getExpoList().remove(expoName);

        System.out.println("Exposition " + expoName + " has been deleted.");
    }
    public static ExpositionController getInstance() {
        if (ExpoListInstance == null) {
            ExpoListInstance = new ExpositionController();
        }
        return ExpoListInstance;
    }
    public LinkedHashMap<String, Expositon> getExpoList() {
        return expoList;
    }
}
