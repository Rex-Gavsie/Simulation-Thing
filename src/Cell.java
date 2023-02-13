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
        cellBrain = new Brain(cellGenome.getGenes());
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    

    /**
     *                  --Inputs--
     * Open Space Above (Vision) @param aboveSightDist
     * Open Space Left (Vision) @param leftSightDist
     * Open Space Right (Vision) @param rightSightDist
     * Open Space Below (Vision) @param belowSightDist
     *  
     * Internal Clock (Time) @param cellTimeInternal
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