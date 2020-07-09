package InputWorkers;

import Enums.*;
import Exceptions.*;

import java.util.HashSet;
import java.util.Set;

public abstract class  InputDataValidator {

    /**Constructors*/
    private InputDataValidator() {}

    /** Static methods*/
    public static String[] stringTokensSplitter(String stringPhrase) {
        Set<String> results = new HashSet<String>();
        String[] tokens = stringPhrase.split(",");
        for (String token : tokens) {
            String[] subTokens = token.split("\\s");
            for (String subToken : subTokens) {
                subToken = subToken.replaceAll("\\s", "");
                if (!subToken.equals("")) {
                    results.add(subToken);
                }
            }
        }
        return results.toArray(new String[]{});
    }

    public static boolean checkInputDataBoundaries(EmployeeDataTypes dataType, Object data) {

        for(int k = 0; k < InputDataBoundaries.values().length; k++) {
            if (dataType.equals(InputDataBoundaries.values()[k].getDataType())) {
                if (InputDataBoundaries.values()[k].getBoundaries().length == 2) {
                    if (dataType.getDataFieldType().equals(String.class)) {
                        return data.toString().length() >= (Integer)InputDataBoundaries.values()[k].getBoundaries()[0]  && data.toString().length() <= (Integer)InputDataBoundaries.values()[k].getBoundaries()[1];
                    }
                    else if (dataType.getDataFieldType().equals(Character.class)) {
                        return ((Character)data).compareTo((Character)InputDataBoundaries.values()[k].getBoundaries()[0]) >= 0 && ((Character)data).compareTo((Character)InputDataBoundaries.values()[k].getBoundaries()[1]) <= 0;
                    }
                    else if (dataType.getDataFieldType().equals(Integer.class)) {
                        return ((Integer)data).compareTo((Integer)InputDataBoundaries.values()[k].getBoundaries()[0]) >= 0 && ((Integer)data).compareTo((Integer)InputDataBoundaries.values()[k].getBoundaries()[1]) <= 0;
                    }
                    else if (dataType.getDataFieldType().equals(Double.class)) {
                        return ((Double)data).compareTo((Double)InputDataBoundaries.values()[k].getBoundaries()[0]) >= 0 && ((Double)data).compareTo((Double)InputDataBoundaries.values()[k].getBoundaries()[1]) <= 0;
                    }
                    else if (dataType.getDataFieldType().equals(Float.class)) {
                        return ((Float)data).compareTo((Float)InputDataBoundaries.values()[k].getBoundaries()[0]) >= 0 && ((Float)data).compareTo((Float)InputDataBoundaries.values()[k].getBoundaries()[1]) <= 0;
                    }
                    else if (dataType.getDataFieldType().equals(Short.class)) {
                        return ((Short)data).compareTo((Short)InputDataBoundaries.values()[k].getBoundaries()[0]) >= 0 && ((Short)data).compareTo((Short)InputDataBoundaries.values()[k].getBoundaries()[1]) <= 0;
                    }
                    else if (dataType.getDataFieldType().equals(Long.class)) {
                        return ((Long)data).compareTo((Long)InputDataBoundaries.values()[k].getBoundaries()[0]) >= 0 && ((Long)data).compareTo((Long)InputDataBoundaries.values()[k].getBoundaries()[1]) <= 0;
                    }
                    else {
                        return data.toString().compareTo(InputDataBoundaries.values()[k].getBoundaries()[0].toString()) >= 0 && data.toString().compareTo(InputDataBoundaries.values()[k].getBoundaries()[1].toString()) <= 0;
                    }
                }
                else {
                    boolean equal = false;
                    for (Object boundary : InputDataBoundaries.values()[k].getBoundaries()) {
                        if (boundary.toString().equals(data.toString())) {
                            equal = true;
                            break;
                        }
                    }
                    return equal;
                }
            }
        }
        return true;
    }
    public static boolean validatePassword(String password){
        return password.equals("Password");
    }
    public static boolean validateUserName(String userName){
        return userName.equals("Login");
    }

    public static Object findProperInputDataFormat (EmployeeDataTypes dataType, String stringData) throws IllegalInputFormatException {

        if (dataType.getDataFieldType().equals(String.class)) {
            return InputDataFormat.formatInputStringWords(stringData);
        }
        else if (dataType.getDataFieldType().equals(Character.class)) {
            return InputDataFormat.formatInputCharacterValues(stringData);
        }
        else if (dataType.getDataFieldType().equals(Integer.class)) {
            return InputDataFormat.formatInputIntegerValues(stringData);
        }
        else if (dataType.getDataFieldType().equals(Double.class)) {
            return InputDataFormat.formatInputDoubleValues(stringData);
        }
        else if (dataType.getDataFieldType().equals(Float.class)) {
            return InputDataFormat.formatInputFloatValues(stringData);
        }
        else if (dataType.getDataFieldType().equals(Boolean.class)) {
            return InputDataFormat.formatInputBooleanValues(stringData);
        }
        else if (dataType.getDataFieldType().equals(Short.class)) {
            return InputDataFormat.formatInputShortValues(stringData);
        }
        else if (dataType.getDataFieldType().equals(Long.class)) {
            return InputDataFormat.formatInputLongValues(stringData);
        }
        else {
            throw new IllegalInputFormatException();
        }
    }
}
