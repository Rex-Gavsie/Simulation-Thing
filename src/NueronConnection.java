/**
 * Just stores data on connections, this might be more efficient to do through a list rather than an object
 */
public class NueronConnection {
    public int[] startNueronIndex, endNueronIndex;
    public double connectionWeight, connectionBias;
    public Brain brainOfConnection;

    public NueronConnection(int[] inputStart, int[] inputEnd, double inputWeight, double inputBias, Brain inputBrain) {
        startNueronIndex = inputStart;
        endNueronIndex = inputEnd;
        connectionWeight = inputWeight;
        connectionBias = inputBias;
        brainOfConnection = inputBrain;
        

    }


}
