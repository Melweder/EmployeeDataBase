package Enums;

import java.util.Arrays;

public enum InputDataBoundaries {

    /** Enum fields

     * This data can be changed by authorized person
     * Enum fields stands for a boundaries that affects specific features of EmployeeDataTypes
     * There are available two types of constructors for creating boundaries
     * First type of constructor takes two Object arguments which defines the compartment of created boundary
     * Second type of constructor takes variable number of Object arguments which defines specific set of limitations
     * First input argument for both constructors stands for a type of EmployeeDataTypes feature
     * There is no option for creating boundary of one element set
     * For creating two element set force usage of second type constructor by providing at least three arguments that reiterate
     * To avoid internal errors provide specific type of arguments that match to the EmployeeDataType feature class
     */
    BOUND1(EmployeeDataTypes.NAME, 1, 15),
    BOUND2(EmployeeDataTypes.AGE, 1, 150),
    BOUND3(EmployeeDataTypes.SEX, 'K', 'M', 'M'),
    BOUND4(EmployeeDataTypes.SALARY, 0, 100000),
    BOUND5(EmployeeDataTypes.CHILDREN, 0, 30),
    BOUND6(EmployeeDataTypes.DEP_ID, 0, 1000000),
    BOUND7(EmployeeDataTypes.SURNAME, 1, 50);

    /** Constructors */
    private InputDataBoundaries(final EmployeeDataTypes dataType, final Object lowerBound, final Object upperBound) throws ClassCastException {

        if (dataType.getDataFieldType().equals(String.class) || dataType.getDataFieldType().equals(Character.class) || dataType.getDataFieldType().equals(Integer.class)) {
            this.boundaries = new Integer[]{Integer.parseInt(lowerBound.toString()), Integer.parseInt(upperBound.toString())};
        }
        else if (dataType.getDataFieldType().equals(Float.class)) {
            this.boundaries = new Float[]{Float.parseFloat(lowerBound.toString()), Float.parseFloat(upperBound.toString())};
        }
        else if (dataType.getDataFieldType().equals(Double.class)) {
            this.boundaries = new Double[]{Double.parseDouble(lowerBound.toString()), Double.parseDouble(upperBound.toString())};
        }
        else if (dataType.getDataFieldType().equals(Short.class)) {
            this.boundaries = new Short[]{Short.parseShort(lowerBound.toString()), Short.parseShort(upperBound.toString())};
        }
        else if (dataType.getDataFieldType().equals(Long.class)) {
            this.boundaries = new Long[]{Long.parseLong(lowerBound.toString()), Long.parseLong(upperBound.toString())};
        }
        else
            throw new ClassCastException("Cannot initialize Boundaries of this data type");

        this.dataType = dataType;
    }
    private InputDataBoundaries(final EmployeeDataTypes dataType, final Object... elements) throws ClassCastException {

        if (elements.length <= 1) {
            throw new ClassCastException("Cannot create Boundaries from one or less data arguments!");
        }

        if(dataType.getDataFieldType().equals(String.class)) {
            this.boundaries = new String[elements.length];
            for (int k = 0; k<elements.length; k++) {
                this.boundaries[k] = elements[k].toString();
            }
        }
        else if (dataType.getDataFieldType().equals(Character.class)) {
            this.boundaries = new Character[elements.length];
            for (int k = 0; k<elements.length; k++) {
                this.boundaries[k] = elements[k].toString().charAt(0);
            }
        }
        else if (dataType.getDataFieldType().equals(Integer.class)) {
            this.boundaries = new Integer[elements.length];
            for (int k = 0; k<elements.length; k++) {
                this.boundaries[k] = Integer.parseInt(elements[k].toString());
            }
        }
        else if (dataType.getDataFieldType().equals(Double.class)) {
            this.boundaries = new Double[elements.length];
            for (int k = 0; k<elements.length; k++) {
                this.boundaries[k] = Double.parseDouble(elements[k].toString());
            }
        }
        else if (dataType.getDataFieldType().equals(Float.class)) {
            this.boundaries = new Float[elements.length];
            for (int k = 0; k<elements.length; k++) {
                this.boundaries[k] = Float.parseFloat(elements[k].toString());
            }
        }
        else if (dataType.getDataFieldType().equals(Short.class)) {
            this.boundaries = new Short[elements.length];
            for (int k = 0; k<elements.length; k++) {
                this.boundaries[k] = Short.parseShort(elements[k].toString());
            }
        }
        else if (dataType.getDataFieldType().equals(Long.class)) {
            this.boundaries = new Long[elements.length];
            for (int k = 0; k<elements.length; k++) {
                this.boundaries[k] = Long.parseLong(elements[k].toString());
            }
        }
        else
            throw new ClassCastException("Cannot initialize Boundaries of this data type");

        this.dataType = dataType;
    }

    /** Getters */
    public EmployeeDataTypes getDataType() {
        return dataType;
    }
    public Object[] getBoundaries() {
        return boundaries;
    }

    /** Inner enum features */
    private final EmployeeDataTypes dataType;
    private final Object[] boundaries;

    // Checking whether there are no Boundaries of the same data type
    static {
        for (int k = 0; k < InputDataBoundaries.values().length - 1; k++) {
            for (int l = k + 1 ; l < InputDataBoundaries.values().length; l++) {
                if (InputDataBoundaries.values()[k].dataType.equals(InputDataBoundaries.values()[l].dataType)) {
                    throw new ExceptionInInitializerError("Boundaries of "+InputDataBoundaries.values()[k].dataType+" already exists!");
                }
            }
        }
    }
}
