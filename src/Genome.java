import java.util.ArrayList;
import java.util.List;

public class Genome {
    public ArrayList<String> genes = new ArrayList<String>();
    private double mutationFactor;

    /**
    * Creates a Genome for a given cell
    * @param inputGeneList is a string array containing all the cell's genes
    *  
    *   ***THIS COULD BE WHERE WE IMPLEMENT MUTATION***
    *
    */
    public Genome(ArrayList<String> inputGeneList, double inputMutationFactor) {
        mutationFactor = inputMutationFactor;
        for (int i = 0; i < inputGeneList.size(); i++) {
            genes.add(inputGeneList.get(i));
        }
    }
    
    public static ArrayList<String> generateRandomGenome(int numOfGenes) {
        ArrayList<String> placeholderGenome = new ArrayList<String>();
        placeholderGenome.add("aaaaa");
        placeholderGenome.add("bbbbb");
        return placeholderGenome;
    }

    public static String mutateGene(String inputGene) {
        return null;
    }
}