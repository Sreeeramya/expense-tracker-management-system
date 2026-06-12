package com.sreeramya;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDAO {
    public boolean login(String username,
                         String password) {

        String sql =
                "SELECT * FROM users " +
                        "WHERE username=? AND password=?";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ){

            ps.setString(1, username);
            ps.setString(2, password);

            var rs = ps.executeQuery();

            return rs.next();

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    public int getUserId(String username) {

        String sql =
                "SELECT id FROM users WHERE username=?";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ){

            ps.setString(1, username);

            var rs = ps.executeQuery();

            if(rs.next()) {
                return rs.getInt("id");
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return -1;
    }
    public boolean userExists(String username) {

        String sql =
                "SELECT * FROM users WHERE username=?";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ){

            ps.setString(1, username);

            var rs = ps.executeQuery();

            return rs.next();

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    public void register(User user) {

        String sql =
                "INSERT INTO users(username,password) VALUES(?,?)";

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ){

            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());

            ps.executeUpdate();

            System.out.println("User Registered!");

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

