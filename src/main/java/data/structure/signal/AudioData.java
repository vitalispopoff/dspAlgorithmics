package data.structure.signal;

import algorithms.metaProcessors.FileContentConverter;

public interface AudioData {


	static AudioData newInstance(int v) {

		return new AudioDataSample(v);
	}

	static AudioData setFromSource(byte[] source, int blockAlign) {

		AudioData
			anchor = newInstance(FileContentConverter.readDataSample(source, 0, blockAlign)),
			temp = anchor;

		int
			stripLength = source.length / blockAlign;

		for (int i = 1; i < stripLength; i++) {

			int
				v = FileContentConverter.readDataSample(source, i * blockAlign, blockAlign);

			temp.setNext(newInstance(v));
			temp = temp.getNext();
		}

		return anchor;
	}


	int getValue();

	void setValue(int v);


	void setNext(AudioData s);

	AudioData getNext();
}