package Controller;

import Model.ArtWork;
import Model.Expositon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;

public class ExpositionController {

    private LinkedHashMap<String, Expositon> expoList;
    private static ExpositionController ExpoListInstance = null;
    private static ArtworkController artworkControllerInstance;
    private LinkedHashMap<String, ArtWork> artworkList = artworkControllerInstance.getArtworkList();

    /**
     * ExpositionController constructor
     */
    private ExpositionController(){
        this.expoList = new LinkedHashMap<>();
        artworkControllerInstance = ArtworkController.getInstance();
        Expositon expositon = new Expositon("exhibition","World Fair");
        String dateStr =  "2010/12/5";
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        expositon.setEndDate(date);
        expoList.put(expositon.getExpoName(), expositon);
    }

    /**
     * Method to set an Artwork to an Exposition
     *
     * @see #artworkControllerInstance
     * @see #showExpositions()
     */
    public void setArtworkExposition() {
        RentalController rentalControllerInstance = RentalController.getInstance();
        Scanner scanner = new Scanner(System.in);
        artworkControllerInstance.showArtworks();
        System.out.println("Select Artwork: ");
        String artworkName = scanner.next();
        ArtWork artWork = artworkList.get(artworkName.toLowerCase());
        if (artWork != null) {
            showExpositions();
            System.out.println("Select Exposition: ");
            String expoName = scanner.next();
            if (expoList.containsKey(expoName.toLowerCase())) {
                boolean rentalListTest = rentalControllerInstance.getInstituteList().containsKey(artWork.getInventoryLocation());
                if (rentalListTest) {
                    rentalListTest = rentalControllerInstance.onActiveRent(artWork);
                    Expositon expo = expoList.get(expoName);
                    boolean expoStatusTest = expo.getExpoStatus();
                    if (!rentalListTest && !expoStatusTest ) {
                        artWork.setInventoryLocation(expoName.toLowerCase());
                        System.out.println(artworkName + " will be added to the exposition " + artWork.getInventoryLocation());
                    } else {
                        System.out.println(artworkName + " is not available for exhibition \n Location: " + artWork.getInventoryLocation());
                        System.out.println("Artwork can not be exhibit");
                    }
                } else {
                    artWork.setInventoryLocation(expoName.toLowerCase());
                    System.out.println(artworkName + " will be added to the exposition " + artWork.getInventoryLocation());
                }
            } else {
                System.out.println("Exposition not found. Try again");
                setArtworkExposition();
                }
        } else {
            System.out.println("Artwork not found. Try again");
            setArtworkExposition();
        }
    }

