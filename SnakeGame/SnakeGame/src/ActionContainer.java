import javax.swing.*;
import java.awt.event.ActionEvent;

public class ActionContainer {

    public final Action moveUpAction;
    public final Action moveDownAction;
    public final Action moveRightAction;
    public final Action moveLeftAction;

    public ActionContainer() {
        this.moveUpAction = new MoveUpAction();
        this.moveDownAction = new MoveDownAction();
        this.moveRightAction = new MoveRightAction();
        this.moveLeftAction = new MoveLeftAction();
    }

    private static class MoveUpAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!BoardPanel.direction.equals("down")) {
                BoardPanel.direction = "up";
            }
        }
    }

    private static class MoveDownAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!BoardPanel.direction.equals("up")) {
                BoardPanel.direction = "down";
            }
        }
    }

    private static class MoveRightAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!BoardPanel.direction.equals("left")) {
                BoardPanel.direction = "right";
            }
        }
    }

    private static class MoveLeftAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!BoardPanel.direction.equals("right")) {
                BoardPanel.direction = "left";
            }
        }
    }

}
