package Enums;
import java.util.HashMap;
import java.util.Map;

public enum EmployeeDataTypes {

    /** Enum fields

     * This data can be changed by authorized person
     * Enum field name stands for a feature describing contained employee data
     * First input argument in enum field constructor stands for a String statement describing this feature
     * Second input argument in enum field constructor stands for a type of class for contained data
     * Third input argument in enum field constructor stands for a status whether this feature is required or not
     */
    NAME("imię", String.class, true),
    SURNAME("nazwisko", String.class, true),
    AGE("wiek", Integer.class, false),
    SEX("płeć", Character.class, true),
    SALARY("dochód", Float.class, true),
    DEP_ID("numer działu", Integer.class, false),
    CHILDREN("ilość dzieci", Integer.class, false),
    MARITAL_STATUS("stan cywilny", Boolean.class, false);

    /** Constructors */
    private <T> EmployeeDataTypes(final String dataName, final Class<T> dataFieldType, final boolean isRequired) {
        this.dataName = dataName;
        this.dataFieldType = dataFieldType;
        this.isRequired = isRequired;
    }

    /** Getters */
    public String getDataName() {
        return dataName;
    }
    public Class<?> getDataFieldType() {
        return dataFieldType;
    }
    public static EmployeeDataTypes findByID(int mapKey) {
        return employeeDataMap.get(mapKey);
    }
    public boolean getIsRequired() { return isRequired; }

    /** Inner enum features */
    private final String dataName;
    private final Class<?> dataFieldType;
    private final boolean isRequired;

    /** Static map containing all fields with their UniqueID keys */
    private static final Map<Integer, EmployeeDataTypes> employeeDataMap;
    static {
        employeeDataMap = new HashMap<Integer, EmployeeDataTypes>();
        int iter = 0;
        for(EmployeeDataTypes empData : EmployeeDataTypes.values())
        {
            employeeDataMap.put(iter, empData);
            iter++;
        }
    }
}
