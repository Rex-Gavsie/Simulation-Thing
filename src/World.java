import java.util.ArrayList;
import java.util.Random;

public class World {
    public int cTime, sizeX, sizeY, numOfGenes, pop; // Every timestep, cTime++.
    public double mutationFactor; //Probability of a mutation (eg. 0.1 is 10%)
    public Cell[][] cellsInWorld; //2D Array List of Cells
    public String worldTitle;

    public World(int inputX, int inputY, int startPopulation, String title, int inputGeneNum) {
        cellsInWorld = new Cell[inputY][inputX];
        worldTitle = title;
        sizeX = inputX;
        sizeY = inputY;
        numOfGenes = inputGeneNum;
        pop = startPopulation;
        /*
         * Making cells based on the population number
         */
        
        for (int i = 1; i <= startPopulation; i++) {
            try {
                Random rand = new Random();
                addCell(rand.nextInt(0, inputX), rand.nextInt(0,inputY), Genome.generateRandomGenome(numOfGenes));
            }
            catch(Exception IllegalArgumentException) {
                i--;
            }
        }
        //test
        //addCell(1, 1, Genome.generateRandomGenome(2));
        
    }

    public void addCell(int startX, int startY, ArrayList<String> inputGenome) {
        Cell newCell = new Cell(startX, startY, inputGenome, this, mutationFactor);
        if (this.cellsInWorld[newCell.getY()-1][newCell.getX()-1] == null) {
            this.cellsInWorld[newCell.getY()-1][newCell.getX()-1] = newCell;
        }
        else {
            //System.out.println(startX + ", " + startY);
            //System.out.println(this.cellsInWorld[newCell.getY()-1][newCell.getX()-1]);
            throw new IllegalArgumentException("Already a cell there");
        }
    }

    public static void main(String[] args) {
        World thisWorld = new World(128, 128, 3000, "Steve", 2);
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