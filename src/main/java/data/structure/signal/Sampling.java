package data.structure.signal;

import algorithms.metaProcessors.FileContentConverter;

public interface Sampling {


	static Sampling newInstance(int i){

		return new Sample_0(i);
	}

	static Sampling setFromSource(byte[] source, int sampleLength){

		Sampling
			anchor = newInstance(0),
			temp = anchor;

		int
			stripLength = source.length / sampleLength;

		for (int i = 0; i < stripLength ; i++){

			temp.setValue(FileContentConverter.readDataSample(source, i * sampleLength, sampleLength));
			temp.setNext(newInstance(0));
			temp = temp.getNext();
		}

		return anchor;
	}


	int getValue();

	void setValue(int v);


	void setNext(Sampling s);

	Sampling getNext();

}