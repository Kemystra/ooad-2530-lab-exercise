import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class CoordinatorPage extends JPanel {
    public CoordinatorPage(SessionController sessionController, UserController userController) {
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Add/Edit Sessions", new SessionsPanel(sessionController, userController));
        tabbedPane.addTab("Result", new ResultPanel(userController));
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

class ResultPanel extends JPanel {
    private UserController userController;
    private JTable studentTable;
    private DefaultTableModel tableModel;

    public ResultPanel(UserController userController) {
        this.userController = userController;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Student Results & Award Nomination");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Table with student data
        String[] columnNames = { "Student Name", "Student ID", "Total Marks", "People's Choice Votes", "Award",
                "Action" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only Award column (4) is editable
                return column == 4;
            }
        };

        // Populate table with students
        refreshStudentTable();

        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(35);

        // Set up Award dropdown in the table
        String[] awards = { "-- Select Award --",
                Award.BEST_POSTER.getDisplayName(),
                Award.BEST_ORAL.getDisplayName(),
                Award.PEOPLE_S_CHOICE.getDisplayName()
        };

        JComboBox<String> awardComboBox = new JComboBox<>(awards);
        studentTable.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(awardComboBox));

        // Add Nominate button renderer and editor
        studentTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        studentTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), this));

        JScrollPane scrollPane = new JScrollPane(studentTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void refreshStudentTable() {
        tableModel.setRowCount(0);
        for (User student : userController.getStudents()) {
            // Sample marks and votes (in real app, these would come from a data source)
            int totalMarks = (int) (Math.random() * 50) + 50; // Random marks 50-100
            int votes = (int) (Math.random() * 20); // Random votes 0-20
            Object[] row = {
                    student.getName(),
                    student.getId(),
                    totalMarks,
                    votes,
                    "-- Select Award --",
                    "Nominate"
            };
            tableModel.addRow(row);
        }
    }

    public void nominateStudent(int row) {
        String studentName = (String) tableModel.getValueAt(row, 0);
        String award = (String) tableModel.getValueAt(row, 4);
        if (award.equals("-- Select Award --")) {
            JOptionPane.showMessageDialog(this, "Please select an award first!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Nominated " + studentName + " for " + award + "!",
                    "Nomination Successful",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

// Button renderer for JTable
class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "Nominate" : value.toString());
        return this;
    }
}

// Button editor for JTable
class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean clicked;
    private ResultPanel resultPanel;
    private int currentRow;

    public ButtonEditor(JCheckBox checkBox, ResultPanel resultPanel) {
        super(checkBox);
        this.resultPanel = resultPanel;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        label = (value == null) ? "Nominate" : value.toString();
        button.setText(label);
        clicked = true;
        currentRow = row;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (clicked) {
            resultPanel.nominateStudent(currentRow);
        }
        clicked = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }
}
