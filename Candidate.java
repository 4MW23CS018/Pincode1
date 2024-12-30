package pkg1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Candidate {
    public List<String> getCandidates() {
        List<String> candidates = new ArrayList<>();
        String query = "SELECT * FROM Candidates";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                candidates.add(rs.getInt("candidate_id") + ": " + rs.getString("name") + " - " + rs.getString("position"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidates;
    }
}