    /**
     * Method to determine if an Artwork is exhibited in an exposition
     * and the exposition status of the Exposition the Artwork is in
     *
     * @param artWork Artwork used to locate the Exposition
     * @see #updateExpoStatus(String)
     * @return Determines if the Exposition of an Artwork is true or false
     */
    public boolean onActiveExpo(ArtWork artWork) {
        if (artWork != null) {
            String expoContainsArt = artWork.getInventoryLocation();
            if (expoContainsArt != null) {
                if (!expoList.containsKey(expoContainsArt.toLowerCase())) {
                    return false;
                }
                Expositon expositon = expoList.get(expoContainsArt.toLowerCase());
                updateExpoStatus(expositon.getExpoName().toLowerCase());
                if (expositon.getExpoStatus()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method to determine the status of an Exposition
     *
     * @see #showExpositions()
     * @see #updateExpoStatus(String)
     */
    public void checkExpoStatus() {
        Scanner scanner = new Scanner(System.in);
        showExpositions();
        System.out.println("Select Exposition: ");
        String expoName = scanner.next();
        if (expoList.containsKey(expoName.toLowerCase())) {
            updateExpoStatus(expoName.toLowerCase());
            if (expoList.get(expoName.toLowerCase()).getExpoStatus()) {
                System.out.println("Status: Active");
            } else {
                System.out.println("Status: Completed");
            }
            System.out.println("Starting Date: " + expoList.get(expoName.toLowerCase()).getStartDate());
            System.out.println("Ending Date: " + expoList.get(expoName.toLowerCase()).getEndDate());
        } else {
            System.out.println("Exposition not found. Please try again");
            checkExpoStatus();
        }
    }

    /**
     * Method to create an Exposition
     *
     * @see #updateExpoStatus(String)
     */
    public void createExpo(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Exposition name: ");
        String expoName = scanner.next();
        System.out.println("Exposition description: ");
        String expoDes = scanner.next();
        if (!expoList.containsKey(expoName.toLowerCase())) {
            Expositon expo = new Expositon(expoName.toLowerCase(), expoDes);
            this.expoList.put(expo.getExpoName(), expo);
            boolean done = false;
            while (!done) {
                System.out.println("Select starting date");
                Date startingDate = dateConfiguration();
                System.out.println("Select ending date");
                Date endingDate = dateConfiguration();
                if (startingDate.after(endingDate)) {
                    System.out.println("Error! Starting date can not be after ending date.");
                } else {
                    expo.setStartDate(startingDate);
                    expo.setEndDate(endingDate);
                    updateExpoStatus(expo.getExpoName());
                    System.out.println("Success! Exposition " + expoName + " created.");
                    done = true;
                }
            }
        } else {
            System.out.println("Name already in use. Try again");
            createExpo();
        }

    }

    /**
     * Method for the deletion of an Exposition
     *
     * @see #showExpositions()
     * @see #updateExpoStatus(String)
     */
    public void deleteExpo() {
        Scanner scanner = new Scanner(System.in);
        showExpositions();
        System.out.println("Exposition name to delete: ");
        String expoName = scanner.next();
        if (expoList.containsKey(expoName.toLowerCase())) {
            updateExpoStatus(expoName.toLowerCase());
            if(!expoList.get(expoName.toLowerCase()).getExpoStatus()) {
                expoList.remove(expoName.toLowerCase());
                System.out.println("Exposition " + expoName.toLowerCase() + " has been deleted.");
            } else {
                System.out.println("Exposition is Active. Can not be Deleted.");
            }
        } else {
            System.out.println("Exposition not found. Please type the exact name");
            deleteExpo();
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

    /**
     * Method to print all the Expositions in expoList
     */
    public void showExpositions() {
        if (!expoList.isEmpty()) {
            Set<String> expoKey = expoList.keySet();
            int index = 1;
            for (String exposition : expoKey) {
                System.out.println(index + ". " + exposition);
                index++;
            }
        } else {
            System.out.println("Exposition List is empty");
        }
    }

    /**
     * Method to configure a Date
     *
     * @return Date
     */
    private Date dateConfiguration() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select Year");
        int startYear = scanner.nextInt();
        System.out.println("Select Month: ");
        int startMonth = scanner.nextInt();
        System.out.println("Select Day");
        int startDay = scanner.nextInt();
        if(startMonth>12 || startDay>31){
            System.out.println("Error! Month or Day are not correct. Try again.");
            dateConfiguration();
        }
        String date = startYear + "/" + startMonth + "/" + startDay;
        System.out.println("Date is: " + date + "\n Correct? Y/N");
        String yn = scanner.next();
        if (!yn.equalsIgnoreCase("y") || !yn.equalsIgnoreCase("yes")){
            dateConfiguration();
        }
        Date a = null;
        try {
            a = new SimpleDateFormat("yyyy/MM/dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return a;
    }

    /**
     * Updates the status of an Exposition according to current Date
     *
     * @param expoName String to locate the Exposition to update its status
     */
    private void updateExpoStatus(String expoName) {
       Date currentDate = new Date();
       Date end = expoList.get(expoName.toLowerCase()).getEndDate();

        if(end.before(currentDate)){
            expoList.get(expoName).setExpoStatus(false);
        } else if (end.after(currentDate)){
            expoList.get(expoName.toLowerCase()).setExpoStatus(true);
        }
    }
}
