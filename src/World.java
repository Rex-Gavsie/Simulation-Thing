import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;

public class World {
    public int cTime, sizeX, sizeY, numOfGenes, pop; // Every timestep, cTime++.
    public double mutationFactor; //Probability of a mutation (eg. 0.1 is 10%)
    public Cell[][] cellsInWorld; //2D Array List of Cells
    public String worldTitle;
    public int activationFunctionSelect;
    public Random worldRandom;
    public int nothingAtExceptionCount;
    public int worldHiddenNueronCount;
    private ArrayList<int[][]> winZoneList; //Stores locations of win condition zones in the form of [[x1, y1], [x2, y2]]
    private ArrayList<ArrayList<String>> successfulCellList = new ArrayList<ArrayList<String>>();

    public World() {
        sizeX = 0;
        sizeY = 0;
        cTime = 0;
        worldTitle = "";
        numOfGenes = 0;
        mutationFactor = 0;
        pop = 0;
        activationFunctionSelect = 0;
        worldRandom = new Random();
        nothingAtExceptionCount = 0;
        worldHiddenNueronCount = 0;
        winZoneList = new ArrayList<>();
    }

    public World(int inputX, int inputY, int startPopulation, String title, int inputGeneNum, double mutationFactor, int hiddenNueronInput) {
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
        this.mutationFactor = mutationFactor;
        worldHiddenNueronCount = hiddenNueronInput;
        winZoneList = new ArrayList<>();
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
        cTime++;
        Cell[][] savedCellsInWorld = cellsInWorld; //Ideally freeze the list of cells in the world
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (savedCellsInWorld[i][j] != null) {
                    savedCellsInWorld[i][j].cellTimestep();
                }
            }
        }
    }

    public void printCells() {
        System.out.println();
        for (int row = 0; row < sizeY; row++) {
            for (int horI = 0; horI < sizeX; horI++) {
                System.out.print("--");
            }
            System.out.println();
            for (int vertI = 0; vertI < sizeX; vertI++) {
                System.out.print("|");
                if (cellsInWorld[row][vertI] != null) {
                    System.out.print("X");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.print("|");
            System.out.println();
        }
        for (int horI = 0; horI < sizeX; horI++) {
            System.out.print("--");
        }
    }

    public void addSurvivalCondition(int[][] locationList) {
        winZoneList.add(locationList);
    }

    public void survivalZoneListClear() {
        winZoneList.clear();
    }

    public void endGeneration() throws IllegalAccessException {
        System.out.println(winZoneList.size());
        if (winZoneList.size() == 0) {
            throw new IllegalAccessException();
        }
        //get successful cells
        for (int i = 0; i < winZoneList.size(); i++) {
            saveOneRegion(winZoneList.get(i));
        }
        
        //Clear cells in world
        purgeCellsInWorld();

        //Create new cells based on genomes of old cells
        reproduceSuccessfulCells();
    }



    private void saveOneRegion(int[][] region) {
        if (region[0][1] > region[1][1] || region[0][0] > region[1][0]) {
            throw new IllegalArgumentException();
        }
        for (int i = region[0][1]; i < region[1][1]; i++) {
            for (int j = region[0][0]; j < region[1][0]; j++) {
                if (cellsInWorld[i][j] != null) {
                    successfulCellList.add(cellsInWorld[i][j].getGenome());
                }
            }
        }
    }

    private void purgeCellsInWorld() {
        for (int i = 0; i < cellsInWorld.length; i++) {
            for (int j = 0; j < cellsInWorld[i].length; j++) {
                cellsInWorld[i][j] = null;
            }
        }
    }

    private void reproduceSuccessfulCells() {
        for (int i = 1; i <= pop; i++) {
            try {
                addCell(worldRandom.nextInt(1, sizeX+1), worldRandom.nextInt(1,sizeY+1), successfulCellList.get(worldRandom.nextInt(0,successfulCellList.size()+1)));
            }
            catch(Exception IllegalArgumentException) {
                i--;
            }
        }
    }

    public static void main(String[] args) {
        World thisWorld = new World(10, 10, 5, "Steve", 7, 0.01, 2);
        //System.out.println(thisWorld.nothingAtExceptionCount);
        DisplayWorld dWorld = new DisplayWorld(thisWorld);
        dWorld.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dWorld.setSize(600, 400);
        dWorld.setLocation(200,200);
        dWorld.setVisible(true);
        //thisWorld.worldTimestep();
        //displayWorld.displayThisWorld(thisWorld);
        

    }
}