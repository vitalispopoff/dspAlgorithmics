package data.structure.signal;

import java.util.ArrayList;

public class Strip extends ArrayList<Sampling> {

    public void removeSamples(int fromIndex, int toIndex){

        super.removeRange(fromIndex, toIndex);
    }
}