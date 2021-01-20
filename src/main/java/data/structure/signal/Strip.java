package data.structure.signal;

import java.util.ArrayList;

public class Strip extends ArrayList<Sampleable> implements Stripable {

    public void removeSamples(int fromIndex, int toIndex) {

        super.removeRange(fromIndex, toIndex);
    }

    public int size(){

        return super.size();
    }

    public Sampleable get(int i){

        return (Sampleable) super.get(i);
    }


    public void addSample(Sampleable s){

        super.add(s);
    }

}