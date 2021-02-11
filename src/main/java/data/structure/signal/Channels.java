package data.structure.signal;

import java.util.*;

import static algorithms.metaProcessors.FileContentConverter.*;

public class Channels implements Channeling {

	//	!--- TODO to be removed	--------------------------------

	static final boolean
		_switcher = true;

	public ArrayList<SamplePyramid>
		_channelList = new ArrayList<>();

	//	!--- TODO ----------------------------------------------

	int
		channelIndex;

	Channels
		nextChannel = this;

	SamplePyramid
		samplePyramid;


	public Channels(int numberOfChannels) {

		this(numberOfChannels, 0);

		Channels
			temp = this;

		while (temp.nextChannel != temp) temp = temp.nextChannel;

		temp.nextChannel = this;

	//	!--- TODO to be removed	--------------------------------

		if (!_switcher) {
			for (int i = 0; i < 3; i++)
				_channelList.add(SamplePyramid.newInstance());

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

		if (_switcher) importToChannels(bytesToIntegers(source, blockAlign / numberOfChannels));

		else importToChannels(bytesToIntegers(source, blockAlign / numberOfChannels), numberOfChannels);
	}

	private void importToChannels(Integer[] input) {


	}


	@Override
	public SamplePyramid getChannel(int index) {

		if (_switcher) {

			Channels
				result = this;

			while (this.channelIndex != index)
				result = result.nextChannel;

			return result.samplePyramid;
		}

		return _channelList.get(index);    // ! TODO line to be removed
	}



	//	!--- TODO to be relocated ------------------------------

	private void importToChannels(Integer[] input, int numberOfChannels) {

		if (_channelList != null && this._channelList.size() > 0) {
			_channelList.clear();
		}

		for (int i = 0; i < numberOfChannels; i++)
			_channelList.add(SamplePyramid.newInstance());

		int
			index = 0;

		for (Integer i : input)
			_channelList.get(index++ % numberOfChannels).addSampling(i);
	}

	@Override
	public byte[] getSource(int bitsPerSample) {

		return integersToBytes(consolidateChannels(), bitsPerSample);
	}

	private Integer[] consolidateChannels() {

		int
			numberOfChannels = _channelList.size(),
			numberOfSamples = _channelList.get(0).size(),
			resultLength = numberOfChannels * numberOfSamples;

		Integer[]
			result = new Integer[resultLength];

		for (int i = 0; i < resultLength; i++) {

			int
				channelIndex = i % numberOfChannels,
				sampleIndex = (i - channelIndex) / numberOfChannels;

			result[i] = this._channelList.get(channelIndex).getSampling(sampleIndex).getValue();
		}

		return result;
	}

	//	!--- TODO ----------------------------------------------




}

/*private void removeChannel(int index){ channels.remove(index); }*/    // remove channels not used at all