public class Cell {
    /**
    * Ints Stored: Position in X and Y, birthTime (to calculate age), etc.
    * Genome class to handle more complex genome stuff later on
    * Brain class (initialized after genome class and is then just past the info from the genome class)
     */
    private int posX, posY, birthTime;
    private Genome cellGenome;
    private Brain cellBrain;

    public Cell(int startX, int startY, ArrayList<String> inputGenome) {
        posX = startX;
        posY = startY;
        birthTime = World.cTime;
        cellGenome = new Genome(inputGenome);
        cellBrain = new Brain(cellGenome.getGenes);
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

}