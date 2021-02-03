package data.structure.signal;

import java.util.ArrayList;

public class Channel extends ArrayList<Sampling> implements Channeling {


    public void removeSamples(int fromIndex, int toIndex) {

        super.removeRange(fromIndex, toIndex);
    }


    public int size(){

        return super.size();
    }


    public Sampling getSampling(int i){

        return super.get(i);
    }


    public void addSampling(Sampling s){

        super.add(s);
    }

}