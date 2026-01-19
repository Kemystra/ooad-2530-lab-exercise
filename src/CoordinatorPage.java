import javax.swing.*;
import java.awt.*;

public class CoordinatorPage extends JPanel {
    public CoordinatorPage(SessionController sessionController, UserController userController) {
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Add/Edit Sessions", new SessionsPanel(sessionController, userController));
        tabbedPane.addTab("Assign Sessions", new AssignSessionsPanel(sessionController));
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
    private UserController userController;
    private DefaultListModel<String> sessionListModel;
    private JList<String> sessionList;

    public SessionsPanel(SessionController sessionController, UserController userController) {
        this.sessionController = sessionController;
        this.userController = userController;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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

        // Evaluator dropdown field
        JComboBox<User> evaluatorComboBox = new JComboBox<>();
        for (User evaluator : userController.getEvaluators()) {
            evaluatorComboBox.addItem(evaluator);
        }
        add(new JLabel("Evaluator:"));
        add(evaluatorComboBox);

        // Student dropdown field
        JComboBox<User> studentComboBox = new JComboBox<>();
        for (User student : userController.getStudents()) {
            studentComboBox.addItem(student);
        }
        add(new JLabel("Student:"));
        add(studentComboBox);

        // Create button
        JButton createButton = new JButton("Create Session");
        createButton.addActionListener(e -> {
            String day = dayField.getText();
            String month = monthField.getText();
            String year = yearField.getText();
            String date = day + "/" + month + "/" + year;
            String venue = venueField.getText();
            String type = (String) typeComboBox.getSelectedItem();
            User evaluator = (User) evaluatorComboBox.getSelectedItem();
            User student = (User) studentComboBox.getSelectedItem();
            JOptionPane.showMessageDialog(this,
                    "Session Created:\nDate: " + date + "\nVenue: " + venue + "\nType: " + type +
                            "\nEvaluator: " + evaluator + "\nStudent: " + student);
            Session newSession = new Session(date, venue, SessionType.valueOf(type), evaluator, student);
            sessionController.addSession(newSession);
            refreshSessionList();
        });
        add(createButton);

        // Sessions list
        sessionListModel = new DefaultListModel<>();
        sessionList = new JList<>(sessionListModel);
        JScrollPane scrollPane = new JScrollPane(sessionList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("All Sessions"));
        add(scrollPane);

        // Load existing sessions
        refreshSessionList();
    }

    private void refreshSessionList() {
        sessionListModel.clear();
        for (Session session : sessionController.getSessions()) {
            String evaluatorName = session.getEvaluator() != null ? session.getEvaluator().getName() : "N/A";
            String studentName = session.getStudent() != null ? session.getStudent().getName() : "N/A";
            String display = session.getDate() + " | " + session.getVenue() + " | " + session.getType() +
                    " | Evaluator: " + evaluatorName + " | Student: " + studentName;
            sessionListModel.addElement(display);
        }
    }
}

class AssignSessionsPanel extends JPanel {
    public AssignSessionsPanel(SessionController sessionController) {
        setLayout(new GridBagLayout());
        JLabel label = new JLabel("Assign Sessions Panel");
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        add(label);
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
