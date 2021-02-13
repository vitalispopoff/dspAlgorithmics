package data.structure.signal;

import java.util.ArrayList;

public class SampleBlock_old extends ArrayList<AudioData> implements SamplePyramid {


    public void addSampling(AudioData s){

        super.add(s);
    }


    public AudioData getSampling(int i){

        return super.get(i);
    }

    public int size(){

        return super.size();
    }
}