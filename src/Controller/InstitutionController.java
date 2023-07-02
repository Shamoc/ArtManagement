package Controller;

import DB_Implementation.InstitutionDaoImplementation;
import Model.Institution;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InstitutionController {
    private static InstitutionController instListInstance;
    private InstitutionDaoImplementation instDao;
    private List<Institution> instList;

    /**
     * Constructor for InstitutionController
     *
     */
    private InstitutionController() throws SQLException {
        instDao = InstitutionDaoImplementation.getInstance();
        instList = instDao.getInstitution();
    }

    /**
     * Singleton for RentalController
     *
     * @return The RentalController instance
     */
    public static InstitutionController getInstance() {
        if (instListInstance == null) {
            try {
                instListInstance = new InstitutionController();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return instListInstance;
    }

    /**
         * Method to print all the Institutes in instituteList
         */
    public void showInstitutes() throws SQLException {
            if (!instDao.getInstitution().isEmpty()) {
                int index = 1;
                for (Institution inst : instDao.getInstitution()) {
                    System.out.println(index + ". " + inst.getInstName());
                    index++;
                }
            } else {
                System.out.println("Institutions List is empty");
            }
        }

    /**
     *
     */

    public List<Institution> showRentedInstitutions() throws SQLException {
        List<Institution> instList = instDao.rentedInstitutions();
        int index = 1;
        for (Institution inst : instList){
            System.out.println(index + ". " + inst.getInstName());
            index++;
        }
        return instList;
    }

    /**
     * Method to create an Institute and add it to instituteList
     */
    public void createInstitute() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Rental Institution name: ");
        String instName = scanner.next();
        System.out.println("Rental Institution address: ");
        String instAddress = scanner.next();
        Institution inst = new Institution(instName, instAddress);
        if (!instList.contains(inst)) {
            instDao.add(inst);
            System.out.println("Success! Institute " + inst.getInstName() + " was created.");
        } else {
            System.out.println("Name already in use. Try again");
            createInstitute();
        }
    }

    /**
     * Method to delete an Institute and remove it from instituteList
     *
     */
    public void deleteInstitute() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        if (!instList.isEmpty()) {
            System.out.println("Institute to delete: ");
            showInstitutes();
            String instName = scanner.next();
            if (instList.contains(instName)) {
                instList.remove(instName);
                System.out.println("Success! Institute " + instName.toLowerCase() + " was deleted");
            } else {
                System.out.println("Exposition not found. Please type the exact name");
                deleteInstitute();
            }
        } else {
            System.out.println("Empty list");
        }
    }

    /**
     * Method to print Institution details.
     *
     * @throws SQLException
     */
    public void instDetails() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        showInstitutes();
        System.out.println("Select Institution: ");
        int instID = scanner.nextInt();
        List<Institution> instList = instDao.getInstitution();
        Institution inv = instList.get(instID - 1);
        if (inv!=null) {
            System.out.println("Institution: " + inv.getInstName());
            System.out.println("Institution address: " + inv.getInstAddress());
        } else {
            System.out.println("Institution not found. Please try again");
            instDetails();
        }
    }
}
