package GUI.AppPanels.SubMenuPanels;

import GUI.AppPanels.BackgroundPanel;
import GUI.AppPanels.MenuPanel;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public abstract class LoadSavePanel extends BackgroundPanel implements ActionListener {

    /** Constructor */
    public LoadSavePanel(JFrame mainFrame, Dimension originalFrameSize, String backgroundImage) {
        super(backgroundImage, originalFrameSize);

        this.mainFrame = mainFrame;
        this.title = setTitle();
        this.nextButton = setNextButton();
        this.fileDirectory = setFileDirectory();
        gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        nextButton.addActionListener(this);
        backButton.addActionListener(this);
        message.setForeground(Color.red);
        setupLayout();
    }

    /** Overridden methods */
    @Override
    public void setupLayout() {
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.ipady = 5 + countAdditionalSize(backButton, gridBagLayout);
        constraints.ipadx = 10 * countAdditionalSize(backButton, gridBagLayout);
        title.setFont(new Font("Dialog", Font.BOLD, 14 + countAdditionalSize(backButton, gridBagLayout)));
        title.setPreferredSize(new Dimension(500, 35));
        add(title, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.ipadx = 20 * countAdditionalSize(backButton, gridBagLayout);
        fileDirectory.setFont(new Font("Dialog", Font.PLAIN, 12 + countAdditionalSize(backButton, gridBagLayout)));
        fileDirectory.setPreferredSize(new Dimension(400, 30));
        add(fileDirectory, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.ipadx = 10 * countAdditionalSize(backButton, gridBagLayout);
        nextButton.setFont(new Font("Dialog", Font.BOLD, 12 + countAdditionalSize(nextButton, gridBagLayout)));
        nextButton.setPreferredSize(new Dimension(80, 30));
        add(nextButton, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 3;
        message.setFont(new Font("Dialog", Font.PLAIN, 10 + countAdditionalSize(backButton, gridBagLayout)));
        add(message, constraints);

        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 3;
        constraints.ipady = countAdditionalSize(backButton, gridBagLayout);
        constraints.insets = new Insets(15 + countAdditionalSize(backButton, gridBagLayout), 0, 0, 0);
        backButton.setFont(new Font("Dialog", Font.BOLD, 12 + countAdditionalSize(backButton, gridBagLayout)));
        backButton.setPreferredSize(new Dimension((preferredButtonSize.width - 100), preferredButtonSize.height));
        add(backButton, constraints);
    }

    /** Abstract parent methods */
    public abstract void loadOrSaveFile();
    public abstract JLabel setTitle();
    public abstract JButton setNextButton();
    public abstract JTextField setFileDirectory();

    /** Interface methods */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextButton) {
            loadOrSaveFile();
        } else if (e.getSource() == backButton) {
            drawNextPanel(mainFrame, new MenuPanel(mainFrame, originalFrameSize));
        }
    }

    /** Fields */
    protected final JFrame mainFrame;
    protected final GridBagLayout gridBagLayout;
    protected final JLabel title;
    protected final JLabel message = new JLabel(" ");
    protected final JTextField fileDirectory;
    protected final JButton nextButton;
    protected final JButton backButton = new JButton("Wstecz");
}
