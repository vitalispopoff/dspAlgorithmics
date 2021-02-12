package data.structure.signal;

public interface SamplePyramid {


	static SamplePyramid newInstance(){

		return new SamplePyramidKnot_0();
	}



	default void addSampling(Integer v){

		addSampling(AudioData.newInstance(v));
	}

	default AudioData getSampling(int i){

		return null;
	}



	void addSampling(AudioData s);

	int size();
}