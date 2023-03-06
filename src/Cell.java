import java.util.ArrayList;

public class Cell {
    /**
    * Ints Stored: Position in X and Y, birthTime (to calculate age), etc.
    * Genome class to handle more complex genome stuff later on
    * Brain class (initialized after genome class and is then just past the info from the genome class)
     */

    private int posX, posY, birthTime;
    public int numInputSensors, numOutputs, numHiddenNuerons;
    private Genome cellGenome;
    private Brain cellBrain;
    public World cellWorld;

    public Cell(int startX, int startY, ArrayList<String> inputGenome, World inputWorld, double inputMutationF, int inputHidden) {
        posX = startX;
        posY = startY;
        cellWorld = inputWorld;
        birthTime = cellWorld.cTime;
        cellGenome = new Genome(this, inputGenome, inputMutationF);
        cellBrain = new Brain(this, cellGenome);
        numInputSensors = 10; //MANUALLY SET
        numHiddenNuerons = inputHidden;
        numOutputs = 4; //MANUALLY SET
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    /** ------------SENSORS-------------------*/
    /* Movement Sensors */

    private int aboveSightDist() {
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

    private int belowSightDist() {
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

    private int leftSightDist() {
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

    private int rightSightDist() {
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

    private int cellTimeExternal() {
        return cellWorld.cTime;
    }
    
    private int cellTimeInternal() { // Age kinda, maybe I change how this one works later?
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

    /**
     * 
     * @param sensorIndexInput
     * <p> 0 - Above Sight Distance
     * <p> 1 - Below Sight Distance
     * <p> 2 - Left Sight Distance
     * <p> 3 - Right Sight Distance
     * <p> 4 - External Time
     * <p> 5 - Internal 
     * <p> 6 - Top Border Distance
     * <p> 7 - Bottom Border Distance
     * <p> 8 - Left Border Distance
     * <p> 9 - Right Border Distance
     * @return Sensor Output
     */
    public int callSensor(int sensorIndexInput) {
        switch (sensorIndexInput) {
            case 0:
                return aboveSightDist();
            case 1:
                return belowSightDist();
            case 2:
                return leftSightDist();
            case 3:
                return rightSightDist();
            case 4:
                return cellTimeExternal();
            case 5:
                return cellTimeInternal();
            case 6:
                return topBorderDist();
            case 7:
                return botBorderDist();
            case 8:
                return leftBorderDist();
            case 9:
                return rightBorderDist();
            default:
                throw new IndexOutOfBoundsException("Not a valid sensor");
        }
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
     * 
     * @param outputIndex
     * <p> 0 - Move up
     * <p> 1 - Move Down
     * <p> 2 - Move Left
     * <p> 3 - Move Right
     */
    public void doOutput(int outputIndex) {
        switch (outputIndex) {
            case 0:
                moveUp();
                break;
            case 1:
                moveDown();
                break;
            case 2:
                moveLeft();
                break;
            case 3: 
                moveRight();
                break;
            default:
                throw new IndexOutOfBoundsException("Not a valid output");
        }
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