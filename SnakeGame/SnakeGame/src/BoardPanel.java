import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class BoardPanel extends JPanel {
    static int UNIT_SIZE;
    final Color BOARD_COLOR = new Color(255, 204, 153);
    final Color SNAKE_COLOR = new Color(0, 204, 0);
    static int ROWS;
    static int COLUMNS;
    static String direction = "right";
    private final Snake snake;
    private Position apple;
    private final ArrayList<ArrayList<JPanel>> panels;
    public static String userName;
    public static String difficultyLevel;
    public static String unitSize;


    public BoardPanel() {
        super();
        UNIT_SIZE = getUnitSize();
        System.out.println(UNIT_SIZE);
        ROWS = (GameFrame.FRAME_HEIGHT - StatsPanel.HEIGHT) / UNIT_SIZE;
        COLUMNS = GameFrame.FRAME_WIDTH / UNIT_SIZE;
        this.snake = new Snake();
        this.setLayout(new GridLayout(ROWS, COLUMNS, 0 , 0));
        this.setSize(GameFrame.FRAME_WIDTH, GameFrame.FRAME_HEIGHT - StatsPanel.HEIGHT);
        ActionContainer actionContainer = new ActionContainer();
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "moveUp");
        this.getActionMap().put("moveUp", actionContainer.moveUpAction);
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        this.getActionMap().put("moveDown", actionContainer.moveDownAction);
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        this.getActionMap().put("moveRight", actionContainer.moveRightAction);
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        this.getActionMap().put("moveLeft", actionContainer.moveLeftAction);
        //setFocusable(true); // It is necessary to KeyListener
        //requestFocusInWindow();
        panels = new ArrayList(ROWS);
        for(int i = 0; i < ROWS; i++)
            panels.add(new ArrayList(COLUMNS));
        this.setBackground(BOARD_COLOR);
        divideIntoSquares();
        placeStartingSnake();
        placeApple();
    }

    private static int getUnitSize() {
        if (unitSize.equals("small"))
            return 20;
        else if(unitSize.equals("medium"))
            return 25;
        else
            return 50;
    }

    private void divideIntoSquares() {
        for (int i = 0; i < (GameFrame.FRAME_HEIGHT - StatsPanel.HEIGHT) / UNIT_SIZE; i++) {
            for (int j = 0; j < GameFrame.FRAME_WIDTH / UNIT_SIZE; j++) {
                final JPanel unit = new JPanel();
                unit.setSize(new Dimension(UNIT_SIZE, UNIT_SIZE));
                unit.setMaximumSize(new Dimension(UNIT_SIZE, UNIT_SIZE));
                unit.setBackground(BOARD_COLOR);
                unit.setBorder(BorderFactory.createLineBorder(BOARD_COLOR, 0));
                this.add(unit);
                panels.get(i).add(j, unit);
            }
        }
    }

    private void placeStartingSnake() {
        ArrayList<Position> snakeElements = snake.getSnakeElements();
        for(int i = 0; i < snake.size(); i++) {
            Position snakeElement = snakeElements.get(i);
            panels.get(snakeElement.getRow()).get(snakeElement.getColumn()).setBackground(SNAKE_COLOR);
        }
    }

    private void placeApple() {
        Random rand = new Random();
        int row = rand.nextInt(ROWS);
        int column = rand.nextInt(COLUMNS);
        Position appleProposition = new Position(row, column);
        while(snake.contains(appleProposition)) {
            row = rand.nextInt(ROWS);
            column = rand.nextInt(COLUMNS);
            appleProposition = new Position(row, column);
        }
        apple = appleProposition;
        panels.get(row).get(column).setBackground(Color.RED);
    }

    public void startGame() {
        new Thread(this::updateSituation).start();
    }

    private void updateSituation() {
        while(true) {
            Position snakeTail = snake.getTail();
            snake.move();
            if (isCollision()) {
                Game game = new Game(userName, StatsPanel.points, StatsPanel.time, difficultyLevel, unitSize);
                game.saveGameInHistory();
                System.out.println("Saved");
                return;
            }
            updateTime();
            changeColorInPosition(snakeTail, BOARD_COLOR);
            Position snakeHead = snake.getHead();
            changeColorInPosition(snakeHead, SNAKE_COLOR);
            checkCollisionWithApple(snakeHead);
        }
    }

    private void updateTime() {
        DecimalFormat df = new DecimalFormat("0.00");
        StatsPanel.time += snake.getSleepTime() / 1000.0;
        StatsPanel.TIME_LABEL.setText("Time: " + (df.format(StatsPanel.time)));
    }

    private void updatePoints() {
        StatsPanel.points++;
        StatsPanel.POINTS_LABEL.setText("Points: " + StatsPanel.points);
    }

    private void changeColorInPosition(Position pos, Color color) {
        panels.get(pos.getRow()).get(pos.getColumn()).setBackground(color);
    }

    private void checkCollisionWithApple(Position snakeHead) {
        if (snakeHead.getRow() == apple.getRow()) {
            if (snakeHead.getColumn() == apple.getColumn()) {
                snake.eatApple();
                changeColorInPosition(snake.getTail(), SNAKE_COLOR);
                updatePoints();
                placeApple();
            }
        }
    }

    private boolean isCollision() {
        return isCollisionWithSnakeBody() || isCollisionWithWall();
    }

    private boolean isCollisionWithSnakeBody() {
        Position snakeHead = snake.getHead();
        for (Position snakeElement: snake.getSnakeElements()) {
            if(snakeHead != snakeElement) {
                if(snakeHead.equals(snakeElement))
                    return true;
            }
        }
        return false;
    }

    private boolean isCollisionWithWall() {
        Position snakeHead = snake.getHead();
        int snakeHeadRow = snakeHead.getRow();
        int snakeHeadColumn = snakeHead.getColumn();
        return snakeHeadRow < 0 || snakeHeadRow >= ROWS || snakeHeadColumn < 0 || snakeHeadColumn >= COLUMNS;
    }

    public void reset() {
        direction = "right";
    }

}
