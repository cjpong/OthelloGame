package othello;

/**
 * represents a move in Othello, storing move location and value for computer decision
 * making
 */
public class Move {
    private int[] location;
    private int moveValue;

    /**
     * constructs a move object with a location
     * @param location int array representing row and column
     */
    public Move(int[] location){
        this.location = location;
    }

    /**
     * sets moves' evaluation
     * @param value
     */
    public void setMoveValue(int value){
        this.moveValue = value;
    }

    /**
     * gets the location of the move
     * @return
     */
    public int[] getLocation(){
        return this.location;
    }

    /**
     * gets the evaluation of the move
     * @return
     */
    public int getMoveValue(){
        return this.moveValue;
    }

}
