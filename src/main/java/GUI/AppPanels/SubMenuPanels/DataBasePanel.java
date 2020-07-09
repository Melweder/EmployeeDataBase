package GUI.AppPanels.SubMenuPanels;

import EmployeeDataBase.ListOfEmployees;
import GUI.AppPanels.BackgroundPanel;
import GUI.AppPanels.MenuPanel;
import GUI.AppPanels.SubMenuPanels.OperationStatus.SuccessPanel;
import GUI.AppPanels.SubMenuPanels.SubDataBasePanels.*;
import GUI.Images.ImageNames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataBasePanel extends BackgroundPanel implements ActionListener{

    /** Constructor */
    public DataBasePanel(JFrame mainFrame, Dimension originalFrameSize){
        super(ImageNames.dataBaseBackgroundImage, originalFrameSize);

        this.mainFrame = mainFrame;
        gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        addEmployeeButton.addActionListener(this);
        findEmployeeButton.addActionListener(this);
        showEmployeeBaseButton.addActionListener(this);
        additionalFunctionsButton.addActionListener(this);
        removeAllEmployeesButton.addActionListener(this);
        backButton.addActionListener(this);
        setupLayout();
    }

    /** Overridden methods */
    @Override
    public void setupLayout() {

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.ipady = countAdditionalSize(addEmployeeButton, gridBagLayout);
        constraints.ipadx = 20 * countAdditionalSize(addEmployeeButton, gridBagLayout);
        addEmployeeButton.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(addEmployeeButton, gridBagLayout)));
        addEmployeeButton.setPreferredSize(preferredButtonSize);
        add(addEmployeeButton, constraints);

        constraints.insets = new Insets(10 + countAdditionalSize(findEmployeeButton, gridBagLayout) ,0,0,0);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        findEmployeeButton.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(findEmployeeButton, gridBagLayout)));
        findEmployeeButton.setPreferredSize(preferredButtonSize);
        add(findEmployeeButton, constraints);

        constraints.insets = new Insets(10 + countAdditionalSize(showEmployeeBaseButton, gridBagLayout),0,0,0);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        showEmployeeBaseButton.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(showEmployeeBaseButton, gridBagLayout)));
        showEmployeeBaseButton.setPreferredSize(preferredButtonSize);
        add(showEmployeeBaseButton, constraints);

        constraints.insets = new Insets(10 + countAdditionalSize(removeAllEmployeesButton, gridBagLayout),0,0,0);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        removeAllEmployeesButton.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(removeAllEmployeesButton, gridBagLayout)));
        removeAllEmployeesButton.setPreferredSize(preferredButtonSize);
        add(removeAllEmployeesButton, constraints);

        constraints.insets = new Insets(10 + countAdditionalSize(additionalFunctionsButton, gridBagLayout),0,0,0);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        additionalFunctionsButton.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(additionalFunctionsButton, gridBagLayout)));
        additionalFunctionsButton.setPreferredSize(preferredButtonSize);
        add(additionalFunctionsButton, constraints);

        constraints.insets = new Insets(10 + countAdditionalSize(backButton, gridBagLayout),0,0,0);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        backButton.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(backButton, gridBagLayout)));
        backButton.setPreferredSize(preferredButtonSize);
        add(backButton, constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == addEmployeeButton) {
            drawNextPanel(mainFrame, new AddEmployeeFirstPanel(mainFrame, originalFrameSize));
        }
        else if (e.getSource() == findEmployeeButton){
            drawNextPanel(mainFrame, new FindEmployeePanel(mainFrame, originalFrameSize));
        }
        else if (e.getSource() == showEmployeeBaseButton){
            drawNextPanel(mainFrame, new ShowDataBasePanel(mainFrame, originalFrameSize));
        }
        else if (e.getSource() == removeAllEmployeesButton){
            ListOfEmployees.clearListOfEmployees();
            drawNextPanel(mainFrame, new SuccessPanel(mainFrame, originalFrameSize, new DataBasePanel(mainFrame, originalFrameSize)));
        }
        else if (e.getSource() == additionalFunctionsButton){

        }
        else if (e.getSource() == backButton) {
            drawNextPanel(mainFrame, new MenuPanel(mainFrame, originalFrameSize));
        }
    }

    /** Fields */
    private final JFrame mainFrame;
    private final GridBagLayout gridBagLayout;
    private final JButton addEmployeeButton = new JButton("Dodaj pracownika");
    private final JButton findEmployeeButton = new JButton("Znajdź lub edytuj dane pracownika");
    private final JButton showEmployeeBaseButton = new JButton("Wyświetl bazę pracowników");
    private final JButton additionalFunctionsButton = new JButton("Funkcje dodatkowe");
    private final JButton removeAllEmployeesButton = new JButton("Wyczyść bazę pracowników");
    private final JButton backButton = new JButton("Wstecz");
}
