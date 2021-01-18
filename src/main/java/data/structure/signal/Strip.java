package data.structure.signal;

import java.util.ArrayList;

public class Strip extends ArrayList<Sample> {

    public void removeSamples(int fromIndex, int toIndex){

        super.removeRange(fromIndex, toIndex);
    }
}