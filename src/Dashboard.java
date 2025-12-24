import javax.swing.*;
import java.awt.*;


public class Dashboard extends JPanel {
    private CardLayout contentLayout;
    private JPanel contentPanel;

    public Dashboard() {
        setLayout(new BorderLayout());

        // Create sidebar
        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        // Create content area with CardLayout
        contentLayout = new CardLayout();
        contentPanel = new JPanel(contentLayout);

        // Add panels for each section
        contentPanel.add(createContent("Content for Panel 1"), "PANEL1");
        contentPanel.add(createContent("Content for Panel 2"), "PANEL2");
        contentPanel.add(createContent("Content for Panel 3"), "PANEL3");

        add(contentPanel, BorderLayout.CENTER);

        // Show first panel by default
        contentLayout.show(contentPanel, "PANEL1");
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(150, 0));
        sidebar.setBackground(new Color(50, 50, 50));

        // Create sidebar buttons
        addSidebarButton(sidebar, "Panel 1", "PANEL1");
        addSidebarButton(sidebar, "Panel 2", "PANEL2");
        addSidebarButton(sidebar, "Panel 3", "PANEL3");

        // Add glue to push buttons to top
        sidebar.add(Box.createVerticalGlue());

        return sidebar;
    }

    private void addSidebarButton(JPanel sidebar, String text, String panelName) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(70, 70, 70));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btn.addActionListener(e -> contentLayout.show(contentPanel, panelName));

        sidebar.add(btn);
        sidebar.add(Box.createVerticalStrut(5));
    }

    private JPanel createContent(String content) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        JLabel label = new JLabel(content);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(label);
        return panel;
    }
}
