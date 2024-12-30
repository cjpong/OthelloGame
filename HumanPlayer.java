package othello;

import javafx.scene.layout.Pane;

/**
 * simple class that represents a human player
 */
public class HumanPlayer extends Player{
    public HumanPlayer(Board board, Pane game, int color, Referee referee) {
        super(board, game, color, referee);
        this.isComputer = false;
    }
}
