package GUI.AppPanels.SubMenuPanels.SubDataBasePanels;

import EmployeeDataBase.Employee;
import EmployeeDataBase.ListOfEmployees;
import Enums.EmployeeDataTypes;
import Exceptions.IllegalInputFormatException;
import GUI.AppPanels.BackgroundPanel;
import GUI.AppPanels.SubMenuPanels.DataBasePanel;
import GUI.AppPanels.SubMenuPanels.OperationStatus.FailurePanel;
import GUI.AppPanels.SubMenuPanels.OperationStatus.SuccessPanel;
import GUI.Images.ImageNames;
import InputWorkers.InputDataValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEmployeeSecondPanel extends BackgroundPanel implements ActionListener {

    /** Constructor */
    public AddEmployeeSecondPanel(JFrame mainFrame, Dimension originalFrameSize, EmployeeDataTypes[] employeeDataTypes){
        super(ImageNames.addEmployeeSecondBackgroundImage,originalFrameSize);

        this.mainFrame = mainFrame;
        this.employeeDataTypes = employeeDataTypes;
        setComponents(employeeDataTypes);
        gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        backButton.addActionListener(this);
        confirmButton.addActionListener(this);
        setupLayout();
    }

    /** Children methods */
    private void setComponents(EmployeeDataTypes[] employeeDataTypes){
        dataFeatures = new JLabel[employeeDataTypes.length];
        dataFields = new JTextField[employeeDataTypes.length];
        errorMessages = new JLabel[employeeDataTypes.length];
        for (int k = 0; k < employeeDataTypes.length; k++) {
            dataFeatures[k] = new JLabel("Wprowadź " + employeeDataTypes[k].getDataName() + " pracownika:");
            dataFields[k] = new JTextField();
            errorMessages[k] = new JLabel(" ");
            errorMessages[k].setForeground(Color.red);
        }
    }

    /** Overridden methods */
    @Override
    public void setupLayout() {

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets = new Insets(0 ,0,10 + countAdditionalSize(backButton, gridBagLayout),0);
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.ipady = 5 + countAdditionalSize(backButton, gridBagLayout);
        constraints.ipadx = 10 * countAdditionalSize(backButton, gridBagLayout);
        title.setFont(new Font("Dialog",Font.BOLD,16 + countAdditionalSize(backButton, gridBagLayout)));
        add(title, constraints);

        constraints.gridy += 1;
        for (int k = 0; k < employeeDataTypes.length; k++){
            constraints.fill = GridBagConstraints.BOTH;
            if (k%2 == 0) {
                constraints.gridx = 0;
                constraints.insets = new Insets(0 ,0, countAdditionalSize(dataFeatures[k], gridBagLayout), 10 + countAdditionalSize(backButton, gridBagLayout));
                if(k != 0)
                    constraints.gridy += 3;
            }
            else {
                constraints.insets = new Insets(0 ,0, countAdditionalSize(dataFeatures[k], gridBagLayout), 0);
                constraints.gridx = 1;
            }
            constraints.gridwidth = 1;
            constraints.ipadx = 20 * countAdditionalSize(backButton, gridBagLayout);
            dataFeatures[k].setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(backButton, gridBagLayout)));
            add(dataFeatures[k], constraints);

            constraints.fill = GridBagConstraints.BOTH;
            constraints.gridy += 1;
            constraints.gridwidth = 1;
            dataFields[k].setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(backButton, gridBagLayout)));
            dataFields[k].setPreferredSize(new Dimension(300,dataFields[k].getPreferredSize().height));
            add(dataFields[k], constraints);

            constraints.fill = GridBagConstraints.BOTH;
            constraints.gridy += 1;
            constraints.gridwidth = 1;
            constraints.ipadx = 0;
            errorMessages[k].setFont(new Font("Dialog",Font.BOLD,10 + countAdditionalSize(backButton, gridBagLayout)));
            add(errorMessages[k], constraints);

            constraints.gridy -= 2;
        }

        constraints.insets = new Insets(10 ,0,0,10 + countAdditionalSize(backButton, gridBagLayout));
        constraints.fill = GridBagConstraints.LINE_END;
        constraints.gridx = 0;
        constraints.gridy += 3;
        constraints.gridwidth = 1;
        constraints.ipady = countAdditionalSize(backButton, gridBagLayout);
        constraints.ipadx = 20 * countAdditionalSize(backButton, gridBagLayout);
        backButton.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(backButton, gridBagLayout)));
        backButton.setPreferredSize(new Dimension(preferredButtonSize.width-100, preferredButtonSize.height));
        add(backButton, constraints);

        constraints.fill = GridBagConstraints.LINE_START;
        constraints.gridx = 1;
        constraints.gridwidth = 1;
        confirmButton.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(backButton, gridBagLayout)));
        confirmButton.setPreferredSize(new Dimension(preferredButtonSize.width-100, preferredButtonSize.height));
        add(confirmButton, constraints);
    }

    /** Interface methods */
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == confirmButton) {
            Object[] inputData = new Object[employeeDataTypes.length];
            String[] errors = new String[employeeDataTypes.length];
            boolean errorOccurred = false;
            for (int k = 0; k < employeeDataTypes.length; k++){
                try{
                    inputData[k] = InputDataValidator.findProperInputDataFormat(employeeDataTypes[k],dataFields[k].getText());
                    if(!InputDataValidator.checkInputDataBoundaries(employeeDataTypes[k],inputData[k])){
                        errors[k] = "*Wprowadzone dane nie mieszczą się w dop. zakresie!";
                        errorOccurred = true;
                    }
                    else
                        errors[k] = " ";
                }
                catch (IllegalInputFormatException exception){
                    errors[k] = "*" + exception.getMessage();
                    errorOccurred = true;
                }

            }
            if (errorOccurred){
                    for (int k = 0; k < employeeDataTypes.length; k++){
                        errorMessages[k].setText(errors[k]);
                    }
            }
            else{
                try{
                    ListOfEmployees.addNewEmployee(new Employee(inputData, employeeDataTypes));
                    drawNextPanel(mainFrame, new SuccessPanel(mainFrame, originalFrameSize, new DataBasePanel(mainFrame, originalFrameSize)));
                }
                catch(Exception exception){
                    drawNextPanel(mainFrame, new FailurePanel(mainFrame, originalFrameSize, new DataBasePanel(mainFrame, originalFrameSize)));
                }
            }
        }
        else if (e.getSource() == backButton) {
            drawNextPanel(mainFrame, new AddEmployeeFirstPanel(mainFrame, originalFrameSize));
        }
    }

    /** Fields */
    private final JFrame mainFrame;
    private final GridBagLayout gridBagLayout;
    private final EmployeeDataTypes[] employeeDataTypes;
    private final JLabel title = new JLabel("Wprowadź dane pracownika");
    private JLabel[] dataFeatures;
    private JTextField[] dataFields;
    private JLabel[] errorMessages;
    private final JButton backButton = new JButton("Wstecz");
    private final JButton confirmButton = new JButton("Potwierdź");
}
