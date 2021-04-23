import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A Blue Checker class that draws a blue Checker and promotes it to a king if it's in the bottom most position.
 * Mohawk College, COMP 10062 - Programming in Java. August 16, 2020.
 *
 * @author Robert Zaranek (001161598)
 */
public class BlueChecker extends Checker{

    /** Holds the color for this Checker object **/
    private final Color color = Color.CORNFLOWERBLUE;

    /**
     * Draws a BlueChecker object at the specified position on the Checker Board, and gets promoted to
     * a king if at the bottom most position on the board.
     *
     * @param xPos An X-coordinate in the context of the Checker Board
     * @param yPos A Y-coordinate in the context of the Checker Board
     */
    public BlueChecker(int xPos, int yPos) {
        super(xPos, yPos);
        if(yPos == 400){
            promote();
        }
    }

    /**
     * Draws the BlueChecker object at its set position on the Checker Board.
     *
     * @param gc A GraphicsContext object
     */
    public void draw(GraphicsContext gc) {
        super.draw(gc, color);
    }

    /**
     * Draws the BlueChecker object at the specified position.
     *
     * @param gc A GraphicsContext object
     * @param x An X-coordinate derived from the cursor position
     * @param y An Y-coordinate derived from the cursor position
     */
    public void drawAtPos(GraphicsContext gc, double x, double y) {
        super.drawAtPos(gc,color,x,y);
    }
}
