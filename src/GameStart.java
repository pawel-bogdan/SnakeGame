import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameStart implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        GameFrame root = (GameFrame) SwingUtilities.getRoot((JButton)e.getSource());
        GUIManager.deleteMenuView(root);
        SettingsPanel.readSettings();
        GUIManager.createGameView(root);
    }
}
