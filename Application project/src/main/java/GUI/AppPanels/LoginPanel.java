package GUI.AppPanels;

import GUI.Images.ImageContainer;
import GUI.Images.ImageNames;
import InputWorkers.InputDataValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class LoginPanel extends BackgroundPanel implements ActionListener {

    /** Constructor */
    public LoginPanel(JFrame mainFrame, Dimension originalFrameSize){
        super(ImageNames.loggingBackgroundImage, originalFrameSize);

        this.mainFrame = mainFrame;
        gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        setLabels();
        nameField.setText("Login");
        passField.setText("Password");
        loginButton.addActionListener(this);
        setupLayout();
    }

    /** Methods */
    @Override
    public void setupLayout() {
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(loginLabel, constraints);

        constraints.insets = new Insets(10 + countAdditionalSize(userName, gridBagLayout),0,0,0);
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.ipady = countAdditionalSize(userName, gridBagLayout);
        userName.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(loginButton, gridBagLayout)));
        add(userName, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 1;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.ipady = countAdditionalSize(nameField, gridBagLayout);
        nameField.setFont(new Font("Dialog",Font.PLAIN,12 + countAdditionalSize(loginButton, gridBagLayout)));
        add(nameField, constraints);

        constraints.insets = new Insets(5 + countAdditionalSize(userPassword, gridBagLayout),0,0,0);
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.ipady = countAdditionalSize(userPassword, gridBagLayout);
        userPassword.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(loginButton, gridBagLayout)));
        add(userPassword, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 1;
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.ipady = countAdditionalSize(passField, gridBagLayout);
        passField.setFont(new Font("Dialog",Font.PLAIN,12 + countAdditionalSize(loginButton, gridBagLayout)));
        add(passField,constraints);

        constraints.insets = new Insets(5 + countAdditionalSize(loginButton, gridBagLayout),0,0,0);
        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.ipady = countAdditionalSize(loginButton, gridBagLayout);
        loginButton.setFont(new Font("Dialog",Font.BOLD,12 + countAdditionalSize(loginButton, gridBagLayout)));
        loginButton.setPreferredSize(preferredButtonSize);
        add(loginButton,constraints);

        constraints.insets = new Insets(5 + countAdditionalSize(message, gridBagLayout),0,0,0);
        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.ipady = countAdditionalSize(message, gridBagLayout);
        message.setFont(new Font("Dialog",Font.PLAIN,10 + countAdditionalSize(loginButton, gridBagLayout)));
        add(message, constraints);
    }

    private void setLabels() {

        loginLabel = new JLabel(new ImageIcon (ImageContainer.getRescaledImage(ImageNames.loggingImage, 0.5)));
        userName = new JLabel("Użytkownik: ");
        userName.setHorizontalAlignment(SwingConstants.RIGHT);
        userPassword = new JLabel ("Hasło: ");
        userPassword.setHorizontalAlignment(SwingConstants.RIGHT);
        message = new JLabel(" ");
        message.setForeground(Color.red);
    }

    /** Interface methods */
    @Override
    public void refreshLayout(Dimension frameSize, Dimension oldFrameSize) {
        if ((oldFrameSize.height != frameSize.height) || (oldFrameSize.width != frameSize.width)) {
            try{
                loginLabel.setIcon(new ImageIcon (ImageContainer.getRescaledImage(ImageNames.loggingImage, 0.5*getScaleRatio())));
            }
            catch(Exception e) {
                System.out.println(e.getMessage());
            }
            setupLayout();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String name = nameField.getText();
        String password = String.valueOf(passField.getPassword());
        if(InputDataValidator.validatePassword(password) && InputDataValidator.validateUserName(name)) {
            drawNextPanel(mainFrame,new MenuPanel(mainFrame, originalFrameSize));
        }
        else {
            message.setText("*Nieprawidłowa nazwa użytkowanika lub hasło!");
        }
    }

    /** Fields */
    private final JFrame mainFrame;
    private final GridBagLayout gridBagLayout;
    private JLabel loginLabel;
    private JLabel userName;
    private JLabel userPassword;
    private JLabel message;
    private final JTextField nameField = new JTextField();
    private final JPasswordField passField = new JPasswordField();
    private final JButton loginButton= new JButton("Zaloguj");

}
