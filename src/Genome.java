public class Genome {
    public ArrayList<String> genes = new ArrayList<String>();

    /**
    * Creates a Genome for a given cell
    * @param inputGeneList is a string array containing all the cell's genes
    *  
    *   ***THIS COULD BE WHERE WE IMPLEMENT MUTATION***
    *
    */
    public Genome(ArrayList<String> inputGeneList) {
        for (int i = 0; i < Genes.length; i++) {
            genes.add(inputGeneList[i]);
        }
    }

    public ArrayList<String> getGenes() {
        return genes;
    }


}