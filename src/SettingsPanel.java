import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SettingsPanel extends JPanel {
    /*public static final int WIDTH = GameFrame.FRAME_WIDTH;
    public static final int HEIGHT = GameFrame.FRAME_HEIGHT;*/
    private final UserNamePanel userNamePanel;
    private final DifficultyLevelPanel difficultyLevelPanel;
    private final UnitSizePanel unitSizePanel;

    public SettingsPanel() {
        this.setLayout(new FlowLayout());
        this.setOpaque(false);
        readSettings(); // must be before initializing under panels

        userNamePanel = new UserNamePanel();
        difficultyLevelPanel = new DifficultyLevelPanel();
        unitSizePanel = new UnitSizePanel();

        this.add(userNamePanel);
        this.add(createSaveButton());
        this.add(difficultyLevelPanel);
        this.add(unitSizePanel);
    }

    public static void readSettings() {
        String pathToConfigFile = "config.txt";
        File configFile = new File(pathToConfigFile);
        Scanner scanner = null;
        try {
            scanner = new Scanner(configFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(scanner == null)
            return;

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(line.contains("user name:"))
                BoardPanel.userName = line.replaceFirst("user name: ", "");
            else if(line.contains("difficulty level: "))
                BoardPanel.difficultyLevel = line.replaceFirst("difficulty level: ", "");
            else if(line.contains("unit size: "))
                BoardPanel.unitSize = line.replaceFirst("unit size: ", "");
        }
    }

    private JButton createSaveButton() {
        JButton saveButton = new JButton(" Save ");
        GUIManager.setDefaultConfigForSettingsButtons(saveButton);
        saveButton.setBackground(Color.gray);
        saveButton.addActionListener(e -> saveSettings());
        return saveButton;
    }

    private void saveSettings() {
        String pathToConfigFile = "config.txt";
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(pathToConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(fileWriter == null)
            return;
        try {
            fileWriter.write("user name: " + this.userNamePanel.USER_NAME_INPUT.getText() + "\n");
            fileWriter.write("difficulty level: " + this.difficultyLevelPanel.actualDifficultyLevel + "\n");
            fileWriter.write("unit size: " + this.unitSizePanel.actualUnitSize + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static class UserNamePanel extends JPanel {
        private final JTextField USER_NAME_INPUT;

        public UserNamePanel() {
            this.setMaximumSize(new Dimension(GameFrame.FRAME_WIDTH, 60));
            this.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            this.setOpaque(false);

            JLabel USER_NAME_LABEL = new JLabel("Name: ");
            USER_NAME_INPUT = new JTextField(30);
            if(BoardPanel.userName != null)
                USER_NAME_INPUT.setText(BoardPanel.userName);
            this.add(USER_NAME_LABEL);
            this.add(USER_NAME_INPUT);
        }
    }

    private static class DifficultyLevelPanel extends JPanel {
        private final JLabel DIFFICULTY_LEVEL_LABEL = new JLabel();
        private final String DEFAULT_DIFFICULTY_LEVEL = "medium";
        private String actualDifficultyLevel = DEFAULT_DIFFICULTY_LEVEL;

        public DifficultyLevelPanel() {
            this.setOpaque(false);
            this.setMaximumSize(new Dimension(GameFrame.FRAME_WIDTH, 80));
            this.setAlignmentX(JComponent.CENTER_ALIGNMENT);

            if(BoardPanel.difficultyLevel != null)
                DIFFICULTY_LEVEL_LABEL.setText("Difficulty level: " + BoardPanel.difficultyLevel);
            else
                DIFFICULTY_LEVEL_LABEL.setText("Difficulty level: " + DEFAULT_DIFFICULTY_LEVEL);

            this.add(DIFFICULTY_LEVEL_LABEL);
            this.add(createEASYDifficultyLevelButton());
            this.add(createMEDIUMDifficultyLevelButton());
            this.add(createHARDDifficultyLevelButton());
        }

        private JButton createEASYDifficultyLevelButton() {
            JButton easyDiffLvlButton = new JButton("easy");
            easyDiffLvlButton.setBackground(Color.GREEN);
            GUIManager.setDefaultConfigForSettingsButtons(easyDiffLvlButton);
            easyDiffLvlButton.addActionListener(e -> updateDifficultyLvlLabel(" easy "));
            return easyDiffLvlButton;
        }

        private JButton createMEDIUMDifficultyLevelButton() {
            JButton mediumDiffLvlButton = new JButton(" medium ");
            mediumDiffLvlButton.setBackground(Color.ORANGE);
            GUIManager.setDefaultConfigForSettingsButtons(mediumDiffLvlButton);
            mediumDiffLvlButton.addActionListener(e -> updateDifficultyLvlLabel("medium"));
            return mediumDiffLvlButton;
        }

        private JButton createHARDDifficultyLevelButton() {
            JButton hardDiffLvlButton = new JButton(" hard ");
            hardDiffLvlButton.setBackground(Color.RED);
            GUIManager.setDefaultConfigForSettingsButtons(hardDiffLvlButton);
            hardDiffLvlButton.addActionListener(e -> updateDifficultyLvlLabel("hard"));
            return hardDiffLvlButton;
        }

        private void updateDifficultyLvlLabel(String newDiffLvl) {
            actualDifficultyLevel = newDiffLvl;
            DIFFICULTY_LEVEL_LABEL.setText("Difficulty level: " + actualDifficultyLevel);
        }
    }

    private static class UnitSizePanel extends JPanel {
        private final JLabel UNIT_SIZE_LABEL;
        private final String DEFAULT_UNIT_SIZE = "medium";
        private String actualUnitSize = DEFAULT_UNIT_SIZE;

        public UnitSizePanel() {
            this.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            this.setOpaque(false);

            UNIT_SIZE_LABEL = new JLabel("Unit size: " + DEFAULT_UNIT_SIZE);
            if(BoardPanel.unitSize != null)
                UNIT_SIZE_LABEL.setText("Unit size: " + BoardPanel.unitSize);

            this.add(UNIT_SIZE_LABEL);
            this.add(createSMALLUnitSizeButton());
            this.add(createMEDIUMUnitSizeButton());
            this.add(createLARGEUnitSizeButton());
        }

        private JButton createSMALLUnitSizeButton() {
            JButton smallUnitSizeButton = new JButton(" small ");
            smallUnitSizeButton.setBackground(Color.CYAN);
            GUIManager.setDefaultConfigForSettingsButtons(smallUnitSizeButton);
            smallUnitSizeButton.addActionListener(e -> updateUnitSizeLabel("small"));
            return smallUnitSizeButton;
        }

        private JButton createMEDIUMUnitSizeButton() {
            JButton mediumUnitSizeButton = new JButton(" medium ");
            mediumUnitSizeButton.setBackground(Color.CYAN);
            GUIManager.setDefaultConfigForSettingsButtons(mediumUnitSizeButton);
            mediumUnitSizeButton.addActionListener(e -> updateUnitSizeLabel("medium"));
            return mediumUnitSizeButton;
        }

        private JButton createLARGEUnitSizeButton() {
            JButton largeUnitSizeButton = new JButton(" large ");
            largeUnitSizeButton.setBackground(Color.CYAN);
            GUIManager.setDefaultConfigForSettingsButtons(largeUnitSizeButton);
            largeUnitSizeButton.addActionListener(e -> updateUnitSizeLabel("large"));
            return largeUnitSizeButton;
        }

        private void updateUnitSizeLabel(String newUnitSize) {
            actualUnitSize = newUnitSize;
            UNIT_SIZE_LABEL.setText("Unit size: " + actualUnitSize);
        }
    }

}
