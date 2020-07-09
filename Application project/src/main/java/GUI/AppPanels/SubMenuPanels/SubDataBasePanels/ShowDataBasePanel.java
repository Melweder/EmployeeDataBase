package GUI.AppPanels.SubMenuPanels.SubDataBasePanels;

import EmployeeDataBase.*;
import Enums.EmployeeDataTypes;
import GUI.AppPanels.BackgroundPanel;
import GUI.AppPanels.SubMenuPanels.DataBasePanel;
import GUI.AppPanels.SubMenuPanels.OperationStatus.SuccessPanel;
import GUI.Images.ImageNames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class ShowDataBasePanel extends BackgroundPanel implements ActionListener {
    public ShowDataBasePanel(JFrame mainFrame, Dimension originalFrameSize){
        super(ImageNames.addEmployeeSecondBackgroundImage,originalFrameSize);

        this.mainFrame = mainFrame;
        gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        sortOrder = createSortingOrderBoxes();
        filterWindow = createFilterWindow();
        sorterWindow = createSorterWindow();

        employeeTable = createTable();
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeTable.setRowSelectionAllowed(true);
        employeeTable.setFillsViewportHeight(true);
        employeeTable.getTableHeader().setReorderingAllowed(false);
        employeeTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        scrollPane = new JScrollPane(employeeTable);

        backButton.addActionListener(this);
        filterButton.addActionListener(this);
        sorterButton.addActionListener(this);
        deleteEmployeeButton.addActionListener(this);
        confirmButton.addActionListener(this);
        confirmButton2.addActionListener(this);

        setupLayout();
    }

    private JTable createTable(){

        String[] labels = new String[EmployeeDataTypes.values().length];
        for(int k = 0; k < EmployeeDataTypes.values().length; k++){
            labels[k] = EmployeeDataTypes.findByID(k).getDataName();
        }
        employeeList = ListOfEmployees.getAllEmployees();
        JTable employeesTable = new JTable(new DefaultTableModel(labels, employeeList.length)){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (int k  = 0; k < employeeList.length; k++){
            for (int j = 0; j < dataCheckBox.length; j++) {
                employeesTable.setValueAt(employeeList[k].getDataMapObject(EmployeeDataTypes.findByID(j)),k,j);
            }
        }
        return employeesTable;
    }

    private void filterTableContent(){

        int criterionLength = 0;
        for (JCheckBox checkBox : dataCheckBox){
            if(checkBox.isSelected()){
                criterionLength++;
            }
        }
        if(!(criterionLength == 0)){
            EmployeeDataTypes[] employeeDataTypes = new EmployeeDataTypes[criterionLength];
            String[] filterLowerBound = new String[criterionLength];
            String[] filterUpperBound = new String[criterionLength];
            criterionLength = 0;
            for(int k = 0; k < dataCheckBox.length; k++){
                if (dataCheckBox[k].isSelected()){
                    employeeDataTypes[criterionLength] = EmployeeDataTypes.findByID(k);
                    filterLowerBound[criterionLength] = this.filterLowerBound[k].getText();
                    filterUpperBound[criterionLength++] = this.filterUpperBound[k].getText();
                }
            }
            employeeList = ListOfEmployees.filterEmployees(employeeDataTypes,filterLowerBound,filterUpperBound);
        }
        else {
            employeeList = ListOfEmployees.getAllEmployees();
        }
        refreshTableContent(employeeList);
        filterWindow.dispose();
    }

    private void refreshTableContent(Employee[] employeeList){

        if(!(employeeList.length == 0)){
            DefaultTableModel tableModel = (DefaultTableModel) employeeTable.getModel();
            int rows = tableModel.getRowCount();
            for (int k  = 0; k < Math.max(employeeList.length, rows); k++){
                if(k < employeeList.length){
                    if(k >= rows){
                        tableModel.addRow(new Object[]{});
                    }
                    for (int j = 0; j < dataCheckBox.length; j++) {
                        tableModel.setValueAt(employeeList[k].getDataMapObject(EmployeeDataTypes.findByID(j)),k,j);
                    }
                }
                else {
                    tableModel.removeRow(tableModel.getRowCount()-1);
                }
            }
        }
    }

    private void sortTable() {

        int criterionLength = 0;
        for (JComboBox jComboBox : sortOrder) {
            if (!jComboBox.getSelectedItem().equals(jComboBox.getItemAt(0))) {
                criterionLength++;
            }
        }
        EmployeeDataTypes[] employeeDataTypes = new EmployeeDataTypes[criterionLength];
        criterionLength = 0;
        for (int k = 0; k < sortOrder.length; k++){
            if (!sortOrder[k].getSelectedItem().equals(sortOrder[k].getItemAt(0))){
                employeeDataTypes[criterionLength++] = EmployeeDataTypes.findByID(sortOrder[k].getSelectedIndex()-1);
            }
        }
        if (!(employeeDataTypes.length == 0)){
            AdvancedSorting.setSortingQueue(employeeDataTypes);
            Arrays.sort(employeeList,AdvancedSorting.AdvancedComparator);
            refreshTableContent(employeeList);
        }
        sorterWindow.dispose();
    }

    private JComboBox[] createSortingOrderBoxes(){
        JComboBox[] sortOrder = new JComboBox[EmployeeDataTypes.values().length];
        String[] options = new String[EmployeeDataTypes.values().length+1];
        options[0] = "(puste)";
        for (int k = 0; k < EmployeeDataTypes.values().length; k++) {
            options[k+1] = EmployeeDataTypes.findByID(k).getDataName();
        }
        for (int k = 0; k < EmployeeDataTypes.values().length; k++){
            sortOrder[k] = new JComboBox(options);
        }
        return sortOrder;
    }

    private JDialog createFilterWindow(){
        JDialog filterWindow = new JDialog(mainFrame, "Filtrowanie wyników");
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new GridBagLayout());
        setupFilterWindowLayout(filterPanel);
        filterWindow.add(filterPanel);
        filterWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        filterWindow.setSize(new Dimension(filterPanel.getPreferredSize().width+16, filterPanel.getPreferredSize().height+39));
        filterWindow.setLocationRelativeTo(mainFrame);
        filterWindow.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
        filterWindow.setResizable(false);
        return filterWindow;
    }

    private JDialog createSorterWindow(){
        JDialog sorterWindow = new JDialog(mainFrame, "Sortowanie wyników");
        JPanel sorterPanel = new JPanel();
        sorterPanel.setLayout(new GridBagLayout());
        setupSorterWindowLayout(sorterPanel);
        sorterWindow.add(sorterPanel);
        sorterWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        sorterWindow.setSize(new Dimension(sorterPanel.getPreferredSize().width+16, sorterPanel.getPreferredSize().height+39));
        sorterWindow.setLocationRelativeTo(mainFrame);
        sorterWindow.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
        sorterWindow.setResizable(false);
        return sorterWindow;
    }

    private void setupFilterWindowLayout(JPanel pane){

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
            filterLowerBound[k] = new JTextField();
            filterLowerBound[k].setEnabled(false);
            filterLowerBound[k].setPreferredSize(new Dimension(200,filterLowerBound[k].getPreferredSize().height));
            filterLowerBound[k].setFont(new Font("Dialog",Font.BOLD,14));
            filterUpperBound[k] = new JTextField();
            filterUpperBound[k].setEnabled(false);
            filterUpperBound[k].setPreferredSize(new Dimension(200,filterLowerBound[k].getPreferredSize().height));
            filterUpperBound[k].setFont(new Font("Dialog",Font.BOLD,14));

            constraints.fill  = GridBagConstraints.HORIZONTAL;

            pane.add(dataCheckBox[k], constraints);
            constraints.gridx += 1;
            insets.left = 0;
            insets.right = 10;
            constraints.insets = insets;
            constraints.fill  = GridBagConstraints.BOTH;
            pane.add(filterLowerBound[k], constraints);
            constraints.gridx += 1;
            pane.add(filterUpperBound[k], constraints);
            constraints.gridx -= 2;
            constraints.gridy += 1;
            insets.left = 10;
            insets.right = 5;
            insets.top = 5;
            constraints.insets = insets;
        }
        constraints.fill  = GridBagConstraints.NONE;
        constraints.insets = new Insets(10 ,0,10,0);
        constraints.gridwidth = 3;
        confirmButton.setPreferredSize(new Dimension(preferredButtonSize.width-100, preferredButtonSize.height));
        pane.add(confirmButton, constraints);
    }
    private void setupSorterWindowLayout(JPanel pane) {

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        Insets insets = new Insets(10 ,10,0,10);
        constraints.insets = insets;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        for (JComboBox jComboBox : sortOrder) {

            pane.add(jComboBox, constraints);
            constraints.gridy += 1;
            insets.top = 5;
            constraints.insets = insets;
        }
        constraints.fill  = GridBagConstraints.NONE;
        constraints.insets = new Insets(10 ,0,10,0);
        confirmButton2.setPreferredSize(new Dimension(preferredButtonSize.width-100, preferredButtonSize.height));
        pane.add(confirmButton2, constraints);
    }

    @Override
    public void setupLayout() {
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 5;
        constraints.ipady = 5 + countAdditionalSize(backButton, gridBagLayout);
        constraints.ipadx = 10 * countAdditionalSize(backButton, gridBagLayout);
        title.setFont(new Font("Dialog",Font.BOLD,16 + countAdditionalSize(backButton, gridBagLayout)));
        add(title, constraints);

        constraints.fill = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10 + countAdditionalSize(backButton, gridBagLayout) ,0,0,0);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 5;
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

        constraints.insets = new Insets(10 + countAdditionalSize(backButton, gridBagLayout) ,0,0,10 + countAdditionalSize(filterButton, gridBagLayout));
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.ipady = countAdditionalSize(filterButton, gridBagLayout);
        constraints.ipadx = 20 * countAdditionalSize(filterButton, gridBagLayout);
        filterButton.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(filterButton, gridBagLayout)));
        filterButton.setPreferredSize(new Dimension(preferredButtonSize.width-100, preferredButtonSize.height));
        add(filterButton, constraints);

        constraints.insets = new Insets(10 + countAdditionalSize(sorterButton, gridBagLayout) ,0,0,10 + countAdditionalSize(sorterButton, gridBagLayout));
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.ipady = countAdditionalSize(sorterButton, gridBagLayout);
        constraints.ipadx = 20 * countAdditionalSize(sorterButton, gridBagLayout);
        sorterButton.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(sorterButton, gridBagLayout)));
        sorterButton.setPreferredSize(new Dimension(preferredButtonSize.width-100, preferredButtonSize.height));
        add(sorterButton, constraints);

        constraints.insets = new Insets(10 + countAdditionalSize(deleteEmployeeButton, gridBagLayout) ,0,0,0);
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 3;
        constraints.gridy = 2;
        constraints.ipady = countAdditionalSize(deleteEmployeeButton, gridBagLayout);
        constraints.ipadx = 20 * countAdditionalSize(deleteEmployeeButton, gridBagLayout);
        deleteEmployeeButton.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(deleteEmployeeButton, gridBagLayout)));
        deleteEmployeeButton.setPreferredSize(new Dimension(preferredButtonSize.width-100, preferredButtonSize.height));
        add(deleteEmployeeButton, constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == filterButton) {
            filterWindow.setVisible(true);
        }
        else if(e.getSource() == sorterButton) {
            sorterWindow.setVisible(true);
        }
        else if(e.getSource() == deleteEmployeeButton) {
            ListOfEmployees.deleteEmployee(employeeList[employeeTable.getSelectedRow()]);
            drawNextPanel(mainFrame, new SuccessPanel(mainFrame, originalFrameSize, new DataBasePanel(mainFrame, originalFrameSize)));
        }
        else if(e.getSource() == confirmButton) {
            filterTableContent();
        }
        else if(e.getSource() == confirmButton2) {
            sortTable();
        }
        else if (e.getSource() == backButton) {
            drawNextPanel(mainFrame, new DataBasePanel(mainFrame, originalFrameSize));
        }
        else if(e.getSource().getClass().equals(JCheckBox.class)) {
            for(int k = 0; k < dataCheckBox.length; k++){
                if(e.getSource() == dataCheckBox[k]){
                    filterLowerBound[k].setEnabled(dataCheckBox[k].isSelected());
                    filterUpperBound[k].setEnabled(dataCheckBox[k].isSelected());
                }
            }
        }
    }

    private Employee[] employeeList;
    private final JFrame mainFrame;
    private final GridBagLayout gridBagLayout;
    private final JTable employeeTable;
    private final JScrollPane scrollPane;
    private final JDialog filterWindow;
    private final JDialog sorterWindow;
    private final JComboBox[] sortOrder;
    private final JLabel title = new JLabel("Baza danych pracowników");
    private final JButton backButton = new JButton("Wstecz");
    private final JButton filterButton = new JButton("Filtruj");
    private final JButton sorterButton = new JButton("Sortuj");
    private final JButton deleteEmployeeButton = new JButton("Usuń pracownika");
    private final JCheckBox[] dataCheckBox = new JCheckBox[EmployeeDataTypes.values().length];
    private final JTextField[] filterLowerBound = new JTextField[EmployeeDataTypes.values().length];
    private final JTextField[] filterUpperBound = new JTextField[EmployeeDataTypes.values().length];
    private final JButton confirmButton = new JButton("OK");
    private final JButton confirmButton2 = new JButton("OK");
}
