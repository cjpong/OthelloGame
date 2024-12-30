package othello;

public class Constants {

    public static final int WHITE = 0;
    public static final int BLACK = 1;
    public static final int CONTROLS_PANE_WIDTH = 250;
    public static final int GRID_WIDTH = 60;
    public static final int BOARD_WIDTH = GRID_WIDTH*10;
    public static final int PIECE_RADIUS = 25;
    public static final int COMPUTER_PLAYER = 1;
    public static final int HUMAN_PLAYER = 0;

    public static final int[][] BOARD_WEIGHTS = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 200, -70, 30, 25, 25, 30, -70, 200, 0},
            {0, -70, -100, -10, -10, -10, -10, -100, -70, 0},
            {0, 30, -10, 2, 2, 2, 2, -10, 30, 0},
            {0, 25, -10, 2, 2, 2, 2, -10, 25, 0},
            {0, 25, -10, 2, 2, 2, 2, -10, 25, 0},
            {0, 30, -10, 2, 2, 2, 2, -10, 30, 0},
            {0, -70, -100, -10, -10, -10, -10, -100, -70, 0},
            {0, 200, -70, 30, 25, 25, 30, -70, 200, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
}
