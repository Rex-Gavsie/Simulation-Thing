import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Brain {
    /** The Neural Net of a cell
    * TO BE IMPLEMENTED
     */
    private Genome genomeOfBrain;
    private Cell cellOfBrain;
    private int[] inputNuerons, hiddenNuerons, outputNuerons;
    private ArrayList<NueronConnection> connectionList = new ArrayList<NueronConnection>();

    /**
    * @param inputGenome stores the input genome
    * 
    * Should initialize a neural net with connections/tendencies based on the genome
     */
    public Brain(Cell inputCell, Genome inputGenome) {
        cellOfBrain = inputCell;
        genomeOfBrain = inputGenome;
        inputNuerons = new int[cellOfBrain.numInputSensors];
        hiddenNuerons = new int[cellOfBrain.numHiddenNuerons];
        outputNuerons = new int[cellOfBrain.numOutputs];
        parseGenome(genomeOfBrain);
    }

    private void parseGenome(Genome genome) {
        for (int i = 0; i < genomeOfBrain.genes.size(); i++) {
            //Establishing stuff we want to store
            String forloopGenome = genomeOfBrain.genes.get(i);
            String hexStart = forloopGenome.substring(0,2); //Hex for start neuron
            String hexEnd = forloopGenome.substring(2, 4); //Hex for end nueron
            String hexWeight = forloopGenome.substring(4,6); //Hex for weight
            String bcBinary = Integer.toBinaryString(Integer.parseInt(forloopGenome.substring(6), 16)); //Binary for Bias and Correlation
            Double weight = 1.0;
            Double bias = 1.0;

            //Setting constants
            final Double max8BitBinaryInDecimalOver4 = 63.75;
            final Double max6BitBinaryInDecimalOver4 = 15.75;
            final int max7BitInteger = 64;

            // Finding the nuerons being connected
            int[] startInfo = findNueronIndex(hexStart, 0);
            int[] endInfo = findNueronIndex(hexEnd, 1);

            //Get weight
            weight = (double)(Integer.parseInt(hexWeight,16))/max8BitBinaryInDecimalOver4;

            //Bias is using two's complement so I'm going to do some preprocessing first to make sure the range is the same
            bias = (double)((max7BitInteger*Integer.parseInt(bcBinary.substring(0,1)))-Integer.parseInt(bcBinary.substring(1,7), 2));
            if (bias < -63.0) {
                bias = -63.0; //Just making sure its [-63,63], may want to revisit this later
            }
            //Raw bias with two's complement is established so we can now go from there
            bias = bias/max6BitBinaryInDecimalOver4;

            //Decide positive or negative correlation
            switch (Integer.parseInt(bcBinary.substring(7))) {
                case 0:
                    weight = weight*-1;
                    break;
                case 1:
                    break;
                default:
                    throw new IllegalArgumentException("How did you get something that isn't 0 or 1 in binary????");
            }
            
            //Finally, store all this data in a new connection object
            NueronConnection newConnection = new NueronConnection(startInfo, endInfo, weight, bias, this);
            connectionList.add(newConnection);
        }
    }

    /**
     * 
     * @param hexInput
     * @param nueronStartPoint
     * If 0, this is the start point for the connection and can only return a input or hidden nueron
     * If 1, this is the end point for the connection and can only return an output or hidden nueron
     * @return list [Layer, Index]
     */
    private int[] findNueronIndex(String hexInput, int connectionType) { 
        int convertedHexInput = Integer.parseInt(hexInput, 16);
        int nueronIndex = -1;
        int[] output = {0, -1};
        switch (connectionType) { 
            case 0: //It's finding a start point
                nueronIndex = convertedHexInput % (cellOfBrain.numInputSensors + cellOfBrain.numHiddenNuerons);
                if (nueronIndex >= cellOfBrain.numInputSensors) { //Splitting the combined index into two seperate values
                    output[0] = 1;
                    output[1] = nueronIndex-cellOfBrain.numInputSensors;
                } else {
                    output[1] = nueronIndex;
                }
                break;
            case 1:  //It's finding an end point
                nueronIndex = convertedHexInput % (cellOfBrain.numHiddenNuerons + cellOfBrain.numOutputs);
                if (nueronIndex < cellOfBrain.numHiddenNuerons) { //Same idea as above (splitting the index)
                    output[0] = 1;
                    output[1] = nueronIndex;
                } else {
                    output[0] = 2;
                    output[1] = nueronIndex-cellOfBrain.numHiddenNuerons;
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid connection type");
        }
        return(output);
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