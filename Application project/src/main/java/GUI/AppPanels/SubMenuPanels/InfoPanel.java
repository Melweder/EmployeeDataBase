package GUI.AppPanels.SubMenuPanels;

import GUI.AppPanels.BackgroundPanel;
import GUI.AppPanels.MenuPanel;
import GUI.Images.ImageNames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoPanel extends BackgroundPanel implements ActionListener {

    /** Constructor */
    public InfoPanel(JFrame mainFrame, Dimension originalFrameSize) {
        super(ImageNames.infoBackgroundImage, originalFrameSize);

        this.mainFrame = mainFrame;
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(25,10,10,10));
        backButton.addActionListener(this);
        credits  = getCredits();
        for (JLabel credit:credits) {
            add(credit);
        }
        add(Box.createVerticalGlue());
        setupLayout();
        add(backButton);
    }

    /** Children methods */
    private int countAdditionalSize(int minComponentSize) {
        if (getScaleRatio() > 1.0)
            return ((int) (minComponentSize*getScaleRatio())) - minComponentSize;
        else
            return 0;
    }

    private JLabel[] getCredits() {
        JLabel[] credits = new JLabel[5];
        for (int k = 0; k < credits.length; k++) {
            credits[k] = new JLabel();
        }
        credits[0].setText("Application version: 1.00");
        credits[1].setText("Last update: 06.2020");
        credits[2].setText("Designer: Łukasz Wach");
        credits[3].setText("");
        credits[4].setText("");
        for (JLabel credit: credits) {
            credit.setHorizontalTextPosition(SwingConstants.RIGHT);
        }
        return credits;
    }

    /** Overridden methods */
    @Override
    public void setupLayout() {
        for (JLabel credit : credits) {
            credit.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(12)));
        }
        backButton.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(12)));
        Dimension buttonSize = new Dimension(200 + 20*countAdditionalSize(10),40 + countAdditionalSize(10));
        backButton.setMaximumSize(buttonSize);
        backButton.setPreferredSize(buttonSize);
    }

    /** Interface methods */
    @Override
    public void actionPerformed(ActionEvent e) {
        drawNextPanel(mainFrame, new MenuPanel(mainFrame, originalFrameSize));
    }

    /** Fields */
    private final JFrame mainFrame;
    private final JLabel[] credits;
    private final JButton backButton = new JButton("Wstecz");
}
