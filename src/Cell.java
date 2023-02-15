import java.util.ArrayList;

public class Cell {
    /**
    * Ints Stored: Position in X and Y, birthTime (to calculate age), etc.
    * Genome class to handle more complex genome stuff later on
    * Brain class (initialized after genome class and is then just past the info from the genome class)
     */

    private int posX, posY, birthTime;
    private Genome cellGenome;
    private Brain cellBrain;
    private World cellWorld;

    public Cell(int startX, int startY, ArrayList<String> inputGenome, World inputWorld, double inputMutationF) {
        posX = startX;
        posY = startY;
        cellWorld = inputWorld;
        birthTime = cellWorld.cTime;
        cellGenome = new Genome(inputGenome, inputMutationF);
        cellBrain = new Brain(this, cellGenome.genes);
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    /** ------------SENSORS-------------------*/
    /* Movement Sensors */

    public int aboveSightDist() {
        int distanceAbove = 0;
        for (int i=posY-1; i>0; i--) {
            if (cellWorld.nothingAtXY(posX, i)) {
                distanceAbove++;
            }
            else {
                return distanceAbove;
            }
        }
        return distanceAbove;
    }

    public int belowSightDist() {
        int distanceBelow = 0;
        for (int i=posY+1; i<=cellWorld.sizeY; i++) {
            if (cellWorld.nothingAtXY(posX, i)) {
                distanceBelow++;
            }
            else {
                return distanceBelow;
            }
        }
        return distanceBelow;
    }

    public int leftSightDist() {
        int distanceLeft = 0;
        for (int i=posX-1; i>0; i--) {
            if (cellWorld.nothingAtXY(i, posY)) {
                distanceLeft++;
            }
            else {
                return distanceLeft;
            }
        }
        return distanceLeft;
    }

    public int rightSightDist() {
        int distanceRight = 0;
        for (int i=posX+1; i<=cellWorld.sizeX; i--) {
            if (cellWorld.nothingAtXY(i, posY)) {
                distanceRight++;
            }
            else {
                return distanceRight;
            }
        }
        return distanceRight;
    }

    /* Time Sensors */

    public int cellTimeExternal() {
        return cellWorld.cTime;
    }
    
    public int cellTimeInternal() {
        return (cellWorld.cTime-birthTime);
    }

    /* Border Distance Sensors */

    public int leftBorderDist() {
        return(posX-1);
    }
    
    public int rightBorderDist() {
        return(cellWorld.sizeX-posX);
    }

    public int topBorderDist() {
        return(posY-1);
    }

    public int botBorderDist() {
        return(cellWorld.sizeY-posY);
    }

    /** ------------OUTPUTS------------------- */
    /* Movement */

    public void moveUp() {
        try {
            cellWorld.moveCell(this, posX, posY-1);
        }
        catch(Exception IllegalArgumentException) {
            throw IllegalArgumentException;
        }
        this.posY=posY-1;
    }

    public void moveDown() {
        try {
            cellWorld.moveCell(this, posX, posY+1);
        }
        catch(Exception IllegalArgumentException) {
            throw IllegalArgumentException;
        }
        this.posY=posY+1;
    }

    public void moveLeft() {
        try {
            cellWorld.moveCell(this, posX-1, posY);
        }
        catch(Exception IllegalArgumentException) {
            throw IllegalArgumentException;
        }
        this.posY=posX-1;
    }

    public void moveRight() {
        try {
            cellWorld.moveCell(this, posX+1, posY);
        }
        catch(Exception IllegalArgumentException) {
            throw IllegalArgumentException;
        }
        this.posY=posY+1;
    }

    /**
     *                  --Inputs--
     * Open Space Above (Vision) @param aboveSightDist IMPLEMENTED
     * Open Space Left (Vision) @param leftSightDist IMPLEMENTED
     * Open Scpace Right (Vision) @param rightSightDist IMPLEMENTED
     * Open Space Below (Vision) @param belowSightDist IMPLEMENTED
     *  
     * Age (Time) @param cellTimeInternal
     * External Clock (Time) @param cTimeExternal
     * 
     * Distance to Left Border (Spatial) @param leftBorderDist
     * Distance to Right Border (Spatial) @param rightBorderDist
     * Distance to Top Border (Spatial) @param topBorderDist
     * Distance to Bottom Border (Spatial) @param botBorderDist
     * 
     *                  --Outputs--
     * 
     * Move Up (Movement) @param moveUp
     * Move Down (Movement) @param moveDown
     * Move Left (Movement) @param moveLeft
     * Move Right (Movement) @param moveRight 
     * 
     * Change Internal Clock Speed (Time) @param cellInternalClockTimeScale
            * If +, speed up, if -, slow down
     * 
     * 
     *              --LATER DOWN THE ROAD--
     * Object Type addition to Vision
        * Cell, Plant, etc. 
     * Genome Similarity addition to Vision
     * Energy
     */
}