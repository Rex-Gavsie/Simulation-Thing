import java.util.ArrayList;
import java.util.List;

public class Brain {
    /** The Neural Net of a cell
    * TO BE IMPLEMENTED
     */
    private Genome genomeOfBrain;
    private Cell cellOfBrain;
    private int[] inputNuerons, hiddenNuerons, outputNuerons;

    /**
    * @param inputGenome stores the input genome
    * 
    * Should initialize a neural net with connections/tendencies based on the genome
     */
    public Brain(Cell inputCell, Genome inputGenome) {
        cellOfBrain = inputCell;
        genomeOfBrain = inputGenome;
        inputNuerons = new int[cellOfBrain.numInputSensors];
        outputNuerons = new int[cellOfBrain.numOutputs];
    }

    /** ----What the brain needs to do----
     * 
     * Takes input and normalizes it into a value between 0 and 1
     * 
     * Multiplies input by the weight, adds the bias
     * 
     * Neuron sums all of the modified input values it's given, and then runs it through a sigmoid function
     * 
     * If output neuron is greater than 0.9, fire the neuron
     * 
     */
}