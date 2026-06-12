package com.sreeramya;

import javax.swing.*;

public class RegisterGUI extends JFrame {

    public RegisterGUI() {

        setTitle("Register");

        setSize(400, 300);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(null);

        JLabel userLabel =
                new JLabel("Username:");

        userLabel.setBounds(30, 30, 100, 25);

        JTextField userField =
                new JTextField();

        userField.setBounds(150, 30, 180, 25);

        JLabel passLabel =
                new JLabel("Password:");

        passLabel.setBounds(30, 80, 100, 25);

        JPasswordField passField =
                new JPasswordField();

        passField.setBounds(150, 80, 180, 25);

        JButton registerButton =
                new JButton("Register");

        registerButton.setBounds(
                150,
                140,
                120,
                30
        );

        add(userLabel);
        add(userField);

        add(passLabel);
        add(passField);

        add(registerButton);

        registerButton.addActionListener(e -> {

            String username =
                    userField.getText();

            String password =
                    new String(
                            passField.getPassword()
                    );
            if(username.isBlank()
                    || password.isBlank()) {

                JOptionPane.showMessageDialog(
                        null,
                        "Fields cannot be empty"
                );

                return;
            }
            UserDAO dao =
                    new UserDAO();

            if(dao.userExists(username)) {

                JOptionPane.showMessageDialog(
                        null,
                        "Username already exists"
                );

                return;
            }

            User user =
                    new User(
                            username,
                            password
                    );

            dao.register(user);

            JOptionPane.showMessageDialog(
                    null,
                    "Registration Successful"
            );

            dispose();
        });

        setVisible(true);
    }
}
