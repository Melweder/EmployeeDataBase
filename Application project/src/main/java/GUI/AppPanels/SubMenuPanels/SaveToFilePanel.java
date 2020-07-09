package GUI.AppPanels.SubMenuPanels;

import EmployeeDataBase.ListOfEmployees;
import GUI.AppPanels.MenuPanel;
import GUI.AppPanels.SubMenuPanels.OperationStatus.FailurePanel;
import GUI.AppPanels.SubMenuPanels.OperationStatus.SuccessPanel;
import GUI.Images.ImageNames;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SaveToFilePanel extends LoadSavePanel {

    /** Constructor */
    public SaveToFilePanel(JFrame mainFrame, Dimension originalFrameSize){
        super(mainFrame, originalFrameSize, ImageNames.loadFileBackgroundImage);
    }

    /** Overridden methods */
    @Override
    public JLabel setTitle() {
        return new JLabel("Wprowadź ścieżkę oraz nazwę pliku:");
    }

    @Override
    public JTextField setFileDirectory() {
        return new JTextField("Data/EmployeesBase.txt");
    }

    @Override
    public JButton setNextButton() {
        return new JButton("Zapisz");
    }

    @Override
    public void loadOrSaveFile() {
        String fileName = fileDirectory.getText();
        if (!fileName.endsWith(".txt")){
            message.setText("Nieprawidłowe rozszerzenie pliku!");
        }
        else {
            try{
                ListOfEmployees.saveListInFile(new File(fileName));
                drawNextPanel(mainFrame, new SuccessPanel(mainFrame, originalFrameSize, new MenuPanel(mainFrame, originalFrameSize)));
            }
            catch(Exception exception){
                drawNextPanel(mainFrame, new FailurePanel(mainFrame, originalFrameSize, new MenuPanel(mainFrame, originalFrameSize)));
            }
        }
    }
}
