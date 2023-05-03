package Controller;

import Model.ArtWork;
import Model.Expositon;
import Model.Inventory;

import java.util.LinkedHashMap;
import java.util.Scanner;

public class ExpositionController {

    private LinkedHashMap<String, Expositon> expoList;
    private static ExpositionController ExpoListInstance = null;

    private ExpositionController(){
        this.expoList = new LinkedHashMap<>();
    }

    public void setArtworkExposition(){
        ArtworkController artworkControllerInstance = ArtworkController.getInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Artwork for rental: ");
        String artworkName = scanner.next();
        ArtWork artWork = artworkControllerInstance.getArtworkList().get(artworkName);
        System.out.println("Set Institute that will be renting");
        String artworkLocation = scanner.next();

        if (!expoList.containsKey(artworkLocation)){
            System.out.println("Institute not found \n Create Institute?");
        } else {
            artWork.setInventoryLocation(artworkLocation);
        }
    }

    public void createExpo(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Exposition name: ");
        String expoName = scanner.next();
        System.out.println("Exposition description: ");
        String expoDes = scanner.next();

        Expositon expo = new Expositon(expoName, expoDes, true);
        this.getExpoList().put(expoName, expo);

    }

    public void deleteExpo(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Exposition name to delete: ");
        String expoName = scanner.next();
        getExpoList().remove(expoName);
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
