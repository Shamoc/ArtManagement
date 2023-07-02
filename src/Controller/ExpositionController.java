package Controller;

import DB_Implementation.ArtworkDaoImplementation;
import DB_Implementation.ExpositionDaoImplementation;
import Model.Artwork;
import Model.Expositon;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExpositionController {

    private List<Expositon> expoList;
    private static ExpositionController expoInstance;
    private static ArtworkController artInstance;
    private List<Artwork> artworkList;
    private static ArtworkDaoImplementation artDao;
    private static ExpositionDaoImplementation expoDao;

    /**
     * ExpositionController constructor
     */
    private ExpositionController() throws SQLException {
        artDao = ArtworkDaoImplementation.getInstance();
        expoDao = ExpositionDaoImplementation.getInstance();
        artInstance = ArtworkController.getInstance();
        artworkList = artDao.getArtForExpo();
        this.expoList = expoDao.getExposition();
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
    }

    /**
     * Method to set an Artwork to an Exposition
     *
     * @see #artInstance
     */
    public void setArtworkExposition() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        showArtForExpo();
        List<Artwork> artsList = artDao.getArtForExpo();
        System.out.println("Select Artwork: ");
        int artworkID = scanner.nextInt();
        Artwork artWork = artsList.get(artworkID - 1);
        if (artWork != null) {
            showExpositions();
            System.out.println("Select Exposition: ");
            int userExpo = scanner.nextInt();
            Expositon expo = expoList.get(userExpo - 1);
            if (expo != null) {
                updateExpoStatus(expo);
                if (expo.getExpoStatus().equalsIgnoreCase("Active")) {
                    artDao.updateArtToExpo(artWork.getArt_id(), expo.getExpo_id());
                    System.out.println("Success");
                } else {
                    System.out.println("Exposition is not active");
                }
            }
        }
    }

    /**
     * Method to create an Exposition
     *
     */
    public void createExpo() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("CREATE EXPO \n Exposition name: ");
        String expoName = scanner.next();
        System.out.println("Exposition description: ");
        String expoDes = scanner.next();
        Expositon expositon = new Expositon(expoName, expoDes);
        if (!expoList.contains(expositon)) {
            boolean done = false;
            while (!done) {
                System.out.println("Select STARTING DATE");
                java.sql.Date startingDate = dateConfiguration();
                System.out.println("Select ENDING DATE");
                java.sql.Date endingDate = dateConfiguration();
                if (startingDate.after(endingDate)) {
                    System.out.println("Error! Starting date can not be after ending date.");
                } else {
                    expositon.setStartDate(startingDate);
                    expositon.setEndDate(endingDate);
                    updateExpoStatus(expositon);
                    expoDao.add(expositon);

                    System.out.println("Success! Exposition " + expositon.getExpoName() + " created.");
                    done = true;
                }
            }
        } else {
            System.out.println("Name already in use. Try again");
            createExpo();
        }

    }

    /**
     * Method for the print of an Exposition details.
     *
     * @throws SQLException
     */
    public void expoDetails() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        showExpositions();
        System.out.println("Select Exposition: ");
        int expoID = scanner.nextInt();
        List<Expositon> expoList = expoDao.getExposition();
        Expositon expo = expoList.get(expoID - 1);
        updateExpoStatus(expo);
        if (expo!=null) {
            System.out.println("Exposition: " + expo.getExpoName());
            System.out.println("Exposition description: " + expo.getExpoDescription());
            System.out.println("Exposition status: " + expo.getExpoStatus());
            System.out.println("Exposition start date: " + expo.getStartDate());
            System.out.println("Exposition end date: " + expo.getEndDate());
        } else {
            System.out.println("Exposition not found. Please try again");
            expoDetails();
        }
    }

    /**
     * Method for the deletion of an Exposition
     *
     */
    public void deleteExpo() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        if (!expoList.isEmpty()) {
            showExpositions();
            System.out.println("Exposition to delete: ");
            int expoID = scanner.nextInt();
            Expositon expo = expoList.get(expoID - 1);
            if (expo != null) {
                updateExpoStatus(expo);
                if (expo.getExpoStatus().equalsIgnoreCase("Completed")) {
                    expoDao.delete(expo.getExpo_id());
                    System.out.println("Exposition " + expo.getExpoName() + " has been deleted.");
                } else {
                    System.out.println("Exposition is Active. Can not be Deleted.");
                }
            } else {
                System.out.println("Exposition not found");
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
        if (expoInstance == null) {
            try {
                expoInstance = new ExpositionController();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return expoInstance;
    }

    /**
     * Method to access the expoList
     * @return expoList
     */
    public List<Expositon> getExpoList() {
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
        Date a = null;
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
                a = new SimpleDateFormat("yyyy/MM/dd").parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter only numbers");
            dateConfiguration();
        }
        java.sql.Date dateSql = new java.sql.Date(a.getTime());
        return dateSql;
    }

    /**
     * Updates the status of an Exposition according to current Date
     *
     */
    private void updateExpoStatus(Expositon expo) throws SQLException {
        Date nowDate = new Date();
        java.sql.Date currentDate = new java.sql.Date(nowDate.getTime());
        Date end = expo.getEndDate();
        Date start = expo.getStartDate();
        if (start.before(currentDate)) {
            if (end.after(currentDate)) {
                expoDao.update(expo);
                expo.setExpoStatus("Active");
            } else {
                expoDao.update(expo);
                artDao.updateArtToInvFromExpo(expo.getExpo_id());
                expo.setExpoStatus("Completed");
            }
        } else if (start.after(currentDate)) {
            expoDao.update(expo);
            expo.setExpoStatus("Active");
        }
    }

    /**
     * Method to print all the Artwork available for Exposition
     *
     * @throws SQLException
     */
    public void showArtForExpo() throws SQLException {
        if (!artworkList.isEmpty()) {
            int index = 1;
            for (Artwork artworks : artDao.getArtForExpo()) {
                System.out.println(index + ". " + artworks.getName());
                index++;
            }
        } else {
            System.out.println("Empty List");
        }
    }
}
