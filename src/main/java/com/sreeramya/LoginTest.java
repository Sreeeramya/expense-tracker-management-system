package com.sreeramya;

public class LoginTest {

    public static void main(String[] args) {

        UserDAO dao =
                new UserDAO();

        boolean success =
                dao.login("sreeramya","9999");

        System.out.println(success);
    }
}