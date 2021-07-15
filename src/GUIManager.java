import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GUIManager {

     public static void deleteMenuView(GameFrame frame) {
        frame.getContentPane().removeAll();
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
        frame.isSettingsDisplayed = false;
    }

    public static void createGameView(GameFrame frame) {
        BoardPanel boardPanel = new BoardPanel();
        boardPanel.reset();
        frame.setGamePanel(boardPanel);
        StatsPanel statsPanel = new StatsPanel();
        statsPanel.reset();
        frame.getContentPane().add(statsPanel);
        frame.getContentPane().add(boardPanel);
    }

    public static void toggleSettings(GameFrame frame) {
         JPanel menuPanel = ((JPanel)frame.getContentPane().getComponent(0));
         if(frame.isSettingsDisplayed) {
             menuPanel.remove(frame.getSettingsPanel());
             frame.setSettingsPanel(null);
             frame.isSettingsDisplayed = false;
         }
         else {
             SettingsPanel settingsPanel = new SettingsPanel();
             frame.setSettingsPanel(settingsPanel);
             ((JPanel)frame.getContentPane().getComponent(0)).add(settingsPanel);
             frame.isSettingsDisplayed = true;
         }
        frame.revalidate();
        frame.repaint();
    }

    public static void setDefaultConfigForMenuButtons(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font(Font.MONOSPACED, Font.BOLD,18));
        button.setBackground(Color.ORANGE);
        button.setBorder(BorderFactory.createLineBorder(GameFrame.FRAME_COLOR, 15));
    }

    public static void setDefaultConfigForSettingsButtons(JButton button) {
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font(Font.MONOSPACED, Font.BOLD,14));
        button.setBorder(BorderFactory.createLineBorder(GameFrame.FRAME_COLOR, 2));
    }

    public static void showTopScores() {
        JFrame frameForTopScores = createFrameForTopScores();
        JPanel scoresPanel = new JPanel();
        scoresPanel.setBackground(GameFrame.FRAME_COLOR);
        frameForTopScores.add(scoresPanel);
        scoresPanel.setLayout(new BoxLayout(scoresPanel, BoxLayout.Y_AXIS));
        JLabel topScoresLabel = new JLabel("\uD83C\uDFC6 Top scores \uD83C\uDFC6");
        topScoresLabel.setBorder(BorderFactory.createEmptyBorder(30, 0 ,30, 0));
        topScoresLabel.setForeground(new Color(230, 172, 0));
        topScoresLabel.setFont(new Font(Font.SERIF, Font.BOLD, 26));
        topScoresLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        scoresPanel.add(topScoresLabel);
        JLabel infoLabel = new JLabel("Nr.   Player   Points   Time   Level   Unit size");
        infoLabel.setForeground(new Color(230, 172, 0));
        infoLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        scoresPanel.add(infoLabel);
        ArrayList<String> topScores = loadScores();
        topScores.sort((o1, o2) -> {
            short indexOfCollectedPointsColumn = 1;
            try {
                Integer score1 = Integer.valueOf(o1.split(" {3}")[indexOfCollectedPointsColumn]);
                Integer score2 = Integer.valueOf(o2.split(" {3}")[indexOfCollectedPointsColumn]);
                return score1.compareTo(score2) * -1;
            }
            catch (ArrayIndexOutOfBoundsException e) {
                return 0;
            }
        });

        for(int i = 0; i < topScores.size(); i++) {
            JLabel scoreLabel = new JLabel((i+1 + "   ") + topScores.get(i));
            scoreLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            scoresPanel.add(scoreLabel);
        }

        frameForTopScores.setVisible(true);
        frameForTopScores.setLocationRelativeTo(null);

    }

    private static ArrayList<String> loadScores() {
        String pathToHistoryFile = "history.txt";
        File historyFile = new File(pathToHistoryFile);
        Scanner scanner = null;
        try {
            scanner = new Scanner(historyFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> scores = new ArrayList<>();
        while(scanner.hasNextLine()) {
            scores.add(scanner.nextLine());
        }
        return scores;
    }

    private static JFrame createFrameForTopScores() {
        JFrame frame = new JFrame();
        frame.setSize(400, 600);
        frame.setBackground(GameFrame.FRAME_COLOR);
        return frame;
    }
}
