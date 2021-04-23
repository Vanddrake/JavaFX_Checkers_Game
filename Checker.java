import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * An abstract Checker class that draws a Checker and holds common values for use in subclasses.
 * Mohawk College, COMP 10062 - Programming in Java. August 16, 2020.
 *
 * @author Robert Zaranek (001161598)
 */
public abstract class Checker {

    /** Holds the Checker's current X-coordinate in the context of the Checker Board **/
    private int xPos;
    /** Holds the Checker's current Y-coordinate in the context of the Checker Board **/
    private int yPos;
    /** If this Checker is a king or not **/
    private boolean isKing = false;

    /**
     * A constructor that creates a Checker object and sets its X and Y coordinates.
     *
     * @param xPos An X-coordinate in the context of the Checker Board
     * @param yPos A Y-coordinate in the context of the Checker Board
     */
    public Checker(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    /**
     * A method that returns this Checker object's X coordinate.
     *
     * @return This Checker object's X coordinate.
     */
    public int getxPos() {
        return xPos;
    }

    /**
     * A method that returns this Checker object's Y coordinate.
     *
     * @return This Checker object's Y coordinate.
     */
    public int getyPos() {
        return yPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    /**
     * Draws the Checker object at its set position on the Checker Board.
     *
     * @param gc A GraphicsContext object
     * @param color A color taken from a subclass' color
     */
    public void draw(GraphicsContext gc, Color color) {
        gc.setFill(color);
        gc.fillOval(xPos+5,yPos+5,40,40);
        gc.setLineWidth(5);
        gc.setStroke(Color.BLACK);
        gc.strokeOval(xPos+5,yPos+5,40,40);
        // Draws a crown on the Checker if it's a king
        if(isKing()){
            gc.setFill(Color.BLACK);
            gc.fillPolygon(new double[]{getxPos()+15, getxPos()+15, getxPos()+35,getxPos()+35,getxPos()+30,getxPos()+25,getxPos()+20},
                    new double[]{getyPos()+15,getyPos()+30,getyPos()+30,getyPos()+15,getyPos()+25,getyPos()+15,getyPos()+25}, 7);
        }
    }

    /**
     * Draws the Checker object at the specified position.
     *
     * @param gc A GraphicsContext object
     * @param color A color taken from a subclass' color
     * @param x An X-coordinate derived from the cursor position
     * @param y An Y-coordinate derived from the cursor position
     */
    public void drawAtPos(GraphicsContext gc, Color color, double x, double y){
        gc.setFill(color);
        gc.fillOval(x+5,y+5,40,40);
        gc.setLineWidth(5);
        gc.setStroke(Color.BLACK);
        gc.strokeOval(x+5,y+5,40,40);
        // Draws a crown on the Checker if it's a king
        if(isKing()){
            gc.setFill(Color.BLACK);
            gc.fillPolygon(new double[]{x+15, x+15, x+35,x+35,x+30,x+25,x+20},
                    new double[]{y+15,y+30,y+30,y+15,y+25,y+15,y+25}, 7);
        }
    }

    /**
     * A method that makes this Checker object a king.
     */
    public void promote() {
        isKing = true;
    }

    /**
     * A method that returns a boolean value to determine if this Checker object is a king.
     *
     * @return If this Checker object is a king
     */
    public boolean isKing() {
        return isKing;
    }
}
