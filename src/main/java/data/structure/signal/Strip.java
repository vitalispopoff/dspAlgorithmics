package data.structure.signal;

import java.util.ArrayList;

public class Strip extends ArrayList<Sampling> implements Stripable {

    public void removeSamples(int fromIndex, int toIndex) {

        super.removeRange(fromIndex, toIndex);
    }

    public int size(){

        return super.size();
    }

    public Sampling get(int i){

        return (Sampling) super.get(i);
    }


    public void addSample(Sampling s){

        super.add(s);
    }

}