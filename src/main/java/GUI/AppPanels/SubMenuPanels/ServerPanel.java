package GUI.AppPanels.SubMenuPanels;

import GUI.AppPanels.BackgroundPanel;
import GUI.AppPanels.MenuPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class ServerPanel extends BackgroundPanel implements ActionListener {

    /** Constructor */
    public ServerPanel(JFrame mainFrame, Dimension originalFrameSize, String backgroundImage) {
        super(backgroundImage, originalFrameSize);

        this.mainFrame = mainFrame;
        this.title = setTitle();
        this.nextButton = setNextButton();
        this.fileDirectory = setFileDirectory();
        this.loginField = setLoginField();
        this.passwordField = setPasswordField();
        this.tableField = setTableField();
        gridBagLayout = new GridBagLayout();
        progressWindow = createProgressWindow();
        setLayout(gridBagLayout);
        nextButton.addActionListener(this);
        backButton.addActionListener(this);
        message.setForeground(Color.red);
        setupLayout();
    }
    /** Methods */
    public JDialog createProgressWindow(){
        JDialog progressWindow = new JDialog(mainFrame, "Wczytywanie bazy danych");
        JPanel progressPanel = new JPanel();
        progressBar.setStringPainted(true);
        progressBar.setString(setProgressBarNote());
        progressPanel.add(progressBar);
        progressBar.setPreferredSize(preferredButtonSize);
        progressWindow.add(progressPanel);
        progressWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        progressWindow.setSize(new Dimension(progressPanel.getPreferredSize().width+16, progressPanel.getPreferredSize().height+39));
        progressWindow.setLocationRelativeTo(mainFrame);
        //progressWindow.setModalityType(Dialog.ModalityType.TOOLKIT_MODAL);
        progressWindow.setResizable(false);
        return progressWindow;
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

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.ipadx = 5;
        loginLabel.setFont(new Font("Dialog", Font.BOLD, 14 + countAdditionalSize(backButton, gridBagLayout)));
        add(loginLabel, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.ipadx = 20 * countAdditionalSize(backButton, gridBagLayout);
        loginField.setFont(new Font("Dialog", Font.PLAIN, 12 + countAdditionalSize(backButton, gridBagLayout)));
        add(loginField, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.ipadx =  5;
        passwordLabel.setFont(new Font("Dialog", Font.BOLD, 14 + countAdditionalSize(backButton, gridBagLayout)));
        add(passwordLabel, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.ipadx = 20 * countAdditionalSize(backButton, gridBagLayout);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 12 + countAdditionalSize(backButton, gridBagLayout)));
        add(passwordField, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        constraints.ipadx = 5;
        tableLabel.setFont(new Font("Dialog", Font.BOLD, 14 + countAdditionalSize(backButton, gridBagLayout)));
        add(tableLabel, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        constraints.ipadx = 20 * countAdditionalSize(backButton, gridBagLayout);
        tableField.setFont(new Font("Dialog", Font.PLAIN, 12 + countAdditionalSize(backButton, gridBagLayout)));
        add(tableField, constraints);

        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridx = 0;
        constraints.gridy = 6;
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
    public abstract String setProgressBarNote();
    public JTextField setLoginField() { return new JTextField("waIklMsqqJ"); }
    public JTextField setPasswordField() { return new JTextField("xuY3zMezTN"); }
    public JTextField setTableField() { return new JTextField("EmployeeDataBase"); }
    public JTextField setFileDirectory() {
        return new JTextField("mysql://remotemysql.com:3306/waIklMsqqJ");
    }

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
    protected final JDialog progressWindow;
    protected final JProgressBar progressBar = new JProgressBar(0,100);;
    protected final JLabel title;
    protected final JLabel message = new JLabel(" ");
    protected final JTextField fileDirectory;
    protected final JButton nextButton;
    protected final JButton backButton = new JButton("Wstecz");
    protected final JLabel loginLabel = new JLabel("Login:");
    protected final JLabel passwordLabel = new JLabel("Hasło:");
    protected final JLabel tableLabel = new JLabel("Baza danych:");
    protected final JTextField loginField;
    protected final JTextField passwordField;
    protected final JTextField tableField;
}