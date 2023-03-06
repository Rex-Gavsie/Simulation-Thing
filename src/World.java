import java.util.ArrayList;
import java.util.Random;

public class World {
    public int cTime, sizeX, sizeY, numOfGenes, pop; // Every timestep, cTime++.
    public double mutationFactor; //Probability of a mutation (eg. 0.1 is 10%)
    public Cell[][] cellsInWorld; //2D Array List of Cells
    public String worldTitle;
    public int activationFunctionSelect;
    public Random worldRandom;
    public int nothingAtExceptionCount;
    public int worldHiddenNueronCount;

    public World(int inputX, int inputY, int startPopulation, String title, int inputGeneNum, int hiddenNueronInput) {
        //Verify legal inputs
        if (inputX*inputY < startPopulation) {
            throw new IllegalArgumentException("Population is greater than area");
        }

        cellsInWorld = new Cell[inputY][inputX];
        worldTitle = title;
        sizeX = inputX;
        sizeY = inputY;
        numOfGenes = inputGeneNum;
        pop = startPopulation;
        activationFunctionSelect = 0;
        worldRandom = new Random();
        nothingAtExceptionCount = 0;
        worldHiddenNueronCount = hiddenNueronInput;
        /*
         * Making cells based on the population number
         */
        
        for (int i = 1; i <= startPopulation; i++) {
            try {
                addCell(worldRandom.nextInt(1, inputX+1), worldRandom.nextInt(1,inputY+1), Genome.generateRandomGenome(numOfGenes));
            }
            catch(Exception IllegalArgumentException) {
                i--;
            }
        }
        //test
        //addCell(1, 1, Genome.generateRandomGenome(2));
        
    }

    public void addCell(int startX, int startY, ArrayList<String> inputGenome) {
        Cell newCell = new Cell(startX, startY, inputGenome, this, mutationFactor, worldHiddenNueronCount);
        if (nothingAtXY(newCell.getX(), newCell.getY())) {
            this.cellsInWorld[newCell.getY()-1][newCell.getX()-1] = newCell;
        }
        else {
            //System.out.println(startX + ", " + startY);
            //System.out.println(this.cellsInWorld[newCell.getY()-1][newCell.getX()-1]);
            throw new IllegalArgumentException("Already a cell there");
        }
    }

    public boolean nothingAtXY(int posX, int posY) {
        if (this.cellsInWorld[posY-1][posX-1] == null) {
            return true;
        }
        nothingAtExceptionCount++;
        return false;
    }

    public void moveCell(Cell cell, int xNew, int yNew) {
        if (nothingAtXY(xNew, yNew)) {
            int xOld = cell.getX();
            int yOld = cell.getY();
            this.cellsInWorld[yNew-1][xNew-1] = cell;
            if (nothingAtXY(xOld, yOld)) {
                throw new NullPointerException("AAAAAAAAAAAAAAAAA");
            }
            this.cellsInWorld[yOld-1][xOld-1] = null;
        }
        else {
            throw new IllegalArgumentException("There's already a cell there!");
        }
    }

    public void changeNewCellHiddenNuerons(int newNum) {
        worldHiddenNueronCount = newNum; 
    }

    public void worldTimestep() {
        Cell[][] savedCellsInWorld = cellsInWorld; //Ideally freeze the list of cells in the world
        for (int i = 0; i < savedCellsInWorld.length; i++) {
            for (int j = 0; j < savedCellsInWorld[i].length; i++) {
                savedCellsInWorld[i][j].cellTimestep();
            }
        }
    }

    public static void main(String[] args) {
        World thisWorld = new World(10, 10, 10, "Steve", 2, 2);
        //System.out.println(thisWorld.nothingAtExceptionCount);
        displayWorld.displayThisWorld(thisWorld);
        thisWorld.worldTimestep();
        displayWorld.displayThisWorld(thisWorld);
        thisWorld.worldTimestep();
        displayWorld.displayThisWorld(thisWorld);
        /*for (int row = 0; row < thisWorld.cellsInWorld.length; row++) {
            int numInRow=0;
            for (int col = 0; col < thisWorld.cellsInWorld[row].length; col++) {
                if (thisWorld.cellsInWorld[row][col] != null) {
                    numInRow++;
                }
            }
            System.out.println("Row: " + row + " | Cells: " + numInRow);
        }*/

    }
}