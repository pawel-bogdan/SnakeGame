import java.util.ArrayList;

public class Snake {

    private final ArrayList<Position> snakeElements;

    public Snake() {
        snakeElements = new ArrayList<>(3);
        // these are positions where snake will be placed at the start
        snakeElements.add(new Position(BoardPanel.ROWS / 2, BoardPanel.COLUMNS / 4 - 2));
        snakeElements.add(new Position(BoardPanel.ROWS / 2, BoardPanel.COLUMNS / 4 - 1));
        snakeElements.add(new Position(BoardPanel.ROWS / 2, BoardPanel.COLUMNS / 4));
    }

    public int size() {
        return snakeElements.size();
    }

    public ArrayList<Position> getSnakeElements() {
        return snakeElements;
    }

    public Position getHead() {
        return snakeElements.get(snakeElements.size() - 1);
    }

    public Position getTail() {
        return snakeElements.get(0);
    }


    public void move() {
        int newSnakeHeadRow;
        int newSnakeHeadColumn;
        Position snakeHead = getHead();

        switch (BoardPanel.direction) {
            case "up" -> {
                newSnakeHeadRow = snakeHead.getRow() - 1;
                newSnakeHeadColumn = snakeHead.getColumn();
            }
            case "down" -> {
                newSnakeHeadRow = snakeHead.getRow() + 1;
                newSnakeHeadColumn = snakeHead.getColumn();
            }
            case "left" -> {
                newSnakeHeadRow = snakeHead.getRow();
                newSnakeHeadColumn = snakeHead.getColumn() - 1;
            }
            default -> {
                newSnakeHeadRow = snakeHead.getRow();
                newSnakeHeadColumn = snakeHead.getColumn() + 1;
            }
        }

        Position newSnakeHead = new Position(newSnakeHeadRow, newSnakeHeadColumn);
        snakeElements.remove(getTail());
        snakeElements.add(newSnakeHead);
        sleep();
    }

    private void sleep() {
        long sleepTime = getSleepTime();
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void eatApple() {
        grow();
    }

    private void grow() {
        Position tail = snakeElements.get(0);

        Position newTail = new Position(tail.getRow() + 1, tail.getColumn());
        if(isBetweenWalls(newTail) && !BoardPanel.direction.equals("down")) {
            snakeElements.add(0, newTail);
            return;
        }
        newTail = new Position(tail.getRow() - 1, tail.getColumn());
        if(isBetweenWalls(newTail) && !BoardPanel.direction.equals("up")) {
            snakeElements.add(0, newTail);
            return;
        }
        newTail = new Position(tail.getRow(), tail.getColumn() + 1);
        if(isBetweenWalls(newTail) && !BoardPanel.direction.equals("right")) {
            snakeElements.add(0, newTail);
            return;
        }
        newTail = new Position(tail.getRow(), tail.getColumn() - 1);
        if(isBetweenWalls(newTail) && !BoardPanel.direction.equals("left")) {
            snakeElements.add(0, newTail);
            return;
        }
    }

    private boolean isBetweenWalls(Position position) {
        int posRow = position.getRow();
        int posColumn = position.getColumn();
        return posRow >= 0 && posRow < BoardPanel.ROWS && posColumn >= 0 && posColumn < BoardPanel.COLUMNS;
    }

    public boolean contains(Position element) {
        for (Position bodyElement: snakeElements) {
            if(bodyElement.equals(element))
                return true;
        }
        return false;
    }

    public long getSleepTime() {
        if (BoardPanel.difficultyLevel.equals("easy"))
            return 250L;
        else if(BoardPanel.difficultyLevel.equals("hard"))
            return 100L;
        else
            return 150L;
    }

}
