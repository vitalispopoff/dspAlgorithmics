package data.structure.signal;

public interface Channeling {

	//	!--- TODO to be removed	--------------------------------

	boolean
		_switcher0 = false; // * run new implementation ?

	temporal t = new temporal();
	class temporal{ static{ System.out.println("Channeling > new implementation = " + Channeling._switcher0); }}

	//	!--- TODO ----------------------------------------------


	static Channeling newInstance(byte[] source, int blockAlign, int numberOfChannels){

		return new Channels(source, blockAlign, numberOfChannels);
	}



	default int size(){

		return 1;
	}


	SamplePyramid getChannel(int index);

	byte[] releaseSource(int bitsPerSample);



}