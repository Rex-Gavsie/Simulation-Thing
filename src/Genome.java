import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Genome {
    public ArrayList<String> genes = new ArrayList<String>();
    private double mutationFactor;
    private Cell cellOfGenome;
    private static Random genomeRandom = new Random();

    /**
    * Creates a Genome for a given cell
    * @param inputGeneList is a string array containing all the cell's genes
    *  
    *   ***THIS COULD BE WHERE WE IMPLEMENT MUTATION***
    *
    */
    public Genome() {
        cellOfGenome = null;
        mutationFactor = 0;
    }

    public Genome(Cell inputCell, ArrayList<String> inputGeneList, double inputMutationFactor) {
        cellOfGenome = inputCell;
        mutationFactor = inputMutationFactor;
        for (int i = 0; i < inputGeneList.size(); i++) {
            genes.add(inputGeneList.get(i));
        }
        genomeRandom = inputCell.cellWorld.worldRandom;
    }
    
    public static ArrayList<String> generateRandomGenome(int numOfGenes) {
        ArrayList<String> outputGenome = new ArrayList<String>();
        for (int i = 0; i < numOfGenes; i++) {
            outputGenome.add(generateRandomGene());
        }
        return outputGenome;
    }

    public static String generateRandomGene() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            buffer.append(Integer.toHexString(genomeRandom.nextInt(1,16)));
        }
        return buffer.toString();
    }

    public static String mutateGene(String inputGene) {
        return null;
    }

    /*public static void main(String[] args) {
        Genome genome = new Genome();
        System.out.println(genome.generateRandomGenome(5));
    }*/
}

/**
 * How genes work
 * 8 bit hexadecimal storage
 *  
 * 32 bit binary
 * 00000000000000000000000000000000
 * Things needed to specify:
 - Input Neuron
 - Output Neuron
 - Correlation (1 or 0 to represent positive or negative)
 - Strength

 * Bits 1-8
 * 00000000  
 * input neuron, found by value modulo number of inputs/outputs/hidden layers 
 
 * Bits 9-16
 * 00000000
 * output neuron, found by value modulo number of inputs/outputs/hidden layers
 * Output Neuron MUST not equal input neuron
 *  
 * Bits 17-24
 * 00000000
 * Strength of Connection
 * Is found by Value /(Maximum Value*0.25)
 * Is multiplied by the input value to pass to the output neuron 
 * 
 * Bit 25-31
 * 0000000
 * Bias
 * 2s compliment
 * Is found by Value / (Maximum Value*0.25) 
 * 
 * Bit 32
 * Correlation 
 * If == 0, negative correlation
 * if == 1, positive correlation
 *
 */