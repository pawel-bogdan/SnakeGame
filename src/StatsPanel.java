import javax.swing.*;
import java.awt.*;

public class StatsPanel extends JPanel {
    public final static int WIDTH = GameFrame.FRAME_WIDTH;
    public final static int HEIGHT = 30;
    private final static Color COLOR = new Color(255, 204, 102);
    public static final JButton BACK_TO_MENU_BUTTON = createBackToMenuButton();
    public static final JButton STOP_PAUSE_BUTTON = createStopPauseButton();
    public static final JLabel POINTS_LABEL = new JLabel("Points: 0");
    public static final JLabel TIME_LABEL = new JLabel("Time: 0");
    public static double time;
    public static int points;

    public StatsPanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(COLOR);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        time = 0;
        points = 0;

        this.add(BACK_TO_MENU_BUTTON);
        this.add(POINTS_LABEL);
        this.add(TIME_LABEL);
        this.add(STOP_PAUSE_BUTTON);
    }

    private static JButton createBackToMenuButton() {
        JButton backToMenuButton = new JButton("MENU");
        backToMenuButton.setMinimumSize(new Dimension(100, 30));
        backToMenuButton.setAlignmentX(Component.TOP_ALIGNMENT);
        backToMenuButton.setBackground(Color.ORANGE);
        backToMenuButton.setBorder(BorderFactory.createLineBorder(new Color(255, 170, 0), 2));
        backToMenuButton.addActionListener(e -> {
            GameFrame frame = (GameFrame) SwingUtilities.getRoot((JButton)e.getSource());
            comebackToMenuView(frame);
        });
        return backToMenuButton;
    }

    private static JButton createStopPauseButton() {
        JButton stopPauseButton = new JButton("▶");
        stopPauseButton.setMinimumSize(new Dimension(100, 30));
        stopPauseButton.setAlignmentX(Component.TOP_ALIGNMENT);
        stopPauseButton.setBackground(Color.ORANGE);
        stopPauseButton.setBorder(BorderFactory.createLineBorder(new Color(255, 170, 0), 2));
        stopPauseButton.addActionListener(e -> {
            GameFrame frame = (GameFrame) SwingUtilities.getRoot((JButton)e.getSource());
            StatsPanel statsPanel = (StatsPanel) stopPauseButton.getParent();
            statsPanel.remove(stopPauseButton);
            statsPanel.revalidate();
            statsPanel.repaint();
            frame.startGame();
        });
        return stopPauseButton;
    }

    private static void comebackToMenuView(GameFrame frame) {
        frame.getContentPane().removeAll();
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
        frame.createStartingView();
    }

    public void reset() {
        points = 0;
        time = 0;
        POINTS_LABEL.setText("Points: " + StatsPanel.points);
        TIME_LABEL.setText("Time: " + StatsPanel.time);
    }
}
