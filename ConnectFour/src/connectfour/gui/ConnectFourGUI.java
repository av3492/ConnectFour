package connectfour.gui;

import connectfour.model.ConnectFourBoard;
import connectfour.model.Observer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * The main class that represents the game GUI.
 *
 * @author RIT CS
 * @author Anvesh Sai Vemuri
 */
public class ConnectFourGUI extends Application implements Observer<ConnectFourBoard> {

    /**
     * Creates a image named empty which has white background to represent unmarked positions on board
     */
    private static Image EMPTY = new Image(Objects.requireNonNull(ConnectFourGUI.class.getResourceAsStream("empty.png")));
    /**
     * Creates a image named p1 which has black background to represent player1 positions on board
     */
    private static Image P1 = new Image(Objects.requireNonNull(ConnectFourGUI.class.getResourceAsStream("p1black.png")));
    /**
     * Creates a image named p1 which has black background to represent player1 positions on board
     */
    private static Image P2 = new Image(Objects.requireNonNull(ConnectFourGUI.class.getResourceAsStream("p2red.png")));
    /**
     * Creates a Board
     */
    private ConnectFourBoard board;
    /**
     * Creates a Gridpane
     */
    private GridPane gridPane;
    /**
     * Creates a Borderpane
     */
    private BorderPane borderPane;

    /**
     * Initializes the Gui
     */
    @Override
    public void init() {
        board = new ConnectFourBoard();
        gridPane = new GridPane();
        borderPane = new BorderPane();
        this.board.addObserver(this);
    }

    /**
     * This method creates Buttons In Gridpane with their event handlers.
     *
     * @return the gridpane
     */
    public GridPane grid() {


        for (int row = 0; row < ConnectFourBoard.ROWS; row++) {
            for (int col = 0; col < ConnectFourBoard.COLS; col++) {


                Button br = new Button();
                br.setGraphic(new ImageView(EMPTY));
                int column = col;
                br.setOnAction((event) -> {
                    if (this.board.isValidMove(column)) {
                        this.board.makeMove(column);
                    }
                });
                gridPane.add(br, col, row);
            }
        }
        return gridPane;
    }

    /**
     * This method creates a borderpane with the texts which gives the statistics of the game.
     *
     * @return it returns the borderpane which contains the game statistics
     */
    private Pane makeBorderPane() {


        Text movesMade = new Text(this.board.getMovesMade() + " moves made");
        borderPane.setLeft(movesMade);
        Text currentPlayer = new Text(" Current Player: " + this.board.getCurrentPlayer());
        borderPane.setCenter(currentPlayer);
        Text status = new Text("Status: " + this.board.getGameStatus());
        borderPane.setRight(status);
        return borderPane;

    }

    /**
     * This method updates the UI and gives the statistics of the game.
     *
     * @param board - the board
     */
    public void update(ConnectFourBoard board) {

        for (int row = 0; row < ConnectFourBoard.ROWS; ++row) {
            for (int col = 0; col < ConnectFourBoard.COLS; ++col) {
                if (board.getContents(row, col) == ConnectFourBoard.Player.P1) {
                    ((Button) gridPane.getChildren().get(row * ConnectFourBoard.COLS + col)).setGraphic(new ImageView(P1));
                } else if ((board.getContents(row, col) == ConnectFourBoard.Player.P2)) {
                    ((Button) gridPane.getChildren().get(row * ConnectFourBoard.COLS + col)).setGraphic(new ImageView(P2));
                }
            }
        }
        ((Text) borderPane.getLeft()).setText(board.getMovesMade() + "  moves made");
        ((Text) borderPane.getCenter()).setText("Current Player: " + board.getCurrentPlayer());
        ((Text) borderPane.getRight()).setText("Status: " + board.getGameStatus());

        if (board.getGameStatus() != ConnectFourBoard.Status.NOT_OVER) {
            gridPane.setDisable(true);
        }
    }

    /**
     * This method initializes all JFX effects components and shows the stage.
     *
     * @param stage - the user interface
     */
    @Override
    public void start(Stage stage) {

        GridPane gridPane = grid();
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);
        borderPane.setBottom(this.makeBorderPane());
        Scene scene = new Scene(borderPane);
        stage.setTitle("ConnectFourGUI");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }
}


