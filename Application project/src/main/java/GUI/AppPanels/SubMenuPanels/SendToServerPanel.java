package GUI.AppPanels.SubMenuPanels;

import EmployeeDataBase.ListOfEmployees;
import Exceptions.DataLengthMismatch;
import Exceptions.IllegalInputFormatException;
import GUI.AppPanels.MenuPanel;
import GUI.AppPanels.SubMenuPanels.OperationStatus.FailurePanel;
import GUI.AppPanels.SubMenuPanels.OperationStatus.SuccessPanel;
import GUI.Images.ImageNames;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class SendToServerPanel extends ServerPanel {

    /** Constructor */
    public SendToServerPanel(JFrame mainFrame, Dimension originalFrameSize) {
        super(mainFrame, originalFrameSize, ImageNames.serverFileBackgroundImage);
    }

    /** Overridden methods */
    @Override
    public JLabel setTitle() {
        return new JLabel("Wprowadź adres URL serwera bazy pracowników:");
    }

    @Override
    public JButton setNextButton() {
        return new JButton("Prześlij");
    }

    @Override
    public String setProgressBarNote() { return "Zapisywanie"; }

    @Override
    public void loadOrSaveFile() {
        String url = "jdbc:" + fileDirectory.getText() + "?useSSL=false";
        progressWindow.setVisible(true);
        progressBar.setIndeterminate(true);
        SwingWorker<Void, Integer> swingWorker = new SwingWorker<Void, Integer>() {

            @Override
            protected Void doInBackground() throws SQLException {
                Connection connection = DriverManager.getConnection(url,loginField.getText(),passwordField.getText());
                //TODO: napisac lepsza obsluge wyjatkow;
                ListOfEmployees.sendListToServer(connection, tableField.getText());
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
