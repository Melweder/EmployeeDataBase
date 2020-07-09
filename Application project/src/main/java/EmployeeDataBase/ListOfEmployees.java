package EmployeeDataBase;

import Enums.*;
import Exceptions.IllegalInputFormatException;
import Exceptions.DataLengthMismatch;
import GUI.AppPanels.SubMenuPanels.ProgressCounter;
import InputWorkers.InputDataValidator;

import java.io.*;
import java.sql.*;
import java.util.LinkedList;

public class ListOfEmployees {

    private final static LinkedList<Employee> EmployeeContainer = new LinkedList<Employee>();

    private ListOfEmployees() {}

    public static void  addNewEmployee(Employee employee){
        EmployeeContainer.add(employee);
    }
    public static void sortListOfEmployees() {
        EmployeeContainer.sort(AdvancedSorting.AdvancedComparator);
    }
    public static void sortListOfEmployees(EmployeeDataTypes[] sortingQueue) {
        AdvancedSorting.setSortingQueue(sortingQueue);
        EmployeeContainer.sort(AdvancedSorting.AdvancedComparator);
    }
    public static void loadListFromFile(File file) throws ClassNotFoundException, IOException {

        EmployeeContainer.clear();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            LinkedList<?> loadList = (LinkedList<?>) objectInputStream.readObject();
            for (Object obj : loadList) {
                EmployeeContainer.add((Employee) obj);
            }
        }
    }
    public static void saveListInFile(File file) throws IOException  {

        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))){
            objectOutputStream.writeObject(EmployeeContainer);
        }
    }
    public static void loadListFromServer(Connection connection, String tableName) throws SQLException, IllegalInputFormatException, DataLengthMismatch {

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
        EmployeeContainer.clear();
        while(resultSet.next()) {
            Object[] dataReceived = new Object[EmployeeDataTypes.values().length];
            for (int k = 0; k < EmployeeDataTypes.values().length; k++){
                int columnNumber = resultSet.findColumn(EmployeeDataTypes.findByID(k).getDataName());
                if (EmployeeDataTypes.findByID(k).getDataFieldType() != Boolean.class) {
                    dataReceived[k] = InputDataValidator.findProperInputDataFormat(EmployeeDataTypes.findByID(k), resultSet.getString(columnNumber));
                }
                else {
                    dataReceived[k] = InputDataValidator.findProperInputDataFormat(EmployeeDataTypes.findByID(k), Boolean.toString(resultSet.getBoolean(columnNumber)));
                }
            }
            EmployeeContainer.add(new Employee(dataReceived, EmployeeDataTypes.values()));
        }
        connection.close();
    }
    public static void sendListToServer(Connection connection, String tableName) throws SQLException {

        Statement statement = connection.createStatement();
        statement.executeUpdate("TRUNCATE " + tableName);
        String preparedQuery = "INSERT INTO "+ tableName + " VALUES (";
        for(int k = 0; k < EmployeeDataTypes.values().length; k++ ) {
            preparedQuery = preparedQuery.concat("?,");
        }
        preparedQuery = (preparedQuery.substring(0,preparedQuery.length()-1));
        preparedQuery = preparedQuery.concat(")");
        PreparedStatement preparedStatement = connection.prepareStatement(preparedQuery);
        for(Employee employee : EmployeeContainer){
            for (int k = 0; k < EmployeeDataTypes.values().length; k++){
                if(EmployeeDataTypes.findByID(k).getDataFieldType() != Boolean.class) {
                    preparedStatement.setString(k+1,employee.getDataMapObject(EmployeeDataTypes.findByID(k)).toString());
                }
                else {
                    preparedStatement.setBoolean(k+1,(Boolean)employee.getDataMapObject(EmployeeDataTypes.findByID(k)));
                }
            }
            preparedStatement.execute();
        }
        connection.close();
    }
    public static Employee[] searchForEmployees(EmployeeDataTypes[] employeeDataTypes, String[] searchCriterion){
        String[][] tokensTable = new String[employeeDataTypes.length][];
        for(int k = 0; k < employeeDataTypes.length; k++){
            tokensTable[k] = InputDataValidator.stringTokensSplitter(searchCriterion[k]);
        }
        try{
            boolean[] searchResults = AdvancedSearching.findByMultipleCriterion(EmployeeContainer,employeeDataTypes,tokensTable);
            int resultsNumber = 0;
            for (boolean result : searchResults){
                if (result){
                    resultsNumber++;
                }
            }
            Employee[] employeesFound = new Employee[resultsNumber];
            resultsNumber = 0;
            for(int k = 0; k < searchResults.length; k++){
                if (searchResults[k]){
                    employeesFound[resultsNumber++] = EmployeeContainer.get(k);
                }
            }
            return  employeesFound;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return new Employee[]{};
        }
    }
    public static Employee[] filterEmployees(EmployeeDataTypes[] employeeDataTypes, String[] filterLowerBound, String[] filterUpperBound) {
        try{
            boolean[] searchResults = AdvancedSearching.findWithCompartment(EmployeeContainer,employeeDataTypes, filterLowerBound, filterUpperBound);
            int resultsNumber = 0;
            for (boolean result : searchResults){
                if (result){
                    resultsNumber++;
                }
            }
            Employee[] employeesFiltered = new Employee[resultsNumber];
            resultsNumber = 0;
            for(int k = 0; k < searchResults.length; k++){
                if (searchResults[k]){
                    employeesFiltered[resultsNumber++] = EmployeeContainer.get(k);
                }
            }
            return  employeesFiltered;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new Employee[]{};
        }
    }
    public static Employee[] getAllEmployees(){
        Employee[] allEmployeesArray = new Employee[EmployeeContainer.size()];
        for (int k = 0; k < EmployeeContainer.size(); k++){
            allEmployeesArray[k] = EmployeeContainer.get(k);
        }
        return allEmployeesArray;
    }
    public static void clearListOfEmployees(){
        EmployeeContainer.clear();
    }
    public static void deleteEmployee(Employee employee) {
        EmployeeContainer.remove(employee);
    }

    public static void printList(){
        for(Employee employee : EmployeeContainer){
            System.out.println(employee.getAllData());
        }
    }



}

