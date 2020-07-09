package GUI.AppPanels.SubMenuPanels.SubDataBasePanels;


import Enums.EmployeeDataTypes;
import GUI.AppPanels.BackgroundPanel;
import GUI.AppPanels.SubMenuPanels.DataBasePanel;
import GUI.Images.ImageNames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddEmployeeFirstPanel extends BackgroundPanel implements ActionListener {

    /** Constructor */
    public AddEmployeeFirstPanel(JFrame mainFrame, Dimension originalFrameSize){
        super(ImageNames.addEmployeeFirstBackgroundImage,originalFrameSize);

        this.mainFrame = mainFrame;
        gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        nextButton.addActionListener(this);
        backButton.addActionListener(this);
        setCheckBox();
        setupLayout();
    }

    /** Children methods */
    private void setCheckBox(){

        for (int k = 0; k < dataCheckBox.length; k++){
            dataCheckBox[k] = new JCheckBox(EmployeeDataTypes.findByID(k).getDataName());
            dataCheckBox[k].setOpaque(false);
            if (EmployeeDataTypes.findByID(k).getIsRequired()) {
                dataCheckBox[k].setSelected(true);
                dataCheckBox[k].setEnabled(false);
            }
        }
    }

    /** Overridden methods */
    @Override
    public void setupLayout() {

        setBorder(BorderFactory.createEmptyBorder(0,frameSize.width/2,0,0));
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets = new Insets(0 ,0,10 + countAdditionalSize(backButton, gridBagLayout),0);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.ipady = 5 + countAdditionalSize(backButton, gridBagLayout);
        constraints.ipadx = 10 * countAdditionalSize(backButton, gridBagLayout);
        title.setFont(new Font("Dialog",Font.BOLD,14 + countAdditionalSize(backButton, gridBagLayout)));
        add(title, constraints);

        constraints.insets = new Insets(0 ,0,countAdditionalSize(backButton, gridBagLayout),0);
        for (int k = 0; k < dataCheckBox.length; k++){
            constraints.fill = GridBagConstraints.BOTH;
            if (k%2 == 0) {
                constraints.gridx = 0;
                constraints.gridy += 1;
            }
            else {
                constraints.gridx = 1;
            }
            constraints.gridwidth = 1;
            dataCheckBox[k].setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(backButton, gridBagLayout)));
            add(dataCheckBox[k], constraints);
        }

        constraints.insets = new Insets(20 + countAdditionalSize(nextButton, gridBagLayout) ,0,0,0);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy += 1;
        constraints.gridwidth = 2;
        constraints.ipady = countAdditionalSize(nextButton, gridBagLayout);
        constraints.ipadx = 20 * countAdditionalSize(nextButton, gridBagLayout);
        nextButton.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(nextButton, gridBagLayout)));
        nextButton.setPreferredSize(new Dimension(preferredButtonSize.width-100, preferredButtonSize.height));
        add(nextButton, constraints);

        constraints.insets = new Insets(10 + countAdditionalSize(backButton, gridBagLayout) ,0,0,0);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy += 1;
        constraints.gridwidth = 2;
        backButton.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(backButton, gridBagLayout)));
        backButton.setPreferredSize(new Dimension(preferredButtonSize.width-100, preferredButtonSize.height));
        add(backButton, constraints);

    }

    /** Interface methods */
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == nextButton) {
            ArrayList<EmployeeDataTypes> dataTypesList = new ArrayList<>();
            for (int k = 0; k < dataCheckBox.length; k++){
                if (dataCheckBox[k].isSelected()) {
                    dataTypesList.add(EmployeeDataTypes.findByID(k));
                }
            }
            drawNextPanel(mainFrame, new AddEmployeeSecondPanel(mainFrame, originalFrameSize, dataTypesList.toArray(new EmployeeDataTypes[0])));
        }
        else if (e.getSource() == backButton) {
            drawNextPanel(mainFrame, new DataBasePanel(mainFrame, originalFrameSize));
        }
    }

    /** Fields */
    private final JFrame mainFrame;
    private final GridBagLayout gridBagLayout;
    private final JLabel title = new JLabel("Wybierz dane do wprowadzenia:");
    private final JCheckBox[] dataCheckBox = new JCheckBox[EmployeeDataTypes.values().length];
    private final JButton nextButton = new JButton("Dalej");
    private final JButton backButton = new JButton("Wstecz");
}
