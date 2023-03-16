import java.util.ArrayList;
import java.util.List;

public class Brain {
    /** The Neural Net of a cell
    * TO BE IMPLEMENTED
     */
    private Genome genomeOfBrain;
    private Cell cellOfBrain;
    private double[] inputNuerons, hiddenNuerons, outputNuerons; //min int value Indicates an out of use nueron, otherwise every timestep it will call the inputs from the cell to update it and then call the outputs that it chooses
    private boolean arraysInitialized = false; //Stops us from minimizing the arrays after initialization
    private ArrayList<NueronConnection> connectionList = new ArrayList<NueronConnection>();
    private boolean actionCompleted = false; //Limits cell to 1 action per step

    /**
    * @param inputGenome stores the input genome
    * 
    * Should initialize a neural net with connections/tendencies based on the genome
     */
    public Brain(Cell inputCell, Genome inputGenome) {
        cellOfBrain = inputCell;
        genomeOfBrain = inputGenome;
        inputNuerons = new double[cellOfBrain.numInputSensors];
        hiddenNuerons = new double[cellOfBrain.numHiddenNuerons];
        outputNuerons = new double[cellOfBrain.numOutputs];
        minAllNueronArrays(); //Minimize all arrays
        parseGenome(genomeOfBrain); //Parse the genome and create connections
        specifyUsableIO(); //Sets all in-use nuerons to 0

        //DEBUG
    //     for (int i = 0; i < connectionList.size(); i++) {
    //         NueronConnection connection = connectionList.get(i);
    //         System.out.println("--CONNECTION " + i + "--");
    //         System.out.println("Gene: " + genomeOfBrain.genes.get(i));
    //         System.out.println();
    //         switch (connection.startNueronIndex[0]) {
    //             case 0:
    //                 System.out.println("Start Layer: Input"); 
    //                 switch (connection.startNueronIndex[1]) {
    //                     case 0:
    //                         System.out.println("Start Nueron: Above Sight Distance"); break;
    //                     case 1:
    //                         System.out.println("Start Nueron: Below Sight Distance"); break;
    //                     case 2:
    //                         System.out.println("Start Nueron: Left Sight Distance"); break;
    //                     case 3:
    //                         System.out.println("Start Nueron: Right Sight Distance"); break;
    //                     case 4:
    //                         System.out.println("Start Nueron: External Time"); break;
    //                     case 5:
    //                         System.out.println("Start Nueron: Internal Time"); break;
    //                     case 6:
    //                         System.out.println("Start Nueron: Top Border Distance"); break;
    //                     case 7:
    //                         System.out.println("Start Nueron: Bottom Border Distance"); break;
    //                     case 8:
    //                         System.out.println("Start Nueron: Left Border Distance"); break;
    //                     case 9:
    //                         System.out.println("Start Nueron: Right Border Distance"); break;
    //                     default:
    //                         System.out.println("ERROR!!!! Given Start Index: " + connection.startNueronIndex[1]); break;
    //                 }
    //                 break;
    //             case 1:
    //                 System.out.println("Start Layer: Hidden");
    //                 System.out.println("Start Nueron: " + connection.startNueronIndex[1]);
    //                 break;
    //             default:
    //                 System.out.println("ERROR!!!! Given Start Layer: " + connection.startNueronIndex[0]);
    //         }
    //         System.out.println();
    //         switch (connection.endNueronIndex[0]) {
    //             case 1:
    //                 System.out.println("End Layer: Hidden");
    //                 System.out.println("End Nueron: " + connection.endNueronIndex[1]); break;
    //             case 2:
    //                 System.out.println("End Layer: Output");
    //                 switch (connection.endNueronIndex[1]) {
    //                     case 0:
    //                         System.out.println("End Nueron: Move Up"); break;
    //                     case 1:
    //                         System.out.println("End Nueron: Move Down"); break;
    //                     case 2:
    //                         System.out.println("End Nueron: Move Left"); break;
    //                     case 3:
    //                         System.out.println("End Nueron: Move Right"); break;
    //                     default:
    //                         System.out.println("ERROR!!!! Given End Index: " + connection.endNueronIndex[1]); break;
    //                 }
    //                 break;
    //             default:
    //                 System.out.println("ERROR!!!! Given End Layer: " + connection.endNueronIndex[0]); break;
    //         }
    //         System.out.println();
    //         System.out.println("Connection Weight: " + connection.connectionWeight);
    //         System.out.println("Connection Bias: " + connection.connectionBias);
    //         System.out.println();
    //     }
    }
    
