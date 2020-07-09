package GUI.AppPanels.SubMenuPanels;

import EmployeeDataBase.ListOfEmployees;
import GUI.AppPanels.MenuPanel;
import GUI.AppPanels.SubMenuPanels.OperationStatus.FailurePanel;
import GUI.AppPanels.SubMenuPanels.OperationStatus.SuccessPanel;
import GUI.Images.ImageNames;

import javax.swing.*;
import java.awt.*;
import java.io.File;


public class LoadFromFilePanel extends LoadSavePanel {

    /** Constructor */
    public LoadFromFilePanel(JFrame mainFrame, Dimension originalFrameSize) {
        super(mainFrame, originalFrameSize, ImageNames.loadFileBackgroundImage);
    }

    /** Overridden methods */
    @Override
    public JLabel setTitle() {
        return new JLabel("Wprowadź ścieżkę pliku:");
    }

    @Override
    public JTextField setFileDirectory() {
        return new JTextField("Data/EmployeesBase.txt");
    }

    @Override
    public JButton setNextButton() {
        return new JButton("Wczytaj");
    }

    @Override
    public void loadOrSaveFile() {

        File file = new File(fileDirectory.getText());
        if(!file.isFile()){
            message.setText("Nie znaleziono docelowego pliku!");
        }
        else {
            try{
                ListOfEmployees.loadListFromFile(file);
                drawNextPanel(mainFrame, new SuccessPanel(mainFrame, originalFrameSize, new MenuPanel(mainFrame, originalFrameSize)));
            }
            catch (Exception exception){
                drawNextPanel(mainFrame, new FailurePanel(mainFrame, originalFrameSize, new MenuPanel(mainFrame, originalFrameSize)));
            }
        }
    }
}
