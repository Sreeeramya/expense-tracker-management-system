package com.sreeramya;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ExpenseDAO {
    public void categoryWiseSpending() {

        String sql =
                "SELECT category, SUM(amount) AS total " +
                        "FROM expenses " +
                        "GROUP BY category";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            var rs = ps.executeQuery();

            System.out.println("\nCategory Wise Spending");

            while (rs.next()) {

                String category =
                        rs.getString("category");

                double total =
                        rs.getDouble("total");

                System.out.println(
                        category + " : " + total
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void totalSpending() {

        String sql = "SELECT SUM(amount) AS total FROM expenses";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            var rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println(
                        "Total Spending = " +
                                rs.getDouble("total")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteExpense(int id, int userId) {

        String sql =
                "DELETE FROM expenses " +
                        "WHERE id = ? AND user_id = ?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);
            ps.setInt(2, userId);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Expense Deleted!");
            else
                System.out.println(
                        "Expense Not Found or Access Denied!"
                );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void viewExpenses() {

        String sql = "SELECT * FROM expenses";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            var rs = ps.executeQuery();

            while (rs.next()) {

                System.out.println(
                        rs.getInt("id") + " "
                                + rs.getDouble("amount") + " "
                                + rs.getString("category") + " "
                                + rs.getString("description") + " "
                                + rs.getDate("expense_date")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addExpense(Expense expense) {

        String sql =
                "INSERT INTO expenses(amount, category, description, expense_date, user_id) VALUES(?, ?, ?, ?, ?)";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setDouble(1, expense.getAmount());
            ps.setString(2, expense.getCategory());
            ps.setString(3, expense.getDescription());
            ps.setString(4, expense.getExpenseDate());
            ps.setInt(5, expense.getUserId());

            ps.executeUpdate();

            System.out.println("Expense Added Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void monthlySpending() {

        String sql =
                "SELECT MONTH(expense_date) AS month, " +
                        "SUM(amount) AS total " +
                        "FROM expenses " +
                        "WHERE expense_date IS NOT NULL " +
                        "GROUP BY MONTH(expense_date)";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            var rs = ps.executeQuery();

            System.out.println("\nMonthly Spending");

            while (rs.next()) {

                System.out.println(
                        "Month " +
                                rs.getInt("month")
                                + " : "
                                + rs.getDouble("total")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
