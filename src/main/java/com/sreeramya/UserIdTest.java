package com.sreeramya;

public class UserIdTest {

    public static void main(String[] args) {

        UserDAO dao = new UserDAO();

        System.out.println(
                dao.getUserId("sreeramya")
        );
    }
}
