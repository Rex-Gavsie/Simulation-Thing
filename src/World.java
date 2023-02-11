public class World {
    public int cTime; // Every timestep, cTime++.
    public double mutationFactor; //Probability of a mutation (eg. 0.1 is 10%)
    private Cell[][] cellsInWorld; //2D Array List of Cells

    public World(int sizeX, int sizeY, int startPopulation) {
        cellsInWorld = new Cell[sizeY][sizeX];
    }
}