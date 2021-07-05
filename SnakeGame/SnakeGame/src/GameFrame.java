import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    final static int FRAME_WIDTH = 500;
    final static int FRAME_HEIGHT = 550;
    final static Color FRAME_COLOR = new Color(77, 77, 77);
    private BoardPanel boardPanel;
    public SettingsPanel settingsPanel;
    public boolean isSettingsDisplayed = false;

    public GameFrame(String title) {
        super(title);
        setFrameConfig();
    }

    private void setFrameConfig() {
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.getContentPane().setBackground(FRAME_COLOR);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void createStartingView() {
        JPanel menuPanel = createMenuPanel();
        this.getContentPane().add(menuPanel);

        menuPanel.add(createLogoLabel());
        menuPanel.add(createTitleLabel());
        menuPanel.add(createGameViewButton());
        menuPanel.add(createHighScoresButton());
        menuPanel.add(createSettingsButton());
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        menuPanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        menuPanel.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(FRAME_COLOR);
        return menuPanel;
    }

    private JLabel createLogoLabel() {
        final String pathToLogo = "images\\snake_icon.png";
        ImageIcon imageIcon = new ImageIcon(pathToLogo);
        JLabel gameIcon = new JLabel(imageIcon);
        gameIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        return gameIcon;
    }

    private JLabel createTitleLabel() {
        JLabel gameTitle = new JLabel("Snake");
        gameTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameTitle.setFont(new Font(Font.MONOSPACED, Font.BOLD,24));
        return gameTitle;
    }

    private JButton createGameViewButton() {
        JButton playButton = new JButton("     PLAY     ");
        GUIManager.setDefaultConfigForMenuButtons(playButton);
        playButton.addActionListener(new GameStart());
        return playButton;
    }

    private JButton createHighScoresButton() {
        JButton highScoresButton = new JButton("  TOP SCORES  ");
        GUIManager.setDefaultConfigForMenuButtons(highScoresButton);
        highScoresButton.addActionListener(e -> GUIManager.showTopScores());
        return highScoresButton;
    }

    private JButton createSettingsButton() {
        JButton settings = new JButton("   SETTINGS   ");
        settings.addActionListener(e -> GUIManager.toggleSettings(GameFrame.this));
        GUIManager.setDefaultConfigForMenuButtons(settings);
        return settings;
    }

    public void setGamePanel(BoardPanel boardPanel) {
        this.boardPanel = boardPanel;
    }

    public void startGame() {
        boardPanel.startGame();
    }

    public SettingsPanel getSettingsPanel() {
        return settingsPanel;
    }

    public void setSettingsPanel(SettingsPanel settingsPanel) {
        this.settingsPanel = settingsPanel;
    }
}
