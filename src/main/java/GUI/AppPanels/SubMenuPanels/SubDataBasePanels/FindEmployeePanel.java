package GUI.AppPanels.SubMenuPanels.SubDataBasePanels;

import EmployeeDataBase.Employee;
import EmployeeDataBase.ListOfEmployees;
import Enums.EmployeeDataTypes;
import GUI.AppPanels.BackgroundPanel;
import GUI.AppPanels.SubMenuPanels.DataBasePanel;
import GUI.AppPanels.SubMenuPanels.OperationStatus.FailurePanel;
import GUI.AppPanels.SubMenuPanels.OperationStatus.SuccessPanel;
import GUI.Images.ImageNames;
import InputWorkers.InputDataValidator;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FindEmployeePanel extends BackgroundPanel implements ActionListener {
    public FindEmployeePanel(JFrame mainFrame, Dimension originalFrameSize){
        super(ImageNames.addEmployeeSecondBackgroundImage,originalFrameSize);

        this.mainFrame = mainFrame;
        gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        searchWindow = createSearchWindow();

        employeeTable = createTable();
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeTable.setRowSelectionAllowed(true);
        employeeTable.setColumnSelectionAllowed(false);
        employeeTable.setCellSelectionEnabled(true);
        employeeTable.setFillsViewportHeight(true);
        employeeTable.getTableHeader().setReorderingAllowed(false);
        employeeTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        employeeTable.setDefaultRenderer(Object.class, new CustomTableRenderer());
        scrollPane = new JScrollPane(employeeTable);

        backButton.addActionListener(this);
        searchButton.addActionListener(this);
        saveButton.addActionListener(this);
        confirmButton.addActionListener(this);

        setupLayout();
    }

    /** Inner rendering class */
    private class CustomTableRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellRenderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if(userChangesTable[row][column] == 1){
                setBackground(Color.green);
                setToolTipText(null);
            }
            else if (userChangesTable[row][column] == -1) {
                setBackground(Color.red);
                setToolTipText("Wartość poza zakresem!");
            }
            else if (userChangesTable[row][column] == -2) {
                setBackground(Color.red);
                setToolTipText("Błędna wartość!");
            }
            else{
                setBackground(Color.white);
                setToolTipText(null);
            }
            return cellRenderer;
        }
    }

    /** Methods */
    private JTable createTable(){
        String[] labels = new String[EmployeeDataTypes.values().length];
        for(int k = 0; k < EmployeeDataTypes.values().length; k++){
            labels[k] = EmployeeDataTypes.findByID(k).getDataName();
        }
        return new JTable(new DefaultTableModel(labels,0)){
            @Override
            public void setValueAt(Object aValue, int row, int column) {
                super.setValueAt(aValue, row, column);
                checkUserTableInput(aValue,row, column);
            }
        };
    }

    private void refreshTableContent(){
        int criterionLength = 0;
        for (JCheckBox checkBox : dataCheckBox){
            if(checkBox.isSelected()){
                criterionLength++;
            }
        }
        EmployeeDataTypes[] employeeDataTypes = new EmployeeDataTypes[criterionLength];
        String[] criterions = new String[criterionLength];
        criterionLength = 0;
        for(int k = 0; k < dataCheckBox.length; k++){
            if (dataCheckBox[k].isSelected()){
                employeeDataTypes[criterionLength] = EmployeeDataTypes.findByID(k);
                criterions[criterionLength++] = dataFields[k].getText();
            }
        }
        employeesFound = ListOfEmployees.searchForEmployees(employeeDataTypes,criterions);
        userChangesTable = new int[employeesFound.length][EmployeeDataTypes.values().length];
        if(!(employeesFound.length == 0)){
            DefaultTableModel tableModel = (DefaultTableModel) employeeTable.getModel();
            int rows = tableModel.getRowCount();
            for (int k  = 0; k < Math.max(employeesFound.length, rows); k++){
                if(k < employeesFound.length){
                    if(k >= rows){
                        tableModel.addRow(new Object[]{});
                    }
                    for (int j = 0; j < dataCheckBox.length; j++) {
                        tableModel.setValueAt(employeesFound[k].getDataMapObject(EmployeeDataTypes.findByID(j)),k,j);
                    }
                }
                else {
                    tableModel.removeRow(tableModel.getRowCount()-1);
                }
            }
        }
        searchWindow.dispose();
    }

    private void checkUserTableInput(Object data, int row, int column){

        try{
            Object checkedData = InputDataValidator.findProperInputDataFormat(EmployeeDataTypes.findByID(column),(String) data);
            if (!InputDataValidator.checkInputDataBoundaries(EmployeeDataTypes.findByID(column),checkedData)){
                userChangesTable[row][column] = -1;
            }
            else {
                userChangesTable[row][column] = 1;
            }
        }
        catch (Exception e){
            userChangesTable[row][column] = -2;
        }
    }

    private boolean saveUserChanges(){
        for(int k = 0; k < employeeTable.getRowCount(); k++){
            for (int j = 0; j < EmployeeDataTypes.values().length; j++) {
                if (userChangesTable[k][j] == 1) {
                    try{
                        Object data = InputDataValidator.findProperInputDataFormat(EmployeeDataTypes.findByID(j),(String) employeeTable.getValueAt(k,j));
                        employeesFound[k].setDataMapObject(EmployeeDataTypes.findByID(j),data);
                    }
                    catch(Exception e) {
                        System.out.println(e.getMessage());
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private JDialog createSearchWindow(){
        JDialog searchWindow = new JDialog(mainFrame, "Kryteria wyszukiwania");
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridBagLayout());
        setupSearchWindowLayout(searchPanel);
        searchWindow.add(searchPanel);
        searchWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        searchWindow.setSize(new Dimension(searchPanel.getPreferredSize().width+16, searchPanel.getPreferredSize().height+39));
        searchWindow.setLocationRelativeTo(mainFrame);
        searchWindow.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
        searchWindow.setResizable(false);
        return searchWindow;
    }

    private void setupSearchWindowLayout(JPanel pane){

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        Insets insets = new Insets(10 ,10,0,5);
        constraints.insets = insets;

        for (int k = 0; k < dataCheckBox.length; k++){
            dataCheckBox[k] = new JCheckBox(EmployeeDataTypes.findByID(k).getDataName());
            dataCheckBox[k].setSelected(false);
            dataCheckBox[k].setFont(new Font("Dialog",Font.BOLD,14));
            dataCheckBox[k].setHorizontalAlignment(SwingConstants.LEFT);
            dataCheckBox[k].addActionListener(this);
            dataFields[k] = new JTextField();
            dataFields[k].setEnabled(false);
            dataFields[k].setPreferredSize(new Dimension(300,dataFields[k].getPreferredSize().height));
            dataFields[k].setFont(new Font("Dialog",Font.BOLD,14));

            constraints.fill  = GridBagConstraints.HORIZONTAL;

            pane.add(dataCheckBox[k], constraints);
            constraints.gridx += 1;
            insets.left = 0;
            insets.right = 10;
            constraints.insets = insets;
            constraints.fill  = GridBagConstraints.BOTH;
            pane.add(dataFields[k], constraints);
            constraints.gridx -= 1;
            constraints.gridy += 1;
            insets.left = 10;
            insets.right = 5;
            insets.top = 5;
            constraints.insets = insets;
        }
        constraints.fill  = GridBagConstraints.NONE;
        constraints.insets = new Insets(10 ,0,10,0);
        constraints.gridwidth = 2;
        confirmButton.setPreferredSize(new Dimension(preferredButtonSize.width-100, preferredButtonSize.height));
        pane.add(confirmButton, constraints);
    }

    /** Overridden methods */
    @Override
    public void setupLayout() {

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 4;
        constraints.ipady = 5 + countAdditionalSize(backButton, gridBagLayout);
        constraints.ipadx = 10 * countAdditionalSize(backButton, gridBagLayout);
        title.setFont(new Font("Dialog",Font.BOLD,16 + countAdditionalSize(backButton, gridBagLayout)));
        add(title, constraints);

        constraints.fill = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10 + countAdditionalSize(backButton, gridBagLayout) ,0,0,0);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 4;
        constraints.ipady = 0;
        constraints.ipadx = 0;
        employeeTable.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(backButton, gridBagLayout)));
        employeeTable.setRowHeight(25+countAdditionalSize(backButton, gridBagLayout));
        employeeTable.getTableHeader().setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(backButton, gridBagLayout)));
        employeeTable.setPreferredScrollableViewportSize(new Dimension(mainFrame.getSize().width - 100,mainFrame.getSize().height - title.getPreferredSize().height - preferredButtonSize.height - 2 * countAdditionalSize(backButton, gridBagLayout)- 150));
        add(scrollPane, constraints);

        constraints.insets = new Insets(10 + countAdditionalSize(backButton, gridBagLayout) ,0,0,10 + countAdditionalSize(backButton, gridBagLayout));
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.ipady = countAdditionalSize(backButton, gridBagLayout);
        constraints.ipadx = 20 * countAdditionalSize(backButton, gridBagLayout);
        backButton.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(backButton, gridBagLayout)));
        backButton.setPreferredSize(new Dimension(preferredButtonSize.width-100, preferredButtonSize.height));
        add(backButton, constraints);

        constraints.insets = new Insets(10 + countAdditionalSize(backButton, gridBagLayout) ,0,0,10 + countAdditionalSize(searchButton, gridBagLayout));
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.ipady = countAdditionalSize(searchButton, gridBagLayout);
        constraints.ipadx = 20 * countAdditionalSize(searchButton, gridBagLayout);
        searchButton.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(searchButton, gridBagLayout)));
        searchButton.setPreferredSize(new Dimension(preferredButtonSize.width-100, preferredButtonSize.height));
        add(searchButton, constraints);

        constraints.insets = new Insets(10 + countAdditionalSize(saveButton, gridBagLayout) ,0,0,0);
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.ipady = countAdditionalSize(saveButton, gridBagLayout);
        constraints.ipadx = 20 * countAdditionalSize(saveButton, gridBagLayout);
        saveButton.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(saveButton, gridBagLayout)));
        saveButton.setPreferredSize(new Dimension(preferredButtonSize.width-100, preferredButtonSize.height));
        add(saveButton, constraints);
    }

    /** Interface methods */
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == searchButton) {
            searchWindow.setVisible(true);
        }
        else if(e.getSource() == saveButton) {
           if(saveUserChanges()) {
               drawNextPanel(mainFrame, new SuccessPanel(mainFrame, originalFrameSize, new DataBasePanel(mainFrame, originalFrameSize)));
           }
           else {
               drawNextPanel(mainFrame, new FailurePanel(mainFrame, originalFrameSize, new DataBasePanel(mainFrame, originalFrameSize)));
           }
        }
        else if(e.getSource() == confirmButton) {
            refreshTableContent();
        }
        else if (e.getSource() == backButton) {
            drawNextPanel(mainFrame, new DataBasePanel(mainFrame, originalFrameSize));
        }
        else if(e.getSource().getClass().equals(JCheckBox.class)) {
            for(int k = 0; k < dataCheckBox.length; k++){
                if(e.getSource() == dataCheckBox[k]){
                    dataFields[k].setEnabled(dataCheckBox[k].isSelected());
                }
            }
        }
    }

    private Employee[] employeesFound;
    private int[][] userChangesTable;
    private final JFrame mainFrame;
    private final GridBagLayout gridBagLayout;
    private final JTable employeeTable;
    private final JScrollPane scrollPane;
    private final JDialog searchWindow;
    private final JLabel title = new JLabel("Wyszukiwanie pracowników w bazie danych");
    private final JButton backButton = new JButton("Wstecz");
    private final JButton searchButton = new JButton("Szukaj");
    private final JButton saveButton = new JButton("Zapisz zmiany");
    private final JCheckBox[] dataCheckBox = new JCheckBox[EmployeeDataTypes.values().length];
    private final JTextField[] dataFields = new JTextField[EmployeeDataTypes.values().length];
    private final JButton confirmButton = new JButton("OK");

}
