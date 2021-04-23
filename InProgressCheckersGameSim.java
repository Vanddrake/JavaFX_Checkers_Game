import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static java.lang.Integer.parseInt;

/**
 * A program that allows users play an in-progress game of Checkers, with the included ability to
 * add and remove checkers from the board.
 * Mohawk College, COMP 10062 - Programming in Java. August 16, 2020.
 *
 * @author Robert Zaranek (001161598)
 */
public class InProgressCheckersGameSim extends Application {

    /** A Checker Board object **/
    private CheckerBoard board;
    /** Two Graphics Context objects **/
    private GraphicsContext gc, gc2;
    /** If the last attempt to click on a Checker was a success **/
    private boolean selectSuccess = false;
    /** The last selected Checker object  **/
    private Checker selectedChecker;
    /** Text fields where the user may input their desired x and y coordinates **/
    private TextField xPosField, yPosField;
    /** Label for displaying user notifications **/
    private Label notifLabel;


    /**
     * A mouse event handler that calculates the closest Checker to the cursor, then draws a yellow circle
     * around it if the cursor is within the Checker's tile on the Checker Board.
     *
     * @param me A mouse movement event
     */
    private void moveHandler(MouseEvent me) {
        Checker highLight = board.selectChecker(me.getX(),me.getY());   // Holds the current closest Checker
        // Draws a yellow circle if the cursor is within the Checker's tile on the Checker Board
        if((highLight.getxPos()+50 >= me.getX() && highLight.getyPos()+50 >= me.getY()) && (highLight.getxPos() <= me.getX() && highLight.getyPos() <= me.getY())) {
            gc2.clearRect(0,0,500,500);
            gc2.setStroke(Color.YELLOW);
            gc2.setLineWidth(3);
            gc2.strokeOval(highLight.getxPos()+5,highLight.getyPos()+5,40,40);
        }
        // Clears the top Graphics Context layer if the cursor is away from a Checker
        else {
            gc2.clearRect(0,0,500,500);
        }
    }


    /**
     * A mouse event handler that calculates the closest Checker to the cursor, then if a click occurs within
     * a Checker's tile on the Checker Board, that Checker and a boolean value are stored in memory.
     *
     * @param me A mouse click event
     */
    private void pressedHandler(MouseEvent me) {
        Checker highLight = board.selectChecker(me.getX(),me.getY());   // Holds the current closest Checker
        // Stores values to memory if the mouse is clicked within the area of a Checker
        if((highLight.getxPos()+50 >= me.getX() && highLight.getyPos()+50 >= me.getY()) && (highLight.getxPos() <= me.getX() && highLight.getyPos() <= me.getY())) {
            selectedChecker = board.selectChecker(me.getX(),me.getY());
            selectSuccess = true;
        }
        else {
            selectSuccess = false;
        }
    }


    /**
     * A mouse event handler that checks if the last mouse click event was a success. If successful, covers the
     * selected Checker and draws the same Checker at the cursor's current location.
     *
     * @param me A mouse drag event
     */
    private void dragHandler(MouseEvent me) {
        // If the last mouse click was within the range of a Checker
        if(selectSuccess) {
            // Overlaps the selected Checker with a square tile
            gc2.clearRect(0,0,500,500);
            gc2.setFill(Color.SLATEGRAY);
            gc2.fillRect(selectedChecker.getxPos(),selectedChecker.getyPos(),50,50);
            board.drawLegalMoves(gc2,selectedChecker);
            // Draws the appropriate Checker at the cursor's current position
            if(selectedChecker instanceof RedChecker) {
                ((RedChecker) selectedChecker).drawAtPos(gc2, me.getX()-20, me.getY()-20);
            }
            else {
                ((BlueChecker) selectedChecker).drawAtPos(gc2, me.getX()-20, me.getY()-20);
            }
        }
    }


    /**
     * A mouse event handler that clears the top Graphics Context layer on mouse release.
     *
     * @param me A mouse release event
     */
    private void releasedHandler(MouseEvent me) {
        gc2.clearRect(0,0,500,500);
        if(selectSuccess){
            int releaseX = (int) (me.getX()/50)*50;
            int releaseY = (int) (me.getY()/50)*50;
            board.move(gc,selectedChecker,releaseX,releaseY);
        }
        if(board.redWins()){
            notifLabel.setStyle("-fx-text-fill: black");
            notifLabel.setText("Red Team Wins!!");
        }
        if(board.blueWins()){
            notifLabel.setStyle("-fx-text-fill: black");
            notifLabel.setText("Blue Team Wins!!");
        }
    }


    /**
     * A button press event handler that checks for common exceptions and adds a red Checker to the
     * Checker Board if the text field input criteria is met.
     *
     * @param e A redAddButton button press event
     */
    private void addRedCheckerHandler(ActionEvent e) {
        try {
            int xInput = board.xCoordConverter(xPosField.getText());     // Stores the user input X and Y coordinates
            int yInput = parseInt(yPosField.getText());
            // Checks if coordinates are within the range of the Checker Board grid
            if(xInput >= 1 && yInput >= 1 && xInput <= 8 && yInput <= 8) {
                // Checks if coordinates are on a grey square
                if(board.isLegallyPlaced(xInput,yInput)) {
                    xInput *= 50;       // Multiply by 50 to align with positioning in pixels
                    yInput *= 50;
                    board.addRedChecker(gc,xInput,yInput);      // Adds the Checker to the Checker Board at the chosen position
                    notifLabel.setStyle("-fx-text-fill: black");
                    notifLabel.setText("Red Checker Added at " + xPosField.getText().toUpperCase().strip().charAt(0) + yInput/50);
                }
                else {
                    notifLabel.setStyle("-fx-text-fill: red");
                    notifLabel.setText("Only Placement on Gray Squares is Allowed.");
                }
            }
            else {
                notifLabel.setStyle("-fx-text-fill: red");
                notifLabel.setText("That is Not in the Required Range.");
            }
            // If a non-integer value is input
        } catch (NumberFormatException | StringIndexOutOfBoundsException exception) {
            notifLabel.setStyle("-fx-text-fill: red");
            notifLabel.setText("Please Input a Letter for 'X' and a Number for 'Y' within Range.");
            // If a value is input in which a Checker already exists in that same position
        } catch (IllegalArgumentException exception) {
            notifLabel.setStyle("-fx-text-fill: red");
            notifLabel.setText("A Checker in that Position Already Exists.");
        }
    }


    /**
     * A button press event handler that checks for common exceptions and adds a blue Checker to the
     * Checker Board if the text field input criteria is met.
     *
     * @param e A blueAddButton button press event
     */
    private void addBlueCheckerHandler(ActionEvent e) {
        try {
            int xInput = board.xCoordConverter(xPosField.getText());     // Stores the user input X and Y coordinates
            int yInput = parseInt(yPosField.getText());
            // Checks if coordinates are within the range of the Checker Board grid
            if(xInput >= 1 && yInput >= 1 && xInput <= 8 && yInput <= 8) {
                // Checks if coordinates are on a grey square
                if(board.isLegallyPlaced(xInput,yInput)) {
                    xInput *= 50;       // Multiply by 50 to align with positioning in pixels
                    yInput *= 50;
                    board.addBlueChecker(gc,xInput,yInput);     // Adds the Checker to the Checker Board at the chosen position
                    notifLabel.setStyle("-fx-text-fill: black");
                    notifLabel.setText("Blue Checker Added at " + xPosField.getText().toUpperCase().strip().charAt(0) + yInput/50);
                }
                else {
                    notifLabel.setStyle("-fx-text-fill: red");
                    notifLabel.setText("Only Placement on Gray Squares is Allowed.");
                }
            }
            else {
                notifLabel.setStyle("-fx-text-fill: red");
                notifLabel.setText("That is Not in the Required Range.");
            }
            // If a non-integer value is input
        } catch (NumberFormatException | StringIndexOutOfBoundsException exception) {
            notifLabel.setStyle("-fx-text-fill: red");
            notifLabel.setText("Please Input a Letter for 'X' and a Number for 'Y' within Range.");
            // If a value is input in which a Checker already exists in that same position
        } catch (IllegalArgumentException exception) {
            notifLabel.setStyle("-fx-text-fill: red");
            notifLabel.setText("A Checker in that Position Already Exists.");
        }
    }


    /**
     * A button press event handler that checks to see if a Checker exists in the user specified position
     * on the Checker Board, then removes it if there is a Checker in that position.
     *
     * @param e A removeButton button press event
     */
    private void removeCheckerHandler(ActionEvent e) {
        try {
            int xInput = board.xCoordConverter(xPosField.getText())*50;      // Stores the user input X and Y coordinates
            int yInput = parseInt(yPosField.getText())*50;
            board.removeChecker(gc,xInput,yInput);  // Removes the Checker to the Checker Board at the chosen position
            notifLabel.setStyle("-fx-text-fill: black");
            notifLabel.setText("Checker Removed at " + xPosField.getText().toUpperCase().strip().charAt(0) + yInput/50);
            // If a non-integer value is input
        } catch (NumberFormatException | StringIndexOutOfBoundsException exception) {
            notifLabel.setStyle("-fx-text-fill: red");
            notifLabel.setText("Please Input a Letter for 'X' and a Number for 'Y' within Range.");
            // If a value is input in which there is nothing to remove
        } catch (IllegalArgumentException exception) {
            notifLabel.setStyle("-fx-text-fill: red");
            notifLabel.setText("There is Nothing in that Position.");
        }
    }


    private void resetHandler(ActionEvent e) {
        board.reset();
        board.draw(gc);
        notifLabel.setStyle("-fx-text-fill: black");
        notifLabel.setText("Game Board Reset!");
    }


    private void randomizeHandler(ActionEvent e) {
        board.randomize();
        board.draw(gc);
        notifLabel.setStyle("-fx-text-fill: black");
        notifLabel.setText("Game Board Randomized!");
    }


    /**
     * The start method, including the main components and the model.
     *
     * @param stage The main stage
     */
    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 500, 600);
        stage.setTitle("In-Progress Checkers Game Simulator!");
        stage.setScene(scene);

        // 1. Create the model
        board = CheckerBoard.create();
        Canvas canvas = new Canvas(500, 500);
        Canvas canvas2 = new Canvas(500, 500);

        // 2. Create the GUI components
        xPosField = new TextField();
        yPosField = new TextField();
        Button redAddButton = new Button("Add Red Checker");    // Buttons for adding and removing Checkers
        Button blueAddButton = new Button("Add Blue Checker");
        Button removeButton = new Button("Remove Checker");
        Button resetButton = new Button("Reset");
        Button randomizeButton = new Button("Randomize!");
        Label xyLabel = new Label("X\tY");                      // "X" and "Y" label
        notifLabel = new Label("Click-and-Drag to Move Checkers.");

        // 3. Add components to the root
        root.getChildren().addAll(canvas, canvas2, redAddButton, blueAddButton, removeButton, resetButton, randomizeButton, xPosField, yPosField, xyLabel, notifLabel);

        // 4. Configure the components (colors, fonts, size, location)
        xPosField.relocate(10,520);
        xPosField.setPrefColumnCount(2);
        yPosField.relocate(60,520);
        yPosField.setPrefColumnCount(2);
        redAddButton.relocate(110,505);
        blueAddButton.relocate(110,535);
        removeButton.relocate(230,520);
        resetButton.relocate(400,505);
        randomizeButton.relocate(400,535);
        xyLabel.relocate(30,500);
        xyLabel.setFont(new Font("System", 16));
        notifLabel.relocate(10,565);
        notifLabel.setFont(new Font("System", 16));
        gc = canvas.getGraphicsContext2D();
        gc2 = canvas2.getGraphicsContext2D();
        board.draw(gc);

        // 5. Add Event Handlers and do final setup
        canvas2.addEventHandler(MouseEvent.MOUSE_MOVED, this::moveHandler);
        canvas2.addEventHandler(MouseEvent.MOUSE_PRESSED, this::pressedHandler);
        canvas2.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::dragHandler);
        canvas2.addEventHandler(MouseEvent.MOUSE_RELEASED, this::releasedHandler);
        redAddButton.setOnAction(this :: addRedCheckerHandler);
        blueAddButton.setOnAction(this :: addBlueCheckerHandler);
        removeButton.setOnAction(this :: removeCheckerHandler);
        resetButton.setOnAction(this :: resetHandler);
        randomizeButton.setOnAction(this :: randomizeHandler);

        // 6. Show the stage
        stage.show();
    }


    /**
     * The main method used to launch the start method.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        launch(args);
    }
}
