import java.util.ArrayList;

/**
 * A class used by the CheckerBoard class to locate legal positions that a Checker object
 * is able to move to.
 *
 * August 23, 2020.
 *
 * @author Robert Zaranek
 */
public class LegalMoveCheck {

    /** X and Y coordinates of the position of where this Checker object is able to move to. **/
    private int moveXPos, moveYPos;

    private boolean notBlocked = true;

    /**
     * An empty constructor for the LegalMoveCheck class.
     */
    public LegalMoveCheck() {
    }

    public int getMoveXPos() {
        return moveXPos;
    }

    public int getMoveYPos() {
        return moveYPos;
    }

    public boolean isNotBlocked() {
        return notBlocked;
    }

    public void moveUpperLeft(ArrayList<Checker> checkerList, Checker checker, int releaseX, int releaseY){
        notBlocked = true;
        boolean notMoved = true;
        if(checker.getxPos() == 50 || checker.getyPos() == 50){
            notBlocked = false;
            notMoved = false;
        }
        if(notMoved){
            for(Checker c1 : checkerList){
                if(checker.getxPos()-50 == c1.getxPos() && checker.getyPos()-50 == c1.getyPos()){
                    if(checker instanceof RedChecker && c1 instanceof RedChecker){
                        notBlocked = false;
                        notMoved = false;
                        break;
                    }
                    if(checker instanceof BlueChecker && c1 instanceof BlueChecker){
                        notBlocked = false;
                        notMoved = false;
                        break;
                    }
                    if(checker.getxPos() == 100 || checker.getyPos() == 100){
                        notBlocked = false;
                        notMoved = false;
                        break;
                    }
                    for(Checker c2 : checkerList) {
                        if(checker.getxPos()-100 == c2.getxPos() && checker.getyPos()-100 == c2.getyPos()) {
                            notBlocked = false;
                            notMoved = false;
                            break;
                        }
                    }
                    if(notMoved){
                        moveXPos = checker.getxPos()-100;
                        moveYPos = checker.getyPos()-100;
                        notMoved = false;
                        if(moveXPos == releaseX && moveYPos == releaseY) checkerList.remove(c1);
                        break;
                    }
                }
            }
        }
        if(notMoved){
            moveXPos = checker.getxPos()-50;
            moveYPos = checker.getyPos()-50;
        }
    }

    public void moveUpperRight(ArrayList<Checker> checkerList, Checker checker, int releaseX, int releaseY){
        notBlocked = true;
        boolean notMoved = true;
        if(checker.getxPos() == 400 || checker.getyPos() == 50){
            notBlocked = false;
            notMoved = false;
        }
        if(notMoved){
            for(Checker c1 : checkerList){
                if(checker.getxPos()+50 == c1.getxPos() && checker.getyPos()-50 == c1.getyPos()){
                    if(checker instanceof RedChecker && c1 instanceof RedChecker){
                        notBlocked = false;
                        notMoved = false;
                        break;
                    }
                    if(checker instanceof BlueChecker && c1 instanceof BlueChecker){
                        notBlocked = false;
                        notMoved = false;
                        break;
                    }
                    if(checker.getxPos() == 350 || checker.getyPos() == 100){
                        notBlocked = false;
                        notMoved = false;
                        break;
                    }
                    for(Checker c2 : checkerList) {
                        if(checker.getxPos()+100 == c2.getxPos() && checker.getyPos()-100 == c2.getyPos()) {
                            notBlocked = false;
                            notMoved = false;
                            break;
                        }
                    }
                    if(notMoved){
                        moveXPos = checker.getxPos()+100;
                        moveYPos = checker.getyPos()-100;
                        notMoved = false;
                        if(moveXPos == releaseX && moveYPos == releaseY) checkerList.remove(c1);
                        break;
                    }
                }
            }
        }
        if(notMoved){
            moveXPos = checker.getxPos()+50;
            moveYPos = checker.getyPos()-50;
        }
    }

    public void moveLowerLeft(ArrayList<Checker> checkerList, Checker checker, int releaseX, int releaseY){
        notBlocked = true;
        boolean notMoved = true;
        if(checker.getxPos() == 50 || checker.getyPos() == 400){
            notBlocked = false;
            notMoved = false;
        }
        if(notMoved){
            for(Checker c1 : checkerList){
                if(checker.getxPos()-50 == c1.getxPos() && checker.getyPos()+50 == c1.getyPos()){
                    if(checker instanceof RedChecker && c1 instanceof RedChecker){
                        notBlocked = false;
                        notMoved = false;
                        break;
                    }
                    if(checker instanceof BlueChecker && c1 instanceof BlueChecker){
                        notBlocked = false;
                        notMoved = false;
                        break;
                    }
                    if(checker.getxPos() == 100 || checker.getyPos() == 350){
                        notBlocked = false;
                        notMoved = false;
                        break;
                    }
                    for(Checker c2 : checkerList) {
                        if(checker.getxPos()-100 == c2.getxPos() && checker.getyPos()+100 == c2.getyPos()) {
                            notBlocked = false;
                            notMoved = false;
                            break;
                        }
                    }
                    if(notMoved){
                        moveXPos = checker.getxPos()-100;
                        moveYPos = checker.getyPos()+100;
                        notMoved = false;
                        if(moveXPos == releaseX && moveYPos == releaseY) checkerList.remove(c1);
                        break;
                    }
                }
            }
        }
        if(notMoved){
            moveXPos = checker.getxPos()-50;
            moveYPos = checker.getyPos()+50;
        }
    }

    public void moveLowerRight(ArrayList<Checker> checkerList, Checker checker, int releaseX, int releaseY){
        notBlocked = true;
        boolean notMoved = true;
        if(checker.getxPos() == 400 || checker.getyPos() == 400){
            notBlocked = false;
            notMoved = false;
        }
        if(notMoved){
            for(Checker c1 : checkerList){
                if(checker.getxPos()+50 == c1.getxPos() && checker.getyPos()+50 == c1.getyPos()){
                    if(checker instanceof RedChecker && c1 instanceof RedChecker){
                        notBlocked = false;
                        notMoved = false;
                        break;
                    }
                    if(checker instanceof BlueChecker && c1 instanceof BlueChecker){
                        notBlocked = false;
                        notMoved = false;
                        break;
                    }
                    if(checker.getxPos() == 350 || checker.getyPos() == 350){
                        notBlocked = false;
                        notMoved = false;
                        break;
                    }
                    for(Checker c2 : checkerList) {
                        if(checker.getxPos()+100 == c2.getxPos() && checker.getyPos()+100 == c2.getyPos()) {
                            notBlocked = false;
                            notMoved = false;
                            break;
                        }
                    }
                    if(notMoved){
                        moveXPos = checker.getxPos()+100;
                        moveYPos = checker.getyPos()+100;
                        notMoved = false;
                        if(moveXPos == releaseX && moveYPos == releaseY) checkerList.remove(c1);
                        break;
                    }
                }
            }
        }
        if(notMoved){
            moveXPos = checker.getxPos()+50;
            moveYPos = checker.getyPos()+50;
        }
    }
}
