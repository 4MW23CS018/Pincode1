package pkg1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login {

    /**
     * Validates user login by checking email and password against the database.
     *
     * @param email    The student's email address.
     * @param password The student's password.
     * @return True if the credentials are valid, false otherwise.
     */
    public boolean validateLogin(String email, String password) {
        String query = "SELECT * FROM Students WHERE email = ? AND password = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Returns true if a match is found
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if a USN has already voted.
     *
     * @param usn The unique student number (USN).
     * @return True if the student has already voted, false otherwise.
     */
    public boolean hasAlreadyVoted(String usn) {
        String query = "SELECT * FROM Votes WHERE usn = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, usn);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Returns true if a vote is already recorded
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if an email exists in the Students table.
     *
     * @param email The student's email address.
     * @return True if the email exists, false otherwise.
     */
    public boolean isEmailRegistered(String email) {
        String query = "SELECT * FROM Students WHERE email = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Returns true if email is found
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Resets the password for a student.
     *
     * @param email       The student's email address.
     * @param newPassword The new password to set.
     * @return True if the password was updated successfully, false otherwise.
     */
    public boolean resetPassword(String email, String newPassword) {
        String query = "UPDATE Students SET password = ? WHERE email = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, email);
            return stmt.executeUpdate() > 0; // Returns true if at least one row is updated
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Main method for testing Login functionalities.
     */
    public static void main(String[] args) {
        Login login = new Login();

        // Test login validation
        String email = "abhishek.23CS001@sode-edu.in"; // Replace with test email
        String password = "password123"; // Replace with test password
        if (login.validateLogin(email, password)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid email or password.");
        }

        // Test if USN has already voted
        String usn = "4MW23CS001"; // Replace with test USN
        if (login.hasAlreadyVoted(usn)) {
            System.out.println("User with USN " + usn + " has already voted.");
        } else {
            System.out.println("User with USN " + usn + " has not voted yet.");
        }

        // Test email registration check
        if (login.isEmailRegistered(email)) {
            System.out.println("Email is registered.");
        } else {
            System.out.println("Email is not registered.");
        }

        // Test password reset
        String newPassword = "newPassword123";
        if (login.resetPassword(email, newPassword)) {
            System.out.println("Password reset successful!");
        } else {
            System.out.println("Failed to reset password.");
        }
    }
}
