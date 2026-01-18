import javax.swing.*;
import java.awt.*;

public class CoordinatorPage extends JPanel {
    public CoordinatorPage(SessionController sessionController) {
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        // tabbedPane.addTab("Registration", new RegistrationPanel());
        tabbedPane.addTab("Sessions", new SessionsPanel(sessionController));
        tabbedPane.addTab("Result", new ResultPanel());
        add(tabbedPane, BorderLayout.CENTER);
    }
}

// class RegistrationPanel extends JPanel {
// public RegistrationPanel() {
// setLayout(new GridBagLayout());
// JLabel label = new JLabel("Coordinator Registration Panel");
// label.setFont(new Font("Arial", Font.PLAIN, 18));
// add(label);
// }
// }

class SessionsPanel extends JPanel {
    private SessionController sessionController;

    public SessionsPanel(SessionController sessionController) {
        this.sessionController = sessionController;
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Day field
        JTextField dayField = new JTextField(5);
        add(new JLabel("Day:"));
        add(dayField);

        // Month field
        JTextField monthField = new JTextField(5);
        add(new JLabel("Month:"));
        add(monthField);

        // Year field
        JTextField yearField = new JTextField(5);
        add(new JLabel("Year:"));
        add(yearField);

        // Venue field
        JTextField venueField = new JTextField(20);
        add(new JLabel("Venue:"));
        add(venueField);

        // Session Type field
        String[] sessionTypes = { SessionType.POSTER.name(), SessionType.ORAL.name() };
        JComboBox<String> typeComboBox = new JComboBox<>(sessionTypes);
        add(new JLabel("Session Type:"));
        add(typeComboBox);

        // Create button
        JButton createButton = new JButton("Create Session");
        createButton.addActionListener(e -> {
            String day = dayField.getText();
            String month = monthField.getText();
            String year = yearField.getText();
            String date = day + "/" + month + "/" + year;
            String venue = venueField.getText();
            String type = (String) typeComboBox.getSelectedItem();
            JOptionPane.showMessageDialog(this,
                    "Session Created:\nDate: " + date + "\nVenue: " + venue + "\nType: " + type);
            Session newSession = new Session(date, venue, SessionType.valueOf(type));
            sessionController.addSession(newSession);
        });
        add(createButton);
    }
}

class ResultPanel extends JPanel {
    public ResultPanel() {
        setLayout(new GridBagLayout());
        JLabel label = new JLabel("Coordinator Result Panel");
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        add(label);
    }
}
