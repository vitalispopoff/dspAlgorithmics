package data.structure.signal;

import java.util.ArrayList;

public class SamplePyramidKnot_0 extends ArrayList<AudioData> implements SamplePyramid {


    public void removeSamples(int fromIndex, int toIndex) {

        super.removeRange(fromIndex, toIndex);
    }


    public int size(){

        return super.size();
    }


    public AudioData getSampling(int i){

        return super.get(i);
    }


    public void addSampling(AudioData s){

        super.add(s);
    }

}