package othello;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * This is the PaneOrganizer Class, the class that instantiates the BorderPane, instantiates the Tetris game
 * itself, and assigns different instantiated panes to different sectors of the borderPane.
 */
public class PaneOrganizer {
    private BorderPane root;

    public PaneOrganizer(Controls controls, Pane gamePane) {
        this.root = new BorderPane();
        //initializes BorderPane
        this.root.setCenter(gamePane);
        this.root.setRight(controls.getPane());
    }

    /**
     *This method returns the root, in this case the borderPane for later utilization in the App class when the
     * PaneOrganizer class is instantiated and the root is called from it.
     */
    public BorderPane getRoot(){
        return this.root;
    }
}
