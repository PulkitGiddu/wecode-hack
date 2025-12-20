package com.hsbc.bookit.dao;

import com.hsbc.bookit.domain.Users;
import com.hsbc.bookit.exceptions.CreditResetException;
import com.hsbc.bookit.util.ConManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class LoginDAOImpl implements LoginDAO {

    // ****************************************************************** //
    @Override
    //this method checks the database for an existing username and password entered at the time of login
    //if the username and password is correct it will create a user object and send it to the login service.
    public Users authenticate(String username, String password) {
        Users user = null;
        try {
            Connection con = ConManager.getConnection();
            String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
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
                }
            rs.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user; // Return null if authentication fails
    }

    // ****************************************************************** //
    //method to reset the credits of the manager to 2000 (used in the LoginService)
    public void resetCredits(Users user) {
        if (user.getRole().equalsIgnoreCase("manager")) {
            user.setCredits(2000);
            try {
                Connection con = ConManager.getConnection();
                String sql = "UPDATE Users SET credits = ? WHERE id = ?";
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setInt(1, 2000);
                statement.setString(2, user.getId());

                statement.executeUpdate();
                statement.close();
                con.close();
                System.out.println("Manager has 2000 credits!");
            } catch (SQLException e) {
                throw new CreditResetException("Error occurred while renewing manager credits.");
            }
        }
    }
}