    /**
     * Intialization Methods -----------------------------------
     */
    private void minAllNueronArrays() {
        if (arraysInitialized == true) {
            throw new IllegalCallerException("Arrays already minimized");
        } else {
            boolean inputDone = false;
            boolean hiddenDone = false;
            boolean outputDone = false;
            for (int i = 0; i < Math.max(inputNuerons.length, Math.max(hiddenNuerons.length, outputNuerons.length)); i++) {
                //Inputs
                if (inputDone == false && inputNuerons.length - i > 0) {
                    inputNuerons[i] = Integer.MIN_VALUE;
                } else {
                    inputDone = true;
                }
                //Hidden
                if (hiddenDone == false && hiddenNuerons.length - i > 0) {
                    hiddenNuerons[i] = Integer.MIN_VALUE;
                } else {
                    hiddenDone = true;
                }
                //Outputs
                if (outputDone == false && outputNuerons.length - i > 0) {
                    outputNuerons[i] = Integer.MIN_VALUE;
                } else {
                    outputDone = true;
                }
            }
        }
    }

    private void parseGenome(Genome genome) {
        for (int i = 0; i < genomeOfBrain.genes.size(); i++) {
            //Establishing stuff we want to store
            String forloopGenome = genomeOfBrain.genes.get(i);
            String hexStart = forloopGenome.substring(0,2); //Hex for start neuron
            String hexEnd = forloopGenome.substring(2, 4); //Hex for end nueron
            String hexWeight = forloopGenome.substring(4,6); //Hex for weight
            String bcBinary = Integer.toBinaryString(Integer.parseInt(forloopGenome.substring(6), 16)); //Binary for Bias and Correlation
            while (bcBinary.length() < 8) {
                bcBinary = '0'+bcBinary;
            } 
            Double weight = 1.0;
            Double bias = 1.0;

            //Setting constants
            final Double MAX8BITBINARYOVER4 = 63.75;
            final Double MAX6BITBINARYOVER4 = 15.75;
            final int BINARY7TH = 64;

            // Finding the nuerons being connected
            int[] startInfo = findNueronIndex(hexStart, 0);
            int[] endInfo = findNueronIndex(hexEnd, 1);

            //Get weight
            weight = (double)(Integer.parseInt(hexWeight,16))/MAX8BITBINARYOVER4;

            //Bias is using two's complement so I'm going to do some preprocessing first to make sure the range is the same
            bias = (double)((BINARY7TH*Integer.parseInt(bcBinary.substring(0,1)))-Integer.parseInt(bcBinary.substring(1,7), 2));
            if (bias < -63.0) {
                bias = -63.0; //Just making sure its [-63,63], may want to revisit this later
            }
            //Raw bias with two's complement is established so we can now go from there
            bias = bias/MAX6BITBINARYOVER4;

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

    private void specifyUsableIO() {
        for (int i = 0; i < connectionList.size(); i++) {
            NueronConnection currentConnectionForPurge = connectionList.get(i);
            switch (currentConnectionForPurge.startNueronIndex[0]) {
                case 0:
                    inputNuerons[currentConnectionForPurge.startNueronIndex[1]] = 0; break;
                case 1:
                    hiddenNuerons[currentConnectionForPurge.startNueronIndex[1]] = 0; break;
                default: break;
            }
            switch (currentConnectionForPurge.endNueronIndex[0]) {
                case 1:
                    hiddenNuerons[currentConnectionForPurge.endNueronIndex[1]] = 0; break;
                case 2:
                    outputNuerons[currentConnectionForPurge.endNueronIndex[1]] = 0; break;
                default: break;
            }
        }
    }
    
    /**
     * Math and other utilities ------------------------
     */
    private static double sigmoid(double x) {
        if (x > 10) {
            return 1;
        }
        else if (x < -10) {
            return 0;
        }
        else {
            return 1/(1+ Math.exp(-x));
        }
    }

    /**
     * Actual Usable Stuff ----------------------------
     */

    /**
     * 
     * @param inputData 
     * <p> Takes data in the form of [layer, index]
     * @return
     * <p> 0 - Input Layer
     * <p> 1 - Hidden Layer
     * <p> 2 - Output Layer
     */
    private double getNueron(int[] inputData) {
        switch (inputData[0]) {
            case 0:
                return inputNuerons[inputData[1]];
            case 1:
                return hiddenNuerons[inputData[1]];
            case 2:
                return outputNuerons[inputData[1]];
            default:
                throw new IllegalArgumentException("Data format is incorrect getNueron");
        }
    }

    public void brainTimestep() {
        //REFRESH INPUTS AND ZERO HIDDEN LAYERS/OUTPUTS
        for (int i = 0; i < inputNuerons.length; i++) {
            if (inputNuerons[i] > Integer.MIN_VALUE) {
                inputNuerons[i] = cellOfBrain.callSensor(i);
            }
        }
        for (int i = 0; i < hiddenNuerons.length; i++) {
            if (hiddenNuerons[i] > Integer.MIN_VALUE) {
                hiddenNuerons[i] = 0;
            }
        }
        for (int i = 0; i < outputNuerons.length; i++) {
            if (outputNuerons[i] > Integer.MIN_VALUE) {
                outputNuerons[i] = 0;
            }
        }

        actionCompleted = false;

        //Calculate connections from Sensors to hidden layers
        ArrayList<Integer> hiddenLayerConnections = new ArrayList<Integer>(); //Store the index of hidden layer connections to come back to after
        for (int i = 0; i < connectionList.size(); i++) {
            NueronConnection currentConnectionInputStep = connectionList.get(i);
            if (currentConnectionInputStep.startNueronIndex[0] == 1) { //make a list of connections starting at the hidden layer
                hiddenLayerConnections.add(i);
            } else {
                Double sensorValue = (double)(inputNuerons[currentConnectionInputStep.startNueronIndex[1]]);
                Double formattedValue = ((sensorValue*currentConnectionInputStep.connectionWeight)+currentConnectionInputStep.connectionBias);
                if (currentConnectionInputStep.endNueronIndex[0] == 1) {
                    hiddenNuerons[currentConnectionInputStep.endNueronIndex[1]] += formattedValue;
                } else {
                    outputNuerons[currentConnectionInputStep.endNueronIndex[1]] += formattedValue;
                }
            }
        }

        //Calculate connections from hidden layers to hidden layers, passing them through a sigmoid function first
        for (int i = 0; i < hiddenLayerConnections.size(); i++) {
            NueronConnection hiddenConectionSummation = connectionList.get(hiddenLayerConnections.get(i));
            if (hiddenConectionSummation.endNueronIndex[0] == 1) {
                Double activatedValue = sigmoid((double)(getNueron(hiddenConectionSummation.startNueronIndex)));
                activatedValue = (activatedValue*hiddenConectionSummation.connectionWeight)+hiddenConectionSummation.connectionBias;
                hiddenNuerons[hiddenConectionSummation.endNueronIndex[1]] += activatedValue;
            }
        }

        //Calculate connections from hidden layers to outputs
        for (int i = 0; i < connectionList.size(); i++) {
            NueronConnection outputSumConnection = connectionList.get(i);
            if (outputSumConnection.endNueronIndex[0] == 2 && outputSumConnection.startNueronIndex[0] == 1) { //Check that the output is the output layer
                Double formattedValue = (((double)(getNueron(outputSumConnection.startNueronIndex)))*outputSumConnection.connectionWeight)+outputSumConnection.connectionBias;
                outputNuerons[outputSumConnection.endNueronIndex[1]] += formattedValue;
            }
        }

        //Activation function time!!!
        for (int i = 0; i < hiddenNuerons.length; i++) {
            if (hiddenNuerons[i] > Integer.MIN_VALUE) {
                hiddenNuerons[i] = sigmoid(outputNuerons[i]);
            }
        }

        for (int i = 0; i < outputNuerons.length; i++) {
            if (outputNuerons[i] > Integer.MIN_VALUE) {
                outputNuerons[i] = sigmoid(outputNuerons[i]);
            }
        }

        //Verify your outputs
        if (outputNuerons[0] > 0.9 && outputNuerons[1] > 0.9) { //Check if wants to move up and down and if so, do neither
            outputNuerons[0] = 0;
            outputNuerons[1] = 0;
        }
        
        if (outputNuerons[2] > 0.9 && outputNuerons[3] > 0.9) { //Check if wants to move left and right and if so, do neither
            outputNuerons[2] = 0;
            outputNuerons[3] = 0;
        }

        //Call your outputs!
        for (int i = 0; i < outputNuerons.length; i++) {
            if (outputNuerons[i] > 0.9 && actionCompleted == false) {
                //Debug
                System.out.println("Nueron " + i + " value: " + outputNuerons[i]);
                switch (i) {
                    case 0:
                        System.out.println("Attempting to Move Up!"); break;
                    case 1:
                        System.out.println("Attempting to Move Down!"); break;
                    case 2:
                        System.out.println("Attempting to Move Left!"); break;
                    case 3:
                        System.out.println("Attempting to Move Right!"); break;

                }
                //Debug End
                try {
                    cellOfBrain.doOutput(i);
                    actionCompleted = true;
                } catch (Exception IllegalCallerException) {
                    
                }
            }
        }

        //PLACE FOR ANY DATA COLLECTION


        //End and ideally give a confirmation

    }
    
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