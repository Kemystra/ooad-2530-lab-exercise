import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;

// Main Application
public class App extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public App() {
        setTitle("Tabbed Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Izzmin will handle the login page logic to each page
        LoginForm loginPage = new LoginForm(() -> cardLayout.show(mainPanel, "Coordinator"));

        // Add pages for each roles (student, coordinator, evaluator)
        // They extend JPanel
        // WRITE YOUR PAGE IN A SEPARATE FILE BY EXTENDING JPanel
        // Here TabbedPage is extending JPanel
        CoordinatorPage coordinatorPage = new CoordinatorPage();

        // Add panels to card layout
        // Give unique name to each page
        mainPanel.add(loginPage, "LOGIN");
        mainPanel.add(coordinatorPage, "Coordinator");

        add(mainPanel);

        // Ctrl+Q to close the application
        KeyStroke ctrlQ = KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ctrlQ, "quit");
        getRootPane().getActionMap().put("quit", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dispose();
                System.exit(0);
            }
        });

        // Show empty page first
        cardLayout.show(mainPanel, "LOGIN");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }
}
