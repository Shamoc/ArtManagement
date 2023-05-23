package Utils;

public class Utils {
    /**
     * Method to verify if a string can be an Integer
     *
     * @param strNum Defines the String to be verified.
     * @return Flag whether a string is numeric or not
     */
    public static boolean isNumeric(String strNum) {
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
