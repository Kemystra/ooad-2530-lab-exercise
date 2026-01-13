import javax.swing.*;
import java.awt.*;
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
        TabbedPage coordinatorPage = new TabbedPage();

        // Add panels to card layout
        // Give unique name to each page
        mainPanel.add(loginPage, "LOGIN");
        mainPanel.add(coordinatorPage, "Coordinator");

        add(mainPanel);

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
