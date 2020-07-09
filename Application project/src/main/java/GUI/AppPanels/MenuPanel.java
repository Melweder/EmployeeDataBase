package GUI.AppPanels;

import EmployeeDataBase.ListOfEmployees;
import GUI.AppPanels.SubMenuPanels.*;
import GUI.Images.ImageNames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends BackgroundPanel implements ActionListener {

    /** Constructor */
    public MenuPanel(JFrame mainFrame, Dimension originalFrameSize){
        super(ImageNames.menuBackgroundImage, originalFrameSize);
        this.mainFrame = mainFrame;
        gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        firstButton.addActionListener(this);
        secondButton.addActionListener(this);
        thirdButton.addActionListener(this);
        fourthButton.addActionListener(this);
        fifthButton.addActionListener(this);
        infoButton.addActionListener(this);
        logoutButton.addActionListener(this);
        setupLayout();
    }

    /** Overridden methods **/
    @Override
    public void setupLayout() {
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.ipady = countAdditionalSize(firstButton, gridBagLayout);
        constraints.ipadx = 20 * countAdditionalSize(firstButton, gridBagLayout);
        firstButton.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(firstButton, gridBagLayout)));
        firstButton.setPreferredSize(preferredButtonSize);
        add(firstButton, constraints);

        constraints.insets = new Insets(10 + countAdditionalSize(secondButton, gridBagLayout) ,0,0,0);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        secondButton.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(secondButton, gridBagLayout)));
        secondButton.setPreferredSize(preferredButtonSize);
        add(secondButton, constraints);

        constraints.insets = new Insets(10 + countAdditionalSize(thirdButton, gridBagLayout),0,0,0);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        thirdButton.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(thirdButton, gridBagLayout)));
        thirdButton.setPreferredSize(preferredButtonSize);
        add(thirdButton, constraints);

        constraints.insets = new Insets(10 + countAdditionalSize(fourthButton, gridBagLayout),0,0,0);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        fourthButton.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(fourthButton, gridBagLayout)));
        fourthButton.setPreferredSize(preferredButtonSize);
        add(fourthButton, constraints);

        constraints.insets = new Insets(10 + countAdditionalSize(fifthButton, gridBagLayout),0,0,0);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        fifthButton.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(fifthButton, gridBagLayout)));
        fifthButton.setPreferredSize(preferredButtonSize);
        add(fifthButton, constraints);

        constraints.insets = new Insets(10 + countAdditionalSize(firstButton, gridBagLayout),0,0,0);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        infoButton.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(infoButton, gridBagLayout)));
        infoButton.setPreferredSize(preferredButtonSize);
        add(infoButton, constraints);


        constraints.insets = new Insets(10 + countAdditionalSize(firstButton, gridBagLayout),0,0,0);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        logoutButton.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(firstButton, gridBagLayout)));
        logoutButton.setPreferredSize(preferredButtonSize);
        add(logoutButton, constraints);
    }

    /** Interface methods */
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == firstButton) {
            drawNextPanel(mainFrame,new DataBasePanel(mainFrame, originalFrameSize));
        }
        else if (e.getSource() == secondButton){
            drawNextPanel(mainFrame,new LoadFromFilePanel(mainFrame, originalFrameSize));
        }
        else if (e.getSource() == thirdButton){
            drawNextPanel(mainFrame,new SaveToFilePanel(mainFrame, originalFrameSize));
        }
        else if (e.getSource() == fourthButton){
            drawNextPanel(mainFrame,new LoadFromServerPanel(mainFrame, originalFrameSize));
        }
        else if (e.getSource() == fifthButton){
            drawNextPanel(mainFrame,new SendToServerPanel(mainFrame, originalFrameSize));
        }
        else if (e.getSource() == infoButton){
            drawNextPanel(mainFrame,new InfoPanel(mainFrame, originalFrameSize));
        }
        else if (e.getSource() == logoutButton) {
            drawNextPanel(mainFrame, new LoginPanel(mainFrame, originalFrameSize));
        }
    }

    /** Fields */
    private final JFrame mainFrame;
    private final GridBagLayout gridBagLayout;
    private final JButton firstButton = new JButton("Przeglądaj bazę pracowników");
    private final JButton secondButton = new JButton("Wczytaj bazę pracowników z pliku");
    private final JButton thirdButton = new JButton("Zapisz bazę pracowników do pliku");
    private final JButton fourthButton = new JButton("Wczytaj bazę pracowników z serwera");
    private final JButton fifthButton = new JButton("Prześlij bazę pracowników na serwer");
    private final JButton infoButton = new JButton("Informacje o programie");
    private final JButton logoutButton = new JButton("Wyloguj");
}
