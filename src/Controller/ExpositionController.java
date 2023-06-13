package Controller;

import DB_Implementation.ExpositionDaoImplementation;
import Model.Artwork;
import Model.Expositon;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExpositionController {

    private LinkedHashMap<String, Expositon> expoList;
    private static ExpositionController expoListInstance;
    private static ArtworkController artworkControllerInstance;
    private LinkedHashMap<String, Artwork> artworkList;
    private static ExpositionDaoImplementation expoDao;

    /**
     * ExpositionController constructor
     */
    private ExpositionController(){
        this.expoList = new LinkedHashMap<>();
        expoDao = new ExpositionDaoImplementation();
        artworkControllerInstance = ArtworkController.getInstance();
        artworkList = artworkControllerInstance.getArtworkList();
        Expositon expositon = new Expositon("exhibition","World Fair");
        String dateEn =  "2010/12/5";
        String dateSt = "2000/1/5";
        Date date = null;
        Date dateStart = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(dateEn);
            dateStart = new SimpleDateFormat("yyyy/MM/dd").parse(dateSt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date datesql = new java.sql.Date(date.getTime());
        java.sql.Date dateStartsql = new java.sql.Date(dateStart.getTime());
        expositon.setEndDate(datesql);
        expositon.setStartDate(dateStartsql);
        expoList.put(expositon.getExpoName(), expositon);
    }

    /**
     * Method to set an Artwork to an Exposition
     *
     * @see #artworkControllerInstance
     */
    public void setArtworkExposition() {
        RentalController rentalControllerInstance = RentalController.getInstance();
        Scanner scanner = new Scanner(System.in);
        //artworkControllerInstance.showArtworks();
        System.out.println("Select Artwork: ");
        String artworkName = scanner.next();
        Artwork artWork = artworkList.get(artworkName.toLowerCase());
        if (artWork != null) {
            //showExpositions();
            System.out.println("Select Exposition: ");
            String expoName = scanner.next();
            if (expoList.containsKey(expoName.toLowerCase())) {
                /*if(!artWork.getInv_id().equalsIgnoreCase(expoName.toLowerCase())) {
                    boolean rentalListTest = rentalControllerInstance.getInstituteList().containsKey(artWork.getInv_id());
                    Expositon expo = expoList.get(expoName);
                    boolean expoStatusTest = expo.getExpoCompleteStatus();
                    if (rentalListTest || expoStatusTest) {
                        rentalListTest = rentalControllerInstance.isOnRent(artWork);
                        if (!rentalListTest) {
                            //artWork.setInv_id(expoName.toLowerCase());
                            System.out.println(artworkName + " will be added to the exposition " + artWork.getInv_id());
                        } else {
                            System.out.println(artworkName + " is not available for exhibition \n Location: " + artWork.getInv_id());
                            System.out.println("Artwork can not be exhibit");
                        }
                    } else if (expoStatusTest) {
                        //artWork.setInv_id(expoName.toLowerCase());
                        System.out.println(artworkName + " will be added to the exposition " + artWork.getInv_id());
                    } else {
                        System.out.println("Exposition its completed. Can not add Artwork");
                    }
                } else {
                    System.out.println("Artwork is already added to " + expoName.toLowerCase());
                } */
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
    public boolean onActiveExpo(Artwork artWork) {
        if (artWork != null) {
            /*String expoContainsArt = artWork.getInv_id();
            if (!expoList.containsKey(expoContainsArt.toLowerCase())) {
                return false;
            }
            Expositon expositon = expoList.get(expoContainsArt.toLowerCase());
            updateExpoStatus(expositon.getExpoName().toLowerCase());
            if (expositon.getExpoCompleteStatus()) {
                return true;
            } */
        }
        return false;
    }

    /**
     * Method to determine the status of an Exposition
     *
     * @see #updateExpoStatus(String)
     */
    public void checkExpoStatus() {
        Scanner scanner = new Scanner(System.in);
       // showExpositions();
        System.out.println("Select Exposition: ");
        String expoName = scanner.next();
        if (expoList.containsKey(expoName.toLowerCase())) {
            updateExpoStatus(expoName.toLowerCase());
            Date currentDate = new Date();
            Date start = expoList.get(expoName.toLowerCase()).getStartDate();
            Date end = expoList.get(expoName.toLowerCase()).getEndDate();
            if (start.after(currentDate)) {
                if (expoList.get(expoName.toLowerCase()).getExpoCompleteStatus()) {
                    System.out.println("Starting: " + expoList.get(expoName.toLowerCase()).getStartDate());
                }
            } else if (start.before(currentDate)) {
                if (end.after(currentDate)) {
                    System.out.println("Status: Active");
                } else {
                    System.out.println("Status: Completed");
                }
                System.out.println("Starting Date: " + expoList.get(expoName.toLowerCase()).getStartDate());
                System.out.println("Ending Date: " + expoList.get(expoName.toLowerCase()).getEndDate());
            }
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
                java.sql.Date startingDate = dateConfiguration();
                System.out.println("Select ending date");
                java.sql.Date endingDate = dateConfiguration();
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
     * @see #updateExpoStatus(String)
     */
    public void deleteExpo() {
        Scanner scanner = new Scanner(System.in);
        if (!expoList.isEmpty()) {
            //showExpositions();
            System.out.println("Exposition name to delete: ");
            String expoName = scanner.next();
            if (expoList.containsKey(expoName.toLowerCase())) {
                updateExpoStatus(expoName.toLowerCase());
                if (!expoList.get(expoName.toLowerCase()).getExpoCompleteStatus()) {
                    expoList.remove(expoName.toLowerCase());
                    System.out.println("Exposition " + expoName.toLowerCase() + " has been deleted.");
                } else {
                    System.out.println("Exposition is Active. Can not be Deleted.");
                }
            } else {
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
        if (expoListInstance == null) {
            expoListInstance = new ExpositionController();
        }
        return expoListInstance;
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
    public void showExpositions() throws SQLException {
        if (!expoDao.getExposition().isEmpty()) {
            int index = 1;
            for (Expositon exposition : expoDao.getExposition()) {
                System.out.println(index + ". " + exposition.getExpoName());
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
    private java.sql.Date dateConfiguration() {
        java.sql.Date a = null;
        try {
            Scanner scanner = new Scanner(System.in);
            int startYear = 1;
            int startMonth = 1;
            int startDay = 1;
            boolean done = false;
            while (!done) {
                System.out.println("Select Year");
                startYear = scanner.nextInt();
                if (startYear < 1) {
                    System.out.println("Year can not be less than 1");
                } else {
                    done = true;
                }
            }
            done = false;
            while (!done) {
                System.out.println("Select Month: ");
                startMonth = scanner.nextInt();
                if (startMonth > 12) {
                    System.out.println("Error! Please enter a valid Month number");
                } else {
                    done = true;
                }
            }
            done = false;
            while (!done) {
                System.out.println("Select Day");
                startDay = scanner.nextInt();
                if (startDay > 31 || startDay < 1) {
                    System.out.println("Error! Please enter a valid Day number");
                } else {
                    done = true;
                }
            }
            String date = startYear + "/" + startMonth + "/" + startDay;
            System.out.println("Date is: " + date + "\n Correct? Y/N");
            String yn = scanner.next();
            if (!yn.equalsIgnoreCase("y") && !yn.equalsIgnoreCase("yes")) {
                dateConfiguration();
            }
            try {
                a = (java.sql.Date) new SimpleDateFormat("yyyy/MM/dd").parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter only numbers");
            dateConfiguration();
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
       Date start = expoList.get(expoName.toLowerCase()).getStartDate();
        if (start.before(currentDate)) {
            if (end.after(currentDate)) {
                expoList.get(expoName.toLowerCase()).setExpoCompleteStatus(true);
            } else {
                expoList.get(expoName.toLowerCase()).setExpoCompleteStatus(false);
            }
        } else if (start.after(currentDate)) {
            expoList.get(expoName.toLowerCase()).setExpoCompleteStatus(true);
        }
    }
}
