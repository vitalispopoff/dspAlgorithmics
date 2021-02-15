package data.structure.signal;

public interface Channeling {

	//	!--- TODO to be removed	--------------------------------

	boolean
		_switcher0 = false; // * run new implementation ?

	temporal t = new temporal();
	class temporal{ static{ System.out.println("Channeling > new ver = " + Channeling._switcher0); }}

	//	!--- TODO ----------------------------------------------


	static Channeling newInstance(byte[] source, int blockAlign, int numberOfChannels){

		return new Channels(source, blockAlign, numberOfChannels);
	}

/*	static Channeling newInstance(AudioData audioAnchor, int numberOfChannels){

		return new Channels(audioAnchor, numberOfChannels);
	}*/	// ? temporarily disabled - to be written good tho



	/*default int size(){

		return 1;
	}*/	// info to be taken from header


	SamplePyramid getChannel(int index);

	byte[] releaseSource(int bitsPerSample);
}