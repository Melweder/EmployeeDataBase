package GUI.AppPanels.SubMenuPanels;

import EmployeeDataBase.ListOfEmployees;
import Exceptions.IllegalInputFormatException;
import Exceptions.DataLengthMismatch;
import GUI.AppPanels.MenuPanel;
import GUI.AppPanels.SubMenuPanels.OperationStatus.FailurePanel;
import GUI.AppPanels.SubMenuPanels.OperationStatus.SuccessPanel;
import GUI.Images.ImageNames;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.concurrent.ExecutionException;


public class LoadFromServerPanel extends ServerPanel {

    /** Constructor */
    public LoadFromServerPanel(JFrame mainFrame, Dimension originalFrameSize) {
        super(mainFrame, originalFrameSize, ImageNames.serverFileBackgroundImage);
    }

    /** Overridden methods */
    @Override
    public JLabel setTitle() {
        return new JLabel("Wprowadź adres URL serwera bazy pracowników:");
    }

    @Override
    public JButton setNextButton() {
        return new JButton("Wczytaj");
    }

    @Override
    public String setProgressBarNote() { return "Wczytywanie"; }

    @Override
    public void loadOrSaveFile() {
        String url = "jdbc:" + fileDirectory.getText() + "?useSSL=false";
        progressWindow.setVisible(true);
        progressBar.setIndeterminate(true);
        SwingWorker<Void, Integer> swingWorker = new SwingWorker<Void, Integer>() {

            @Override
            protected Void doInBackground() throws SQLException, DataLengthMismatch, IllegalInputFormatException {
                Connection connection = DriverManager.getConnection(url,loginField.getText(),passwordField.getText());
                //TODO: napisac lepsza obsluge wyjatkow;
                ListOfEmployees.loadListFromServer(connection, tableField.getText());
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    progressWindow.dispose();
                    drawNextPanel(mainFrame, new SuccessPanel(mainFrame, originalFrameSize, new MenuPanel(mainFrame, originalFrameSize)));
                }
                catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    progressWindow.dispose();
                    drawNextPanel(mainFrame, new FailurePanel(mainFrame, originalFrameSize, new MenuPanel(mainFrame, originalFrameSize)));
                }
            }
        };
        swingWorker.execute();
    }
}
