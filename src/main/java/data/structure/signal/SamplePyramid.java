package data.structure.signal;

public interface SamplePyramid {




	static SamplePyramid newInstance(){

		return new SamplePyramidKnot_0();
	}

	int size();


	default AudioData getSampling(int i){

		return null;
	}


	void addSampling(AudioData s);

	default void addSampling(Integer v){

		addSampling(AudioData.newInstance(v));
	}

}