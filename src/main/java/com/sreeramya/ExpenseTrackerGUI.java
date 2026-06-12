package com.sreeramya;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ExpenseTrackerGUI extends JFrame {

    private int userId;
    private String username;

    public ExpenseTrackerGUI(int userId,
                             String username){
        this.userId = userId;
        this.username = username;

        setTitle("Expense Tracker");

        setSize(550, 500);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(null);
        JLabel searchLabel =
                new JLabel("Search:");

        searchLabel.setBounds(
                30,
                210,
                100,
                25
        );

        JTextField searchField =
                new JTextField();

        searchField.setBounds(
                110,
                210,
                120,
                25
        );

        JButton searchButton =
                new JButton("Search");

        searchButton.setBounds(
                250,
                210,
                100,
                25
        );

        JButton showAllButton =
                new JButton("Show All");

        showAllButton.setBounds(
                370,
                210,
                120,
                25
        );
        JLabel welcomeLabel =
                new JLabel(
                        "Welcome, " + username
                );




        welcomeLabel.setBounds(
                30,
                5,
                250,
                25
        );

        add(welcomeLabel);

        JLabel amountLabel =
                new JLabel("Amount:");

        amountLabel.setBounds(30, 30, 100, 25);

        JTextField amountField =
                new JTextField();

        amountField.setBounds(150, 30, 200, 25);

        JLabel categoryLabel =
                new JLabel("Category:");

        categoryLabel.setBounds(30, 70, 100, 25);

        JTextField categoryField =
                new JTextField();

        categoryField.setBounds(150, 70, 200, 25);

        JLabel descriptionLabel =
                new JLabel("Description:");

        descriptionLabel.setBounds(30, 110, 100, 25);

        JTextField descriptionField =
                new JTextField();

        descriptionField.setBounds(150, 110, 200, 25);

        JLabel dateLabel =
                new JLabel("Date:");

        dateLabel.setBounds(30, 150, 100, 25);

        JTextField dateField =
                new JTextField();

        dateField.setBounds(150, 150, 200, 25);

        add(amountLabel);
        add(amountField);

        add(categoryLabel);
        add(categoryField);

        add(descriptionLabel);
        add(descriptionField);

        add(dateLabel);
        add(dateField);
        add(searchLabel);
        add(searchField);
        add(searchButton);
        add(showAllButton);
        JButton addButton =
                new JButton("Add Expense");

        addButton.setBounds(150, 250, 150, 30);
        JButton deleteButton =
                new JButton("Delete Selected");
        JButton logoutButton =
                new JButton("Logout");


        deleteButton.setBounds(
                320,
                250,
                170,
                30
        );
        logoutButton.setBounds(
                420,
                20,
                100,
                30
        );

        add(logoutButton);


        add(deleteButton);


        add(addButton);
        String[] columns = {
                "ID",
                "Amount",
                "Category",
                "Description",
                "Date"
        };

        DefaultTableModel model =
                new DefaultTableModel(columns, 0);

        JTable table =
                new JTable(model);

        JScrollPane scrollPane =
                new JScrollPane(table);

        scrollPane.setBounds(
                20,
                300,
                500,
                140
        );

        add(scrollPane);
        loadExpenses(model);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                double amount =
                        Double.parseDouble(
                                amountField.getText()
                        );

                String category =
                        categoryField.getText();

                String description =
                        descriptionField.getText();

                String date =
                        dateField.getText();

                Expense expense =
                        new Expense(
                                amount,
                                category,
                                description,
                                date,
                                userId
                        );

                ExpenseDAO dao =
                        new ExpenseDAO();

                dao.addExpense(expense);

                loadExpenses(model);



                amountField.setText("");
                categoryField.setText("");
                descriptionField.setText("");
                dateField.setText("");

                JOptionPane.showMessageDialog(
                        null,
                        "Expense Added Successfully!"
                );
            }
        });
        logoutButton.addActionListener(e -> {

            dispose();

            new LoginGUI();
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow =
                        table.getSelectedRow();

                if (selectedRow == -1) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Please select a row."
                    );

                    return;
                }

                int id =
                        (Integer) model.getValueAt(
                                selectedRow,
                                0
                        );

                ExpenseDAO dao =
                        new ExpenseDAO();
                int choice =
                        JOptionPane.showConfirmDialog(
                                null,
                                "Delete this expense?",
                                "Confirm Delete",
                                JOptionPane.YES_NO_OPTION
                        );

                if(choice != JOptionPane.YES_OPTION) {
                    return;
                }
                dao.deleteExpense(
                        id,
                        userId
                );

                loadExpenses(model);




            }
        });
        searchButton.addActionListener(e -> {

            String category =
                    searchField.getText();

            searchExpenses(
                    model,
                    category
            );
        });
        showAllButton.addActionListener(e -> {

            loadExpenses(model);

            searchField.setText("");
        });
        getContentPane().addMouseListener(
                new java.awt.event.MouseAdapter() {
                    @Override
                    public void mousePressed(
                            java.awt.event.MouseEvent e
                    ) {
                        table.clearSelection();
                    }
                }
        );
        setVisible(true);
    }
    private void loadExpenses(DefaultTableModel model) {

        model.setRowCount(0);

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(
                                "SELECT * FROM expenses WHERE user_id=?"
                        )
        ) {

            ps.setInt(1, userId);

            var rs = ps.executeQuery();

            while (rs.next()) {

                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getDouble("amount"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getDate("expense_date")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void searchExpenses(
            DefaultTableModel model,
            String category
    ) {

        model.setRowCount(0);

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(
                                "SELECT * FROM expenses " +
                                        "WHERE category=? " +
                                        "AND user_id=?"
                        )
        ) {

            ps.setString(1, category);
            ps.setInt(2, userId);

            var rs = ps.executeQuery();

            while (rs.next()) {

                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getDouble("amount"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getDate("expense_date")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private double getTotalSpending() {

        double total = 0;

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(
                                "SELECT SUM(amount) AS total " +
                                        "FROM expenses " +
                                        "WHERE user_id=?"
                        )
        ) {

            ps.setInt(1, userId);

            var rs = ps.executeQuery();

            if (rs.next()) {

                total =
                        rs.getDouble("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }
    private double getCategorySpending(
            String category
    ) {

        double total = 0;

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(
                                "SELECT SUM(amount) AS total " +
                                        "FROM expenses " +
                                        "WHERE user_id=? " +
                                        "AND category=?"
                        )
        ) {

            ps.setInt(1, userId);
            ps.setString(2, category);

            var rs = ps.executeQuery();

            if (rs.next()) {

                total =
                        rs.getDouble("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }
    public static void main(String[] args) {

        new ExpenseTrackerGUI(
                1,
                "Demo User"
        );
    }
}
