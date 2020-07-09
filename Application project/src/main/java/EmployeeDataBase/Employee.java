package EmployeeDataBase;

import Enums.EmployeeDataTypes;
import Exceptions.DataLengthMismatch;

import java.io.Serializable;
import java.util.*;

public class Employee implements Comparable<Employee>, Serializable {

    /** Fields */
    private final Map<EmployeeDataTypes, Object> dataMap;

    /** Constructors */
    public Employee (Object[] data, EmployeeDataTypes[] employeeDataTypes) throws ClassCastException, DataLengthMismatch {

        if (data.length == 0 || data.length > EmployeeDataTypes.values().length || data.length != employeeDataTypes.length) {
            throw new DataLengthMismatch();
        }
        dataMap = new LinkedHashMap<EmployeeDataTypes, Object>();
        for (int k = 0; k<data.length; k++) {
           if (employeeDataTypes[k].getDataFieldType().equals(String.class)) {
                String tempData = (String) data[k];
                dataMap.put(employeeDataTypes[k], tempData);
            }
            else if (employeeDataTypes[k].getDataFieldType().equals(Integer.class)) {
                Integer tempData = (Integer) data[k];
                dataMap.put(employeeDataTypes[k], tempData);
            }
            else if (employeeDataTypes[k].getDataFieldType().equals(Float.class)) {
                Float tempData = (Float) data[k];
                dataMap.put(employeeDataTypes[k], tempData);
            }
            else if (employeeDataTypes[k].getDataFieldType().equals(Character.class)) {
                Character tempData = (Character) data[k];
                dataMap.put(employeeDataTypes[k], tempData);
            }
            else if (employeeDataTypes[k].getDataFieldType().equals(Double.class)) {
                Double tempData = (Double) data[k];
                dataMap.put(employeeDataTypes[k], tempData);
            }
            else if (employeeDataTypes[k].getDataFieldType().equals(Boolean.class)) {
                Boolean tempData = (Boolean) data[k];
                dataMap.put(employeeDataTypes[k], tempData);
            }
            else if (employeeDataTypes[k].getDataFieldType().equals(Short.class)) {
                Short tempData = (Short) data[k];
                dataMap.put(employeeDataTypes[k], tempData);
            }
            else if (employeeDataTypes[k].getDataFieldType().equals(Long.class)) {
                Long tempData = (Long) data[k];
                dataMap.put(employeeDataTypes[k], tempData);
            }
            else {
                dataMap.put(employeeDataTypes[k], data[k]);
            }
        }
    }

    /** Getters */
    public Object getDataMapObject(EmployeeDataTypes mapKey) {
        return dataMap.get(mapKey);
    }
    public int getDataMapLength() {
        return dataMap.size();
    }
    public Class<?> getDataMapType(EmployeeDataTypes mapKey) {
        return dataMap.get(mapKey).getClass();
    }

    /** Setters */
    public void setDataMapObject(EmployeeDataTypes mapKey, Object data) throws ClassCastException {

        if (dataMap.get(mapKey) != null) {
            dataMap.remove(mapKey);
        }
        if (mapKey.getDataFieldType().equals(String.class)) {
            String tempData = (String) data;
            dataMap.put(mapKey,tempData);
        }
        else if (mapKey.getDataFieldType().equals(Integer.class)) {
            Integer tempData = (Integer) data;
            dataMap.put(mapKey,tempData);
        }
        else if (mapKey.getDataFieldType().equals(Float.class)) {
            Float tempData = (Float) data;
            dataMap.put(mapKey,tempData);
        }
        else if (mapKey.getDataFieldType().equals(Character.class)) {
            Character tempData = (Character) data;
            dataMap.put(mapKey,tempData);
        }
        else if (mapKey.getDataFieldType().equals(Double.class)) {
            Double tempData = (Double) data;
            dataMap.put(mapKey,tempData);
        }
        else if (mapKey.getDataFieldType().equals(Boolean.class)) {
            Boolean tempData = (Boolean) data;
            dataMap.put(mapKey,tempData);
        }
        else if (mapKey.getDataFieldType().equals(Short.class)) {
            Short tempData = (Short) data;
            dataMap.put(mapKey,tempData);
        }
        else if (mapKey.getDataFieldType().equals(Long.class)) {
            Long tempData = (Long) data;
            dataMap.put(mapKey,tempData);
        }
        else {
            dataMap.put(mapKey,data);
        }
    }

    /** Methods */
    public String getAllData() {

        StringBuilder dataString = new StringBuilder();
        for (EmployeeDataTypes mapKey : dataMap.keySet()) {
            dataString.append(mapKey.getDataName());
            dataString.append(":\t");
            dataString.append(dataMap.get(mapKey));
            dataString.append(System.lineSeparator());
        }
        return  dataString.toString();
    }
    public String getChosenData(EmployeeDataTypes... mapKeys) {

        StringBuilder dataString = new StringBuilder();
        for (EmployeeDataTypes mapKey : mapKeys) {
            dataString.append(mapKey.getDataName());
            dataString.append(":\t");
            dataString.append(dataMap.get(mapKey));
            dataString.append(System.lineSeparator());
        }
        return  dataString.toString();
    }

    /** Overridden methods */
    @Override
    public int compareTo(Employee employee) {

        int comparingSize = Math.min(employee.getDataMapLength(),this.getDataMapLength());
        for (int k = 0; k <comparingSize; k++) {
            if(this.dataMap.get(EmployeeDataTypes.findByID(k)).toString().compareTo(employee.dataMap.get(EmployeeDataTypes.findByID(k)).toString()) > 0 ) {
                return 1;
            }
            else if (this.dataMap.get(EmployeeDataTypes.findByID(k)).toString().compareTo(employee.dataMap.get(EmployeeDataTypes.findByID(k)).toString()) < 0 ) {
                return -1;
            }
            else {
                if (k == comparingSize - 1) {
                    return 0;
                }
            }
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(dataMap, employee.dataMap);
    }
}
