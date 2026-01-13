import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;



public class TabbedPage extends JPanel {
    private JTabbedPane tabbedPane;
    public TabbedPage(HashMap<String, JPanel> panelForEachTab) {
        setLayout(new BorderLayout());
        tabbedPane = new JTabbedPane();

        // Add tabs
        for (Map.Entry<String, JPanel> entry : panelForEachTab.entrySet()) {
            tabbedPane.addTab(entry.getKey(), entry.getValue());
        }

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
