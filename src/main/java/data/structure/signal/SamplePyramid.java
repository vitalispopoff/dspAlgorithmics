package data.structure.signal;

public interface SamplePyramid {

	//	!--- TODO to be removed	--------------------------------

	boolean
		_switcher2 = false;	// * run new implementation ?

	temporal t = new temporal();
	class temporal{ static{ System.out.println("SamplePyramid > new implementation = " + _switcher2); }}

	//	!--- TODO ----------------------------------------------


	static SamplePyramid newInstance(){

		return _switcher2
			? new SampleBlock()
			: new SampleBlock_old();
	}


	default void addSampling(Integer v){

		addSampling(AudioData.newInstance(v));
	}


	void addSampling(AudioData s);

	AudioData getSampling(int i);

	int size();
}