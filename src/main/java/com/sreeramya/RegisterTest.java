package com.sreeramya;

public class RegisterTest {

    public static void main(String[] args) {

        User user =
                new User(
                        "sreeramya",
                        "1234"
                );

        UserDAO dao =
                new UserDAO();

        dao.register(user);
    }
}