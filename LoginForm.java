package pkg1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {
    public LoginForm() {
        setTitle("Student Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20, 20, 80, 25);
        add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(100, 20, 150, 25);
        add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 60, 80, 25);
        add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(100, 60, 150, 25);
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 100, 80, 25);
        add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login();
                if (login.validateLogin(emailField.getText(), new String(passwordField.getPassword()))) {
                    JOptionPane.showMessageDialog(null, "Login Successful!");
                    dispose();
                    new VotingForm(emailField.getText()).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Email or Password!");
                }
            }
        });
    }

    public static void main(String[] args) {
        new LoginForm().setVisible(true);
    }
}
