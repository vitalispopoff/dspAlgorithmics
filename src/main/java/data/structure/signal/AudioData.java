package data.structure.signal;

import algorithms.metaProcessors.FileContentConverter;

/*
* unidirectional cycled list.
* with indices, and tail (set for the anchor)
*
*
* */
public interface AudioData {

	//	!--- TODO to be removed	--------------------------------

	boolean
		_switcher3 = false;	// * run new implementation ?

	temporal t = new temporal();
	class temporal{ static{ System.out.println("AudioData > new ver = " + _switcher3); }}

	//	!--- TODO ----------------------------------------------


	static AudioData newInstance(int v) {

		return new AudioDataSample(v);
	}

	static AudioData setFromSource(byte[] source, int blockAlign) {

		AudioData
			anchor = newInstance(FileContentConverter.readDataSample(source, 0, blockAlign)),
			temp = anchor;

		anchor.setIndex(0);

		int
			stripLength = source.length / blockAlign;

		for (int i = 1; i < stripLength; i++) {

			int
				v = FileContentConverter.readDataSample(source, i * blockAlign, blockAlign);

			temp.setNext(newInstance(v));
			temp = temp.getNext();
			temp.setIndex(i);
		}

		temp.setNext(anchor);
		anchor.setTail(temp);

		return anchor;
	}



	int getChannel();

	void setChannel(int c);


	int getIndex();

	void setIndex(int i);


	int getValue();

	void setValue(int v);


	void setNext(AudioData s);

	AudioData getNext();


	void setTail(AudioData s);

	AudioData getTail();


	byte[] getAll(int blockAlign);

	int size();
}