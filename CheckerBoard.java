import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Random;

/**
 * A Checker Board class that creates a randomized array list of Checkers and includes draw methods for the
 * game board.
 * August 16, 2020.
 *
 * @author Robert Zaranek
 */
public class CheckerBoard {

    /** A variable that allows for only one instance of CheckBoard to be created **/
    private static CheckerBoard singleInstance = null;
    /** An array list of Checker objects **/
    private final ArrayList<Checker> checkers;

    private final LegalMoveCheck legalMoveCheck;


    /**
     * A constructor that creates a CheckerBoard object and generates a random array list of Checker objects.
     */
    private CheckerBoard() {
        checkers = new ArrayList<>();       // initializes the array list
        legalMoveCheck = new LegalMoveCheck();
        randomize();
    }


    /**
     * Creates an instance of CheckerBoard and allows for the creation of only one instance.
     *
     * @return An instance of CheckerBoard
     */
    public static CheckerBoard create(){
        if (singleInstance == null) singleInstance = new CheckerBoard();
        return singleInstance;
    }


    /**
     * Draws a Checker Board complete with Checkers on a GraphicsContext object.
     *
     * @param gc A GraphicsContext object
     */
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BURLYWOOD);
        gc.fillRect(0, 0, 500, 500);
        // Goes down the rows of the Checker Board grid
        for(int column=1;column<=4;column++) {
            // Draws eight squares in the first row and every alternating row thereafter
            for(int evenRow=1;evenRow<=4; evenRow++) {
                gc.setFill(Color.SLATEGRAY);
                gc.fillRect((100*evenRow)-50, (100*column)-50, 50, 50);
            }
            // Draws eight squares in the second row and every alternating row thereafter
            for(int oddRow=1;oddRow<=4; oddRow++) {
                gc.setFill(Color.SLATEGRAY);
                gc.fillRect(100*oddRow, 100*column, 50, 50);
            }
        }
        // Draws numbers on the sides to indicate X and Y coordinates
        gc.setStroke(Color.BLACK);
        gc.setFont(Font.font("System", 19));
        gc.setLineWidth(2);
        gc.strokeText("1\n\n2\n\n3\n\n4\n\n5\n\n6\n\n7\n\n8", 470, 80);
        gc.strokeText("A       B       C        D       E        F        G       H", 70, 30);
        // Draws the appropriate Checker on the Checker Board
        for(Checker checker : checkers){
            if(checker instanceof RedChecker) ((RedChecker) checker).draw(gc);
            else ((BlueChecker) checker).draw(gc);
        }
    }


    public void reset(){
        checkers.clear();
        for(int c=0; c<4; c++){
            checkers.add(new BlueChecker((c*100)+50,50));
        }
        for(int c=0; c<4; c++){
            checkers.add(new BlueChecker((c*100)+100,100));
        }
        for(int c=0; c<4; c++){
            checkers.add(new BlueChecker((c*100)+50,150));
        }
        for(int c=0; c<4; c++){
            checkers.add(new RedChecker((c*100)+100,300));
        }
        for(int c=0; c<4; c++){
            checkers.add(new RedChecker((c*100)+50,350));
        }
        for(int c=0; c<4; c++){
            checkers.add(new RedChecker((c*100)+100,400));
        }
    }


    public void randomize(){
        checkers.clear();
        Random random = new Random();       // Stores a random object
        int startingCheckers = random.nextInt(10)+5;    // Stores a random 5-15 integer
        // Creates 5-15 Checker objects and adds them to the array list
        for(int c=0; c<startingCheckers; c++){
            boolean isRed =  random.nextBoolean();      // Boolean for either a blue or red Checker
            // Loops attempting to create a Checker in a unique position until one is created
            while(true){
                boolean displace = random.nextBoolean();    // 50% chance to move a Checker up and left one space
                // Chooses a random position for a Checker over half of the legal possible positions on the Checker Board
                int xPos = (random.nextInt(4)+1)*100;
                int yPos = (random.nextInt(4)+1)*100;
                // Moves a Checker up and left one space
                if(displace){
                    xPos -= 50;
                    yPos -= 50;
                }
                boolean same = false;   // Stores whether the current Checker is in the same position as another Checker
                // Creates a red Checker if not in the same position as another Checker
                if(isRed){
                    for(Checker checker : checkers) {
                        if (checker.getxPos() == xPos && checker.getyPos() == yPos) {
                            same = true;
                            break;
                        }
                    }
                    if(!same) {
                        checkers.add(new RedChecker(xPos,yPos));
                        break;
                    }
                }
                // Creates a blue Checker if not in the same position as another Checker
                else {
                    for(Checker checker : checkers) {
                        if (checker.getxPos() == xPos && checker.getyPos() == yPos) {
                            same = true;
                            break;
                        }
                    }
                    if(!same) {
                        checkers.add(new BlueChecker(xPos,yPos));
                        break;
                    }
                }
            }
        }
    }


    public boolean redWins(){
        for(Checker c : checkers){
            if(c instanceof BlueChecker) return false;
        }
        return true;
    }


    public boolean blueWins(){
        for(Checker c : checkers){
            if(c instanceof RedChecker) return false;
        }
        return true;
    }


    public int xCoordConverter(String input) {
        char xCoord = input.toLowerCase().strip().charAt(0);
        if(xCoord == 'a') return 1;
        else if(xCoord == 'b') return 2;
        else if(xCoord == 'c') return 3;
        else if(xCoord == 'd') return 4;
        else if(xCoord == 'e') return 5;
        else if(xCoord == 'f') return 6;
        else if(xCoord == 'g') return 7;
        else if(xCoord == 'h') return 8;
        else throw new NumberFormatException("Not a value in the required range.");
    }





    /**
     * A method that calculates and returns a boolean of if the coordinates are in a legal space.
     *
     * @param x An X-coordinate in the context of the Checker Board
     * @param y A Y-coordinate in the context of the Checker Board
     * @return A boolean of if the coordinates are in a legal space
     */
    public boolean isLegallyPlaced(int x, int y) {
        return (x + y) % 2 == 0;
    }


    /**
     * A method that checks if the input space is currently occupied by another Checker on the Checker Board,
     * then adds a RedChecker object to that space if not occupied. Then draws to update the Checker Board.
     *
     * @param gc A GraphicsContext object
     * @param x An X-coordinate in the context of the Checker Board
     * @param y A Y-coordinate in the context of the Checker Board
     */
    public void addRedChecker(GraphicsContext gc, int x, int y) {
        // Throws an exception if a Checker exists in this position
        for(Checker checker : checkers) {
            if(x == checker.getxPos() && y == checker.getyPos()) {
                throw new IllegalArgumentException("A checker in that position already exists.");
            }
        }
        checkers.add(new RedChecker(x,y));
        draw(gc);
    }


    /**
     * A method that checks if the input space is currently occupied by another Checker on the Checker Board,
     * then adds a BlueChecker object to that space if not occupied. Then draws to update the Checker Board.
     *
     * @param gc A GraphicsContext object
     * @param x An X-coordinate in the context of the Checker Board
     * @param y A Y-coordinate in the context of the Checker Board
     */
    public void addBlueChecker(GraphicsContext gc, int x, int y) {
        // Throws an exception if a Checker exists in this position
        for(Checker checker : checkers) {
            if(x == checker.getxPos() && y == checker.getyPos()) {
                throw new IllegalArgumentException("A checker in that position already exists.");
            }
        }
        checkers.add(new BlueChecker(x,y));
        draw(gc);
    }


    /**
     * A method that checks if the input space is currently occupied by another Checker on the Checker Board,
     * then removes a Checker object from that space if it is occupied. Then draws to update the Checker Board.
     *
     * @param gc A GraphicsContext object
     * @param x An X-coordinate in the context of the Checker Board
     * @param y A Y-coordinate in the context of the Checker Board
     */
    public void removeChecker(GraphicsContext gc, int x, int y) {
        boolean nothingRemoved = true;
        // Throws an exception if there is no Checker in this position
        for(int c=0; c<checkers.size(); c++) {
            if(x == checkers.get(c).getxPos() && y == checkers.get(c).getyPos()) {
                checkers.remove(c);
                nothingRemoved = false;
                draw(gc);
                break;
            }
        }
        if(nothingRemoved){
            throw new IllegalArgumentException("Nothing to remove");
        }
    }


    /**
     * Uses the pythagorean theorem to calculate the current closest Checker to the input X and Y coordinates.
     *
     * @param mouseX An X-coordinate derived from the cursor position
     * @param mouseY An Y-coordinate derived from the cursor position
     * @return The current closest Checker object to the X and Y coordinates given
     */
    public Checker selectChecker(double mouseX, double mouseY){
        double closest = 1000;      // Stores the current closest position
        Checker closestChecker = checkers.get(0);       // Stores the current closest Checker object
        // Loops through the list of Checkers to find the closest Checker 
        for(Checker checker : checkers){
            // Calculates the distance from the cursor to the current Checker object in the loop
            double distance = Math.sqrt((mouseX - (checker.getxPos()+25)) * (mouseX - (checker.getxPos()+25)) + (mouseY - (checker.getyPos()+25)) * (mouseY - (checker.getyPos()+25)));
            // The current closest Checker object is stored
            if(distance < closest){
                closest = distance;
                closestChecker = checker;
            }
        }
        return closestChecker;
    }


    public void move(GraphicsContext gc, Checker checker, int releaseX, int releaseY) {
        if(checker.isKing()){
            legalMoveCheck.moveUpperLeft(checkers,checker,releaseX,releaseY);
            if(legalMoveCheck.isNotBlocked()){
                if(legalMoveCheck.getMoveXPos() == releaseX && legalMoveCheck.getMoveYPos() == releaseY){
                    checker.setxPos(releaseX);
                    checker.setyPos(releaseY);
                    draw(gc);
                }
            }

            legalMoveCheck.moveUpperRight(checkers,checker,releaseX,releaseY);
            if(legalMoveCheck.isNotBlocked()){
                if(legalMoveCheck.getMoveXPos() == releaseX && legalMoveCheck.getMoveYPos() == releaseY){
                    checker.setxPos(releaseX);
                    checker.setyPos(releaseY);
                    draw(gc);
                }
            }

            legalMoveCheck.moveLowerLeft(checkers,checker,releaseX,releaseY);
            if(legalMoveCheck.isNotBlocked()){
                if(legalMoveCheck.getMoveXPos() == releaseX && legalMoveCheck.getMoveYPos() == releaseY){
                    checker.setxPos(releaseX);
                    checker.setyPos(releaseY);
                    draw(gc);
                }
            }

            legalMoveCheck.moveLowerRight(checkers,checker,releaseX,releaseY);
            if(legalMoveCheck.isNotBlocked()){
                if(legalMoveCheck.getMoveXPos() == releaseX && legalMoveCheck.getMoveYPos() == releaseY){
                    checker.setxPos(releaseX);
                    checker.setyPos(releaseY);
                    draw(gc);
                }
            }
        }

        else if(checker instanceof RedChecker){

            legalMoveCheck.moveUpperLeft(checkers,checker,releaseX,releaseY);
            if(legalMoveCheck.isNotBlocked()){
                if(legalMoveCheck.getMoveXPos() == releaseX && legalMoveCheck.getMoveYPos() == releaseY){
                    checker.setxPos(releaseX);
                    checker.setyPos(releaseY);
                    if(checker.getyPos() == 50) checker.promote();
                    draw(gc);
                }
            }

            legalMoveCheck.moveUpperRight(checkers,checker,releaseX,releaseY);
            if(legalMoveCheck.isNotBlocked()){
                if(legalMoveCheck.getMoveXPos() == releaseX && legalMoveCheck.getMoveYPos() == releaseY){
                    checker.setxPos(releaseX);
                    checker.setyPos(releaseY);
                    if(checker.getyPos() == 50) checker.promote();
                    draw(gc);
                }
            }
        }

        else if(checker instanceof BlueChecker){

            legalMoveCheck.moveLowerLeft(checkers,checker,releaseX,releaseY);
            if(legalMoveCheck.isNotBlocked()){
                if(legalMoveCheck.getMoveXPos() == releaseX && legalMoveCheck.getMoveYPos() == releaseY){
                    checker.setxPos(releaseX);
                    checker.setyPos(releaseY);
                    if(checker.getyPos() == 400) checker.promote();
                    draw(gc);
                }
            }

            legalMoveCheck.moveLowerRight(checkers,checker,releaseX,releaseY);
            if(legalMoveCheck.isNotBlocked()){
                if(legalMoveCheck.getMoveXPos() == releaseX && legalMoveCheck.getMoveYPos() == releaseY){
                    checker.setxPos(releaseX);
                    checker.setyPos(releaseY);
                    if(checker.getyPos() == 400) checker.promote();
                    draw(gc);
                }
            }
        }
    }


    public void drawLegalMoves(GraphicsContext gc, Checker checker){
        if(checker.isKing()){
            gc.setFill(Color.DARKGOLDENROD);

            legalMoveCheck.moveUpperLeft(checkers,checker,0,0);
            if(legalMoveCheck.isNotBlocked()){
                gc.fillRect(legalMoveCheck.getMoveXPos(),legalMoveCheck.getMoveYPos(),50,50);
            }

            legalMoveCheck.moveUpperRight(checkers,checker,0,0);
            if(legalMoveCheck.isNotBlocked()){
                gc.fillRect(legalMoveCheck.getMoveXPos(),legalMoveCheck.getMoveYPos(),50,50);
            }

            legalMoveCheck.moveLowerLeft(checkers,checker,0,0);
            if(legalMoveCheck.isNotBlocked()){
                gc.fillRect(legalMoveCheck.getMoveXPos(),legalMoveCheck.getMoveYPos(),50,50);
            }

            legalMoveCheck.moveLowerRight(checkers,checker,0,0);
            if(legalMoveCheck.isNotBlocked()){
                gc.fillRect(legalMoveCheck.getMoveXPos(),legalMoveCheck.getMoveYPos(),50,50);
            }
        }

        else if(checker instanceof RedChecker){
            gc.setFill(Color.DARKGOLDENROD);

            legalMoveCheck.moveUpperLeft(checkers,checker,0,0);
            if(legalMoveCheck.isNotBlocked()){
                gc.fillRect(legalMoveCheck.getMoveXPos(),legalMoveCheck.getMoveYPos(),50,50);
            }

            legalMoveCheck.moveUpperRight(checkers,checker,0,0);
            if(legalMoveCheck.isNotBlocked()){
                gc.fillRect(legalMoveCheck.getMoveXPos(),legalMoveCheck.getMoveYPos(),50,50);
            }
        }

        else if(checker instanceof BlueChecker){
            gc.setFill(Color.DARKGOLDENROD);

            legalMoveCheck.moveLowerLeft(checkers,checker,0,0);
            if(legalMoveCheck.isNotBlocked()){
                gc.fillRect(legalMoveCheck.getMoveXPos(),legalMoveCheck.getMoveYPos(),50,50);
            }

            legalMoveCheck.moveLowerRight(checkers,checker,0,0);
            if(legalMoveCheck.isNotBlocked()){
                gc.fillRect(legalMoveCheck.getMoveXPos(),legalMoveCheck.getMoveYPos(),50,50);
            }

        }

    }

}
