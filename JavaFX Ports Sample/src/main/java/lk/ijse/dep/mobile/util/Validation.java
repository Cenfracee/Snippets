package lk.ijse.dep.mobile.util;

import java.util.regex.Pattern;

public class Validation {


    public static String validateUsername(String userName) {
        if (userName.trim().length() == 0) return "all_spaces";
        else if (userName.trim().length() < 4) return "too_short";
        else if (Pattern.compile("[^(a-zA-Z0-9)][^(a-zA-Z0-9)]").matcher(userName).find())
            return "two_consecutive_symbols";
        else return "valid";
    }

    public static String validateFullName(String fullName) {
        if (fullName.trim().length() == 0) return "all_spaces";
        else if (fullName.trim().length() < 4) return "too_short";
        else if (Pattern.compile("[^(a-zA-Z0-9)][^(a-zA-Z0-9)]").matcher(fullName).find())
            return "two_consecutive_symbols";
        else return "valid";
    }

    public static String validatePassword(String password) {
        if (password.trim().length() < 6) return "too_short";
        else if (!Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^(a-zA-Z0-9)])").matcher(password).find())
            return "too_simple";//Uppercase+Lowercase+Number+Symbol
        else return "valid";
    }

    public static boolean validateCustomerID(String customerID) {//Allowed form CXXX
        return (customerID.matches("^C\\d\\d\\d$"));
    }


    public static boolean validateItemCode(String itemCode) {//Allowed form IXXX
        return (itemCode.matches("^I\\d\\d\\d$"));
    }

    public static boolean validateUnitPrice(String text) {
        return (Pattern.compile("^\\d\\d*(\\.\\d+)?$").matcher(text).find());//integers and floats allowed
    }

    public static boolean validateQty(String text) {
        return (Pattern.compile("^\\d\\d*$").matcher(text).find());//only integers
    }

}
