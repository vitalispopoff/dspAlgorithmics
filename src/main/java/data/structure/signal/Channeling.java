package data.structure.signal;

public interface Channeling {


	static Channeling newInstance(){

		return new Channel();
	}

	int size();


	Sampling getSampling(int i);


	void addSampling(Sampling s);

	default void addSampling(Integer v){

		addSampling(Sampling.newInstance(v));
	}

}