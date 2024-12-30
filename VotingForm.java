package pkg1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VotingForm extends JFrame {
    private String email; // Email of the logged-in student
    private JTextField usnField;
    private JComboBox<String> candidateComboBox;
    private JComboBox<String> positionComboBox;
    private JButton voteButton;
    private Voting voting;

    public VotingForm(String email) {
        this.email = email; // Store the email of the logged-in student
        this.voting = new Voting(); // Initialize the voting handler

        setTitle("Voting Form");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel usnLabel = new JLabel("Enter your USN:");
        usnLabel.setBounds(20, 20, 120, 25);
        add(usnLabel);

        usnField = new JTextField();
        usnField.setBounds(150, 20, 200, 25);
        add(usnField);

        JLabel positionLabel = new JLabel("Select Position:");
        positionLabel.setBounds(20, 60, 120, 25);
        add(positionLabel);

        // Dropdown for position selection
        String[] positions = {"President", "Secretary", "Treasurer"};
        positionComboBox = new JComboBox<>(positions);
        positionComboBox.setBounds(150, 60, 200, 25);
        add(positionComboBox);

        JLabel candidateLabel = new JLabel("Choose Candidate:");
        candidateLabel.setBounds(20, 100, 120, 25);
        add(candidateLabel);

        // Fetch candidate list from database
        Candidate candidate = new Candidate();
        List<String> candidates = candidate.getCandidates();
        candidateComboBox = new JComboBox<>(candidates.toArray(new String[0]));
        candidateComboBox.setBounds(150, 100, 200, 25);
        add(candidateComboBox);

        voteButton = new JButton("Vote");
        voteButton.setBounds(150, 140, 100, 25);
        add(voteButton);

        voteButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String usn = usnField.getText();
        int candidateId = candidateComboBox.getSelectedIndex() + 1; // Assuming IDs start from 1
        String position = (String) positionComboBox.getSelectedItem();

        if (usn.isEmpty() || position.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter your USN and select a position.");
            return;
        }

        if (voting.hasAlreadyVoted(usn, position)) {
            JOptionPane.showMessageDialog(null, "You have already voted for this position!");
        } else {
            if (voting.recordVote(usn, candidateId, position)) {
                JOptionPane.showMessageDialog(null, "Vote Recorded Successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to record vote. Please check inputs or try again.");
            }
        }
    }
});


    }
}
