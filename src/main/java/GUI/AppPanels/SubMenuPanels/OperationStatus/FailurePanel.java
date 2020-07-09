package GUI.AppPanels.SubMenuPanels.OperationStatus;

import GUI.AppPanels.BackgroundPanel;
import GUI.Images.ImageContainer;
import GUI.Images.ImageNames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FailurePanel extends BackgroundPanel implements ActionListener {

    public FailurePanel(JFrame mainFrame, Dimension originalFrameSize, JPanel nextPanel){
        super(ImageNames.failureBackgroundImage,originalFrameSize);
        this.mainFrame = mainFrame;
        this.nextPanel = nextPanel;
        gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        confirmButton.addActionListener(this);
        setupLayout();
    }

    @Override
    public void setupLayout() {

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(icon, constraints);

        constraints.insets = new Insets(0 ,10 + countAdditionalSize(confirmButton, gridBagLayout),0,0);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        message.setFont(new Font("Dialog",Font.BOLD,18 + countAdditionalSize(confirmButton, gridBagLayout)));
        add(message, constraints);

        constraints.insets = new Insets(10 + countAdditionalSize(confirmButton, gridBagLayout) ,0,0,0);
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.ipady = countAdditionalSize(confirmButton, gridBagLayout);
        constraints.ipadx = 20 * countAdditionalSize(confirmButton, gridBagLayout);
        confirmButton.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(confirmButton, gridBagLayout)));
        confirmButton.setPreferredSize(new Dimension(preferredButtonSize.width-100, preferredButtonSize.height));
        add(confirmButton, constraints);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
            drawNextPanel(mainFrame, nextPanel);
    }

    /** Interface methods */
    @Override
    public void refreshLayout(Dimension frameSize, Dimension oldFrameSize) {
        if ((oldFrameSize.height != frameSize.height) || (oldFrameSize.width != frameSize.width)) {
            try{
                icon.setIcon(new ImageIcon (ImageContainer.getRescaledImage(ImageNames.failureIcon, 0.75 * getScaleRatio())));
            }
            catch(Exception e) {
                System.out.println(e.getMessage());
            }
            setupLayout();
        }
    }

    private final JFrame mainFrame;
    private final GridBagLayout gridBagLayout;
    private final JPanel nextPanel;
    private final JLabel icon = new JLabel(new ImageIcon (ImageContainer.getRescaledImage(ImageNames.failureIcon, 0.75)));
    private final JLabel message = new JLabel("Operacja nie powiodła się!");
    private final JButton confirmButton = new JButton("Ok");
}
