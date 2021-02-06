package data.structure.signal;

import java.util.*;

import static algorithms.metaProcessors.FileContentConverter.*;

public class AudioData implements Audio {


	private static final boolean
		_switcher = false;	// ! TODO to be removed


	public ArrayList<SignalTree>
		_channelList = new ArrayList<>();    // ! TODO to be removed

	int
		channelIndex = 0;

	AudioData
		nextChannel = this;

	SignalTree
		channelStart;


	private AudioData() {}


	private AudioData(int numberOfChannels, int index) {

		if (numberOfChannels > 0) {
			channelIndex = index;
			nextChannel = new AudioData(--numberOfChannels, ++index);
		}
	}

	public AudioData(int numberOfChannels) {

		this(numberOfChannels, 0);

		AudioData
			s = this;

		while (s.nextChannel != s)
			s = s.nextChannel;

		s.nextChannel = this;


		{
			for (int i = 0; i < 3; i++)
				_channelList.add(SignalTree.newInstance());

		}    // ! TODO to be removed

	}

	AudioData(byte[] source, int blockAlign, int numberOfChannels) {

		this(numberOfChannels);

		importToChannels(bytesToIntegers(source, blockAlign / numberOfChannels), numberOfChannels);

	}    // ! TODO to be rewritten

	private void importToChannels(Integer[] input, int numberOfChannels) {

		if (_channelList != null && this._channelList.size() > 0) {
			_channelList.clear();
		}

		for (int i = 0; i < numberOfChannels; i++)
			_channelList.add(SignalTree.newInstance());

		int
			index = 0;

		for (Integer i : input)
			_channelList.get(index++ % numberOfChannels).addSampling(i);

	}    // ! TODO to be moved elsewhere


/*	public static Audio newInstance(byte[] source, int blockAlign, int numberOfChannels) {

		return new AudioData(source, blockAlign, numberOfChannels);

	}    // ! TODO to be rewritten*/



	private void addChannel() {

		_channelList.add(SignalTree.newInstance());    // ! TODO to be removed

		AudioData
			temp = nextChannel;

		while (temp.nextChannel.channelIndex != 0)
			temp = temp.nextChannel;

		temp.nextChannel = new AudioData(temp.nextChannel, temp.channelIndex + 1);

	}

	private AudioData(AudioData n, int i) {

		this();
		nextChannel = n;
		channelIndex = i;
	}


	@Override
	public byte[] getSource(int bitsPerSample) {

		return integersToBytes(consolidateChannels(), bitsPerSample);

	}    //	! TODO to be rewritten or moved to the interface

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

	}    // ! TODO to be moved elsewhere


	public SignalTree getChannel(int index) {

		if (_switcher) {

			AudioData
				result = this;

			while (this.channelIndex != index)
				result = result.nextChannel;

			return result.channelStart;
		}

		return _channelList.get(index);    // ! TODO to be removed

	}

}

/*private void removeChannel(int index){ channels.remove(index); }*/    // remove channels not used at all