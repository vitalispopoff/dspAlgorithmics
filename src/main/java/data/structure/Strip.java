package data.structure;

import java.util.ArrayList;

public class Strip extends ArrayList<Integer> {

    public void removeSamples(int fromIndex, int toIndex){

        super.removeRange(fromIndex, toIndex);
    }
}