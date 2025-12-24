import javax.swing.*;
import java.awt.*;

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

        // Create pages using separate classes
        LoginForm emptyPage = new LoginForm(() -> cardLayout.show(mainPanel, "HOME"));
        TabbedPage homePage = new TabbedPage();

        // Add panels to card layout
        mainPanel.add(emptyPage, "EMPTY");
        mainPanel.add(homePage, "HOME");

        add(mainPanel);

        // Show empty page first
        cardLayout.show(mainPanel, "EMPTY");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }
}


// Home Page Class
class TabbedPage extends JPanel {
    private JTabbedPane tabbedPane;
    public TabbedPage() {
        setLayout(new BorderLayout());
        tabbedPane = new JTabbedPane();

        // Add tabs
        tabbedPane.addTab("Tab 1", new Dashboard());
        tabbedPane.addTab("Tab 2", createTabContent("Content for Tab 2"));
        tabbedPane.addTab("Tab 3", createTabContent("Content for Tab 3"));

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createTabContent(String content) {
        JPanel panel = new JPanel(new GridBagLayout());
        JLabel label = new JLabel(content);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(label);
        return panel;
    }

    public void addTab(String title, JPanel panel) {
        tabbedPane.addTab(title, panel);
    }
}
