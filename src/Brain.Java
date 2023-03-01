import java.util.ArrayList;

public class Brain {
    /** The Neural Net of a cell
    * TO BE IMPLEMENTED
     */
    private Genome genomeOfBrain;
    private Cell cellOfBrain;
    /**
    * @param inputGenome stores the input genome
    * 
    * Should initialize a neural net with connections/tendencies based on the genome
     */
    public Brain(Cell inputCell, Genome inputGenome) {
        cellOfBrain = inputCell;
        genomeOfBrain = inputGenome;
    }
}