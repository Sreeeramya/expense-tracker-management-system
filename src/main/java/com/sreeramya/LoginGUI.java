package com.sreeramya;

import javax.swing.*;

public class LoginGUI extends JFrame {

    public LoginGUI() {

        setTitle("Login");

        setSize(400, 250);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(null);

        JLabel userLabel =
                new JLabel("Username:");

        userLabel.setBounds(30, 30, 100, 25);

        JTextField userField =
                new JTextField();

        userField.setBounds(140, 30, 180, 25);

        JLabel passLabel =
                new JLabel("Password:");

        passLabel.setBounds(30, 80, 100, 25);

        JPasswordField passField =
                new JPasswordField();

        passField.setBounds(140, 80, 180, 25);

        JButton loginButton =
                new JButton("Login");
        JButton registerButton =
                new JButton("Register");
        JButton logoutButton =
                new JButton("Logout");

        loginButton.setBounds(80, 130, 100, 30);

        registerButton.setBounds(
                200,
                130,
                120,
                30
        );

        add(userLabel);
        add(userField);

        add(passLabel);
        add(passField);

        add(loginButton);
        add(registerButton);

        setVisible(true);
        loginButton.addActionListener(e -> {

            String username =
                    userField.getText();

            String password =
                    new String(
                            passField.getPassword()
                    );

            UserDAO dao =
                    new UserDAO();

            boolean success =
                    dao.login(
                            username,
                            password
                    );

            if(success){

                JOptionPane.showMessageDialog(
                        null,
                        "Login Successful"
                );

                int userId =
                        dao.getUserId(username);

                dispose();

                new ExpenseTrackerGUI(
                        userId,
                        username
                );

            }else{

                JOptionPane.showMessageDialog(
                        null,
                        "Invalid Credentials"
                );
            }
        });
        registerButton.addActionListener(e -> {

            new RegisterGUI();
        });
    }

    public static void main(String[] args) {

        new LoginGUI();
    }
}
