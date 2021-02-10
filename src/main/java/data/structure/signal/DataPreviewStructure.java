package data.structure.signal;

public interface DataPreviewStructure {




	static DataPreviewStructure newInstance(){

		return new DataPreviewStructureKnot_0();
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