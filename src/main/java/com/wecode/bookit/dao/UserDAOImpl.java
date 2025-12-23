package com.wecode.bookit.dao;

import com.wecode.bookit.model.Users;
import com.wecode.bookit.util.ConManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    List<Users> users = new ArrayList<>();
    Connection con = null;

    // ****************************************************************** //
    //this function can only be used by an admin,the logic is written in UserService
    @Override
    public boolean addUsers(Users authenticatedUser, Users newUser) {
        try {
            con = ConManager.getConnection();
            String sql = "INSERT INTO users (id, username, password, name, email, phone, role, credits) VALUES (?,?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, newUser.getId());
            statement.setString(2, newUser.getUsername());
            statement.setString(3, newUser.getPassword());
            statement.setString(4, newUser.getName());
            statement.setString(5, newUser.getEmail());
            statement.setString(6, newUser.getPhone());
            statement.setString(7, newUser.getRole());
            statement.setInt(8, newUser.getCredits());

            int n = statement.executeUpdate();
            System.out.println(n + " records inserted");
            statement.close();
            con.close();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // ****************************************************************** //
    @Override
    public boolean deleteUsers(Users authenticatedUser,String username) {
        try{
            con = ConManager.getConnection();
            String sql_delete = "delete from users where username=?";
            PreparedStatement statement = con.prepareStatement(sql_delete);
            statement.setString(1, username);
            int n = statement.executeUpdate();
            System.out.println(n + " records deleted");
            statement.close();
            con.close();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // ****************************************************************** //
    @Override
    public int updateUsers(Users authenticatedUser,Users user) {
        try {
            con = ConManager.getConnection();
            String sql_update = "UPDATE Users SET password=? WHERE username=?";
            PreparedStatement statement = con.prepareStatement(sql_update);
            statement.setString(1, user.getPassword());

            int rowsAffected = statement.executeUpdate();
            statement.close();
            con.close();

            return rowsAffected;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // ****************************************************************** //
    @Override
    public List<Users>  findUsers() {
        Users user = null;
        try {
            Connection con = ConManager.getConnection();
            String sql = "SELECT * FROM Users";
            PreparedStatement statement = con.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                // Create the Users object and populate it with data from the ResultSet
                user = new Users(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("role"),
                        rs.getInt("credits")
                );
                users.add(user);
            }
            rs.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    // ****************************************************************** //
    @Override
    public List<Users> getUsersbyusername(String username) {
        List<Users> users = new ArrayList<>();
        Users user = null;
        boolean found = false;
        try {
            Connection con = ConManager.getConnection();
            String sql = "SELECT * FROM Users WHERE username = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                found = true;
                // Create the Users object and populate it with data from the ResultSet
                user = new Users(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("role"),
                        rs.getInt("credits")
                );
                users.add(user);
            }
            if (!found) {
                System.out.println("Username not found");
            }
            rs.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    // ****************************************************************** //
    @Override
    public void updateUserCredits(Users authenticatedUser, int credits) {
        try {
            Connection con = ConManager.getConnection();
            String sql = "UPDATE Users SET credits = ? WHERE id = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            // Set the new credits value
            statement.setInt(1, credits);
            // Set the user ID to identify which user's credits to update
            statement.setInt(2, Integer.parseInt(authenticatedUser.getId()));
            // Execute the update
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Credits updated successfully.");
            } else {
                System.out.println("No user found with the provided ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}