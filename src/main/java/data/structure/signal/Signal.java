package data.structure.signal;

import java.util.*;

import static algorithms.metaProcessors.FileContentConverter.*;

public class Signal extends ArrayList<Integer> implements Signaling {


	public ArrayList<Channeling>
		channels;


	Signal(){

		channels = new ArrayList<>();
	}


	public Signal(int numberOfChannels){

		this();

		for (int i = 0; i < numberOfChannels; i++) addChannel();
	}

	private void addChannel(){ channels.add(Channeling.newInstance()); }



	public static Signaling instanceOf(byte[] source, int blockAlign, int numberOfChannels){

		return new Signal(source, blockAlign, numberOfChannels);
	}

	private Signal(byte[] source, int blockAlign, int numberOfChannels){

		this(numberOfChannels);

		importToChannels(bytesToIntegers(source, blockAlign / numberOfChannels), numberOfChannels);
	}

	private void importToChannels(Integer[] input, int numberOfChannels){

		if (channels != null && this.channels.size() > 0)
			channels.clear();

		for (int i = 0; i < numberOfChannels; i++)
			channels.add(Channeling.newInstance());

		int
			index = 0;

		for (Integer i : input)
			channels.get(index++ % numberOfChannels).addSampling(i);
	}



	@Override
	public byte[] getSource(int bitsPerSample){

		return integersToBytes(consolidateChannels(), bitsPerSample);
	}

	private Integer[] consolidateChannels(){

		int
			numberOfChannels = channels.size(),
			numberOfSamples = channels.get(0).size(),
			resultLength = numberOfChannels * numberOfSamples;

		Integer[]
			result = new Integer[resultLength];

		for (int i = 0; i < resultLength; i++) {

			int
				channelIndex = i % numberOfChannels,
				sampleIndex = (i - channelIndex) / numberOfChannels;

			result[i] = this.channels.get(channelIndex).getSampling(sampleIndex).getValue();
		}

		return result;
	}



	public Channeling getChannel(int index){

		return channels.get(index);
	}

}

/*private void removeChannel(int index){ channels.remove(index); }*/	// remove channels not used at all