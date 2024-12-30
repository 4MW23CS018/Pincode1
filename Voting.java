package pkg1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Voting {
    public boolean hasAlreadyVoted(String usn, String position) {
        String query = "SELECT * FROM Votes WHERE usn = ? AND position = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, usn);
            stmt.setString(2, position);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // True if a vote exists for the position
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean recordVote(String usn, int candidateId, String position) {
    String insertQuery = "INSERT INTO Votes (usn, candidate_id, position) VALUES (?, ?, ?)";
    try (Connection conn = DatabaseConnection.connect();
         PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

        // Validate inputs
        if (usn == null || usn.isEmpty()) {
            System.err.println("Invalid USN");
            return false;
        }
        if (position == null || position.isEmpty()) {
            System.err.println("Invalid Position");
            return false;
        }
        if (candidateId <= 0) {
            System.err.println("Invalid Candidate ID");
            return false;
        }

        // Prepare and execute the query
        stmt.setString(1, usn);
        stmt.setInt(2, candidateId);
        stmt.setString(3, position);
        stmt.executeUpdate();
        System.out.println("Vote recorded successfully for USN: " + usn + ", Position: " + position);
        return true;
    } catch (java.sql.SQLIntegrityConstraintViolationException e) {
        System.err.println("Constraint violation: likely duplicate vote. USN: " + usn + ", Position: " + position);
        return false;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}


}
