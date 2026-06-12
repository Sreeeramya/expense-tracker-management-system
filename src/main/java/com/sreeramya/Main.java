package com.sreeramya;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ExpenseDAO dao = new ExpenseDAO();

        while (true) {

            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Delete Expense");
            System.out.println("4. Total Spending");
            System.out.println("5. Category Wise Spending");
            System.out.println("6. Monthly Spending");
            System.out.println("7. Exit");
            System.out.print("Enter Choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:

                    System.out.print("Enter Amount: ");
                    double amount = sc.nextDouble();

                    System.out.print("Enter Category: ");
                    String category = sc.next();

                    sc.nextLine();

                    System.out.print("Enter Description: ");
                    String description = sc.nextLine();

                    System.out.print("Enter Date (YYYY-MM-DD): ");
                    String expenseDate = sc.nextLine();

                    Expense expense =
                            new Expense(
                                    amount,
                                    category,
                                    description,
                                    expenseDate,
                                    1
                            );

                    dao.addExpense(expense);

                    break;

                case 2:

                    dao.viewExpenses();

                    break;

                case 3:

                    System.out.print("Enter Expense ID to Delete: ");
                    int id = sc.nextInt();

                    dao.deleteExpense(id,1);

                    break;
                case 4:
                    dao.totalSpending();
                    break;

                case 5:
                    dao.categoryWiseSpending();
                    break;

                case 6:
                    dao.monthlySpending();
                    break;

                case 7:
                    System.out.println("Goodbye!");
                    System.exit(0);

                default:

                    System.out.println("Invalid Choice");
            }
        }
    }
}