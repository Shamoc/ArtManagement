package Controller;

import Model.Expositon;

import java.util.LinkedHashMap;
import java.util.Scanner;

public class ExpositionController {

    private LinkedHashMap<String, Expositon> expoList;
    private static ExpositionController ExpoListInstance = null;

    private ExpositionController(){
        this.expoList = new LinkedHashMap<>();
    }

    public void createExpo(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Exposition name: ");
        String expoName = scanner.next();
        System.out.println("Exposition description: ");
        String expoDes = scanner.next();

        Expositon expo = new Expositon(expoName,expoDes,Boolean.TRUE);
        this.getExpoList().put(expoName,expo);

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
