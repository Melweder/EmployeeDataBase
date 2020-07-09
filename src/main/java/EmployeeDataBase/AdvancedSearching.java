package EmployeeDataBase;

import Enums.EmployeeDataTypes;
import Exceptions.DataLengthMismatch;

import java.util.LinkedList;

public class AdvancedSearching {

    /** Static methods */
    public static boolean[] findByMultipleCriterion(LinkedList<Employee> employeeList, EmployeeDataTypes[] searchingTypes, String[]... criterion) throws DataLengthMismatch {

        if (searchingTypes.length != criterion.length) {
            throw new DataLengthMismatch();
        }
        boolean[][] resultsTable= new boolean[searchingTypes.length][employeeList.size()];
        for (int k = 0; k < searchingTypes.length; k++) {
            for (Employee employee : employeeList) {
                for (int l = 0; l < criterion[k].length; l++) {
                    if (criterion[k][l].equalsIgnoreCase(employee.getDataMapObject(searchingTypes[k]).toString())) {
                        resultsTable[k][employeeList.indexOf(employee)] = true;
                        break;
                    }
                }
            }
        }
        return countConjunction(resultsTable);
    }

    public static boolean[] findWithCompartment(LinkedList<Employee> employeeList, EmployeeDataTypes[] searchingTypes, String[]... compartment) throws DataLengthMismatch {

        if (compartment.length != 2) {
            throw new DataLengthMismatch();
        }
        for (String[] comps : compartment) {
            if (comps.length != searchingTypes.length)
                throw new DataLengthMismatch();
        }
        // TODO: Poprawic wyszukiwanie dla przedziałow liczbowych!
        boolean[][] resultsTable= new boolean[searchingTypes.length][employeeList.size()];
        for (int k = 0; k < searchingTypes.length; k++) {
            for (Employee employee : employeeList) {
                if (employee.getDataMapObject(searchingTypes[k]).toString().compareTo(compartment[0][k]) >= 0 &&
                        employee.getDataMapObject(searchingTypes[k]).toString().compareTo(compartment[1][k]) <= 0) {

                    resultsTable[k][employeeList.indexOf(employee)] = true;


                }
            }
        }
        return countConjunction(resultsTable);
    }

    private static boolean[] countConjunction (boolean[][] inputTable)
    {
        if(inputTable.length == 0){
            return new boolean[]{};
        }
        boolean[] resultsList = new boolean[inputTable[0].length];
        for (int k = 0; k < inputTable[0].length; k++) {
            boolean conjunction = true;
            for (boolean[] inTab : inputTable) {
                if (!inTab[k]) {
                    conjunction = false;
                    break;
                }
            }
            resultsList[k] = conjunction;
        }
        return resultsList;
    }
}
