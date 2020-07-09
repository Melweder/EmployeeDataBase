package EmployeeDataBase;

import Enums.EmployeeDataTypes;

import java.util.Comparator;

public class AdvancedSorting {

    /** Static fields */

    private static EmployeeDataTypes[] sortingQueue = new EmployeeDataTypes[]{EmployeeDataTypes.findByID(0)};


    /**Inner classes */

    public static Comparator<Employee> AdvancedComparator = new Comparator<Employee>() {

        @Override
        public int compare(Employee o1, Employee o2) {

            return extendedCompareTo(setQueue(o1),setQueue(o2));
        }
    };

    /** Getters */

    public static EmployeeDataTypes[] getSortingQueue() {
        return AdvancedSorting.sortingQueue;
    }

    /** Setters */

    public static void setSortingQueue(EmployeeDataTypes[] sortingQueue) {

        AdvancedSorting.sortingQueue = new EmployeeDataTypes[sortingQueue.length];
        AdvancedSorting.sortingQueue = sortingQueue;
    }

    /** Static methods */

    private static int extendedCompareTo(Object[] a, Object[] b) {

        if (a.length == b.length) {
            for (int k = 0; k<a.length; k++) {
                if (a[k].getClass() == String.class) {
                    return ((String)a[k]).compareTo((String)b[k]);
                }
                else if (a[k].getClass() == Integer.class) {
                    return ((Integer)a[k]).compareTo((Integer)b[k]);
                }
                else if (a[k].getClass() == Float.class) {
                    return ((Float)a[k]).compareTo((Float)b[k]);
                }
                else if (a[k].getClass() == Double.class) {
                    return ((Double)a[k]).compareTo((Double)b[k]);
                }
                else if (a[k].getClass() == Boolean.class) {
                    return ((Boolean)a[k]).compareTo((Boolean)b[k]);
                }
                else if (a[k].getClass() == Short.class) {
                    return ((Short)a[k]).compareTo((Short)b[k]);
                }
                else if (a[k].getClass() == Long.class) {
                    return ((Long)a[k]).compareTo((Long)b[k]);
                }
                else if (a[k].getClass() == Character.class) {
                    return ((Character)a[k]).compareTo((Character)b[k]);
                }
            }
        }
        return 0;
    }

    public static Object[] setQueue(Employee employee) {

        Object[] DataQueue = new Object[sortingQueue.length];
        for (int k = 0; k < sortingQueue.length; k++) {
            for (EmployeeDataTypes dataType : EmployeeDataTypes.values()) {
                if (sortingQueue[k].equals(dataType)) {
                    DataQueue[k] = employee.getDataMapObject(dataType);
                }
            }
        }
        return DataQueue;
    }

}
