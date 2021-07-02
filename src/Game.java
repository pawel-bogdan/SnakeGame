import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class Game {
    private final String userName;
    private final int collectedPoints;
    private final double playedTime;
    private final String difficultyLevel;
    private final String unitSize;

    public Game(String userName, int collectedPoints, double playedTime, String difficultyLevel, String unitSize) {
        this.userName = userName;
        this.collectedPoints = collectedPoints;
        this.playedTime = playedTime;
        this.difficultyLevel = difficultyLevel;
        this.unitSize = unitSize;
    }

    public void saveGameInHistory() {
        String pathToHistoryFile = "C:\\Users\\pawel\\OneDrive\\Pulpit\\Projects\\JAVA\\SnakeGame\\history.txt";
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(pathToHistoryFile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(fileWriter == null) {
            return;
        }
        try {
            DecimalFormat df = new DecimalFormat("0.00");
            String formattedPlayedTime = df.format(playedTime);
            fileWriter.append(String.format("%s   %d   %s   %s   %s\n", userName, collectedPoints, formattedPlayedTime, difficultyLevel, unitSize));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
