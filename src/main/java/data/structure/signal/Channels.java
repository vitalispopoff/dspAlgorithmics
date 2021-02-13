package data.structure.signal;

import data.structure.Previewing;

import java.util.*;

import static algorithms.metaProcessors.FileContentConverter.*;

public class Channels implements Channeling {

	//	!--- TODO to be removed	--------------------------------

	public ArrayList<SamplePyramid> _oldSamplePyramid = new ArrayList<>();

	//	!--- TODO ----------------------------------------------

	int
		channelIndex;

	Channels
		nextChannel = this;

	SamplePyramid
		samplePyramid;

	Previewing
		currentPreview;


	public Channels(int numberOfChannels) {

		this(numberOfChannels, 0);

		Channels
			temp = this;

		while (temp.nextChannel != temp) temp = temp.nextChannel;

		temp.nextChannel = this;

		//	!--- TODO to be removed	--------------------------------

		if (!_switcher0) {

//			for (int i = 0; i < 3; i++) _oldSamplePyramid.add(SamplePyramid.newInstance());

			_oldSamplePyramid = Previewing.getCurrentSamples();


		}

		//	!--- TODO ----------------------------------------------

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



	@Override
	public SamplePyramid getChannel(int index) {

		if (_switcher0) {

			Channels
				result = this;

			while (this.channelIndex != index)
				result = result.nextChannel;

			return result.samplePyramid;
		}

		return _oldSamplePyramid.get(index);    // ! TODO line to be removed
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

	@Override
	public byte[] releaseSource(int bitsPerSample) {

		return integersToBytes(consolidateChannels(), bitsPerSample);
	}

	private Integer[] consolidateChannels() {

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
	}

	//	!--- TODO ----------------------------------------------
}