package InputWorkers;

import Exceptions.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public abstract class InputDataFormat {

    /**Constructors*/
    private InputDataFormat(){}

    /** Static methods */
    public static String formatInputStringWords(String stringData) throws IllegalInputFormatException {

        if (stringData.length() == 0) {
            throw new IllegalInputFormatException("Wprowadzono pustą wartość!");
        }
        char[] charName = stringData.toCharArray();
        Integer[] specialSigns = new Integer[]{260, 262, 280, 321, 323, 211, 346, 377, 379, 261, 263, 281, 322, 324, 243, 347, 378, 380};
        Set<Integer> signsSet = new HashSet<Integer>(Arrays.asList(specialSigns));
        for (int k = 0; k<stringData.length(); k++) {
            if ((int)charName[k] <= 64 || (int)charName[k] >= 123 || ((int)charName[k] <= 96 && (int)charName[k] >= 91)) {
                if (!signsSet.contains((int)charName[k])) {
                    throw new IllegalInputFormatException();
                }
            }
        }
        return stringData.substring(0, 1).toUpperCase() + stringData.substring(1).toLowerCase();
    }

    public static boolean formatInputBooleanValues(String stringData) throws IllegalInputFormatException {

        if (stringData.length() == 0) {
            throw new IllegalInputFormatException("Wprowadzono pustą wartość!");
        }
        if (stringData.toUpperCase().equals(Boolean.TRUE.toString()) || stringData.toUpperCase().equals(Boolean.FALSE.toString())) {
            throw new IllegalInputFormatException("Wprowadzono wartość inną niż TRUE lub FALSE!");
        }
        return Boolean.parseBoolean(stringData);
    }

    public static int formatInputIntegerValues(String stringData) throws IllegalInputFormatException {

        if (stringData.length() == 0) {
            throw new IllegalInputFormatException("Wprowadzono pustą wartość!");
        }
        char[] charValue = stringData.toCharArray();
        for (int k = 0; k<stringData.length(); k++) {
            if ((int)charValue[k] <= 47 || (int)charValue[k] >= 58) {
                if (!(k == 0 && ((int)charValue[k] == 45 || (int)charValue[k] == 43)))
                    throw new IllegalInputFormatException();
            }
        }
        try {
            return Integer.parseInt(stringData);
        }
        catch (NumberFormatException e) {
            if (stringData.compareTo(String.valueOf(Integer.MIN_VALUE)) < 0)
                return Integer.MIN_VALUE;
            else if (stringData.compareTo(String.valueOf(Integer.MAX_VALUE)) > 0)
                return Integer.MAX_VALUE;
            else
                return 0;
        }
    }

    public static float formatInputFloatValues(String stringData) throws IllegalInputFormatException {

        if (stringData.length() == 0) {
            throw new IllegalInputFormatException("Wprowadzono pustą wartość!");
        }
        char[] charValue = stringData.toCharArray();
        boolean multipleDots = false;
        for (int k = 0; k<stringData.length(); k++) {
            if ((int)charValue[k] <= 47 || (int)charValue[k] >= 58) {
                if ((int)charValue[k] == 46 && !multipleDots) {
                    multipleDots = true;
                }
                else {
                    if (!(k == 0 && ((int)charValue[k] == 45 || (int)charValue[k] == 43)))
                        throw new IllegalInputFormatException();
                }
            }
        }
        try {
            return Float.parseFloat(stringData);
        }
        catch (NumberFormatException e) {
            if (stringData.compareTo(String.valueOf(Float.MIN_VALUE)) < 0)
                return Float.MIN_VALUE;
            else if (stringData.compareTo(String.valueOf(Float.MAX_VALUE)) > 0)
                return Float.MAX_VALUE;
            else
                return 0;
        }
    }

    public static double formatInputDoubleValues(String stringData) throws IllegalInputFormatException {

        if (stringData.length() == 0) {
            throw new IllegalInputFormatException("Wprowadzono pustą wartość!");
        }
        char[] charValue = stringData.toCharArray();
        boolean multipleDots = false;
        for (int k = 0; k<stringData.length(); k++) {
            if ((int)charValue[k] <= 47 || (int)charValue[k] >= 58) {
                if ((int)charValue[k] == 46 && !multipleDots) {
                    multipleDots = true;
                }
                else {
                    if (!(k == 0 && ((int)charValue[k] == 45 || (int)charValue[k] == 43)))
                        throw new IllegalInputFormatException();
                }
            }
        }
        try {
            return Double.parseDouble(stringData);
        }
        catch (NumberFormatException e) {
            if (stringData.compareTo(String.valueOf(Double.MIN_VALUE)) < 0)
                return Double.MIN_VALUE;
            else if (stringData.compareTo(String.valueOf(Double.MAX_VALUE)) > 0)
                return Double.MAX_VALUE;
            else
                return 0;
        }
    }

    public static char formatInputCharacterValues(String stringData) throws IllegalInputFormatException{

        if (stringData.length() != 1) {
            throw new IllegalInputFormatException("Wprowadzono niepoprawną ilość znaków!");
        }
        char checkChar = stringData.charAt(0);
        if ((int)checkChar <= 64 || (int)checkChar >= 123 || ((int)checkChar <= 96 && (int)checkChar >= 91)) {
            throw new IllegalInputFormatException("Wprowadzono niedozwolony znak");
        }
        return Character.toUpperCase(checkChar);
    }

    public static short formatInputShortValues(String stringData) throws IllegalInputFormatException {

        if (stringData.length() == 0) {
            throw new IllegalInputFormatException("Wprowadzono pustą wartość!");
        }
        char[] charValue = stringData.toCharArray();
        for (int k = 0; k<stringData.length(); k++) {
            if ((int)charValue[k] <= 47 || (int)charValue[k] >= 58) {
                if (!(k == 0 && ((int)charValue[k] == 45 || (int)charValue[k] == 43)))
                    throw new IllegalInputFormatException();
            }
        }
        try {
            return Short.parseShort(stringData);
        }
        catch (NumberFormatException e) {
            if (stringData.compareTo(String.valueOf(Short.MIN_VALUE)) < 0)
                return Short.MIN_VALUE;
            else if (stringData.compareTo(String.valueOf(Short.MAX_VALUE)) > 0)
                return Short.MAX_VALUE;
            else
                return 0;
        }
    }

    public static long formatInputLongValues(String stringData) throws IllegalInputFormatException {

        if (stringData.length() == 0) {
            throw new IllegalInputFormatException("Wprowadzono pustą wartość!");
        }
        char[] charValue = stringData.toCharArray();
        for (int k = 0; k<stringData.length(); k++) {
            if ((int)charValue[k] <= 47 || (int)charValue[k] >= 58) {
                if (!(k == 0 && ((int)charValue[k] == 45 || (int)charValue[k] == 43)))
                    throw new IllegalInputFormatException();
            }
        }
        try {
            return Long.parseLong(stringData);
        }
        catch (NumberFormatException e) {
            if (stringData.compareTo(String.valueOf(Long.MIN_VALUE)) < 0)
                return Long.MIN_VALUE;
            else if (stringData.compareTo(String.valueOf(Long.MAX_VALUE)) > 0)
                return Long.MAX_VALUE;
            else
                return 0;
        }
    }
}
