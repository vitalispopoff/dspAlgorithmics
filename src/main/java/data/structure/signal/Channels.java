package data.structure.signal;

import java.util.*;

import static algorithms.metaProcessors.FileContentConverter.*;

public class Channels implements Channeling {


	public ArrayList<SamplePyramid> _oldSamplePyramid = new ArrayList<>();	//	!--- TODO to be removed


	int
		channelIndex;

	Channels
		nextChannel = this;

	SamplePyramid
		samplePyramid;


	public Channels(int numberOfChannels) {

		this(numberOfChannels, 0);

		if (_switcher0) {

			Channels
				temp = this;

			while (temp.nextChannel != temp) temp = temp.nextChannel;

			temp.nextChannel = this;
		}

		if (!_switcher0) _oldSamplePyramid = Previewing.getCurrentSamples();    //	!--- TODO to be removed
	}

	private Channels(int numberOfChannels, int index) {

		if (numberOfChannels > 0) {
			channelIndex = index;
			nextChannel = new Channels(--numberOfChannels, ++index);
		}

		if (numberOfChannels == 0) nextChannel = this;
	}


	Channels(byte[] source, int blockAlign, int numberOfChannels) {

		this(numberOfChannels);

		importToChannels(bytesToIntegers(source, blockAlign / numberOfChannels), numberOfChannels);
	}

	/*Channels(AudioData audioData, int numberOfChannels){

		this(numberOfChannels);

		importToChannels(audioData, numberOfChannels);
	}*/	// ! we want to write it tho

	/*private void importToChannels(AudioData audioAnchor, int numberOfChannels){

		if (_oldSamplePyramid != null && this._oldSamplePyramid.size() > 0) {
			_oldSamplePyramid.clear();
		}

		for (int i = 0; i < numberOfChannels; i++)
			_oldSamplePyramid.add(SamplePyramid.newInstance());

		AudioData
			currentSample = audioAnchor;

		while (currentSample.getNext() != currentSample) {

			_oldSamplePyramid.get(currentSample.getIndex()).addSampling(audioAnchor);
			currentSample = currentSample.getNext();
		}

	}*/ // ! that's the part to be written too

	@Override
	public SamplePyramid getChannel(int index) {

		if (_switcher0) {

			Channels
				result = this;

			while (this.channelIndex != index)
				result = result.nextChannel;

			return result.samplePyramid;
		}

		return _oldSamplePyramid.get(index);    //	!--- TODO to be removed
	}


	//	!--- TODO to be relocated ------------------------------

	private void importToChannels(Integer[] input, int numberOfChannels) {

		if (_oldSamplePyramid != null && this._oldSamplePyramid.size() > 0) {
			_oldSamplePyramid.clear();
		}

		for (int i = 0; i < numberOfChannels; i++)
			_oldSamplePyramid.add(SamplePyramid.newInstance());

		int
			index = 0;

		for (Integer i : input)
			_oldSamplePyramid.get(index++ % numberOfChannels).addSampling(i);
	}

	/*
//	@Override
	public byte[] releaseSource(int bitsPerSample) {

		return integersToBytes(consolidateChannels(), bitsPerSample);
	}*/ // ! disposable

	/*private Integer[] consolidateChannels() {

		int
			numberOfChannels = _oldSamplePyramid.size(),
			numberOfSamples = _oldSamplePyramid.get(0).size(),
			resultLength = numberOfChannels * numberOfSamples;

		Integer[]
			result = new Integer[resultLength];

		for (int i = 0; i < resultLength; i++) {

			int
				channelIndex = i % numberOfChannels,
				sampleIndex = (i - channelIndex) / numberOfChannels;

			result[i] = this._oldSamplePyramid.get(channelIndex).getSampling(sampleIndex).getValue();
		}

		return result;
	}*/ // ! disposable

	//	!--- TODO ----------------------------------------------
}