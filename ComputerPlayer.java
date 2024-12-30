package othello;

import com.sun.corba.se.impl.orbutil.closure.Constant;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * ComputerPlayer represents a computer player for the game
 * extends the player class and includes private methods for computing moves.
 */
public class ComputerPlayer extends Player {
    private int level;
    /**
     * @param board
     * @param game
     * @param color
     * @param referee
     * @param level
     */
    public ComputerPlayer(Board board, Pane game, int color, Referee referee, int level) {
        super(board, game, color, referee);
        this.level = level;
        this.isComputer = true;
    }

    /**
     * makes move based on minimax algorithm, the recursivity is determined by the computer's level.
     * @return
     */
    @Override
    public int[] makeMove() {
        Board testBoard = this.board.copyBoard();
        Move bestMove = this.miniMax(testBoard, this.playerColor, this.level);
        if (bestMove == null) {
            return null;
        }
        return bestMove.getLocation();
    }

    /**
     * implements minimax algorithm to determine optimal move
     * evaluates possible moves up to specific depth
     * @param b
     * @param activeColor
     * @param pass depth of move search
     * @return
     */
    private Move miniMax(Board b, int activeColor, int pass) {
        ArrayList<Move> validMoves = b.getPossibleMoves(activeColor);
        if (validMoves.size() == 0) {
            return null;
        }
        Move bestMove = validMoves.get(0); //initializes the best move as the first valid move
        bestMove.setMoveValue(-900); //sets the move value extremely low
        for (Move m : validMoves) {
            Board test = b.copyBoard(); //copies board
            test.clickGrid(m.getLocation(), activeColor); //simulates placing a piece on board
            test.flipSquares(m.getLocation(), activeColor);
            //base case: returns the best move
            if (pass == 1) {
                m.setMoveValue(test.evaluateBoard(activeColor));
                if (m.getMoveValue() > bestMove.getMoveValue()) {
                    bestMove = m; //updates the best move if the current move is better
                }
            }
            else {
                //simulates the opponents move if not at base case
                Board opponentBoard = test.copyBoard();
                int opponentColor = Constants.WHITE;
                if (activeColor == Constants.WHITE) {
                    opponentColor = Constants.BLACK;
                }
                Move opponentMove = miniMax(opponentBoard, opponentColor, pass - 1);
                if (opponentMove == null) {
                    continue;
                }
                //negates opponent move value
                m.setMoveValue(-1 * opponentMove.getMoveValue());
                if (m.getMoveValue() >= bestMove.getMoveValue()) {
                    bestMove = m;
                }
            }
        }
        return bestMove;
    }
}
