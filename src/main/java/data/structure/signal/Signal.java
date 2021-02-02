package data.structure.signal;

import algorithms.metaProcessors.FileContentConverter;

import java.util.*;

//import static algorithms.metaProcessors.FileContentConverter.writeDataSample;

public class Signal extends ArrayList<Integer> implements Signalable{


	public ArrayList<Channeling>
		strips;



	public Signal(){

		strips = new ArrayList<>();
	}

	public Signal(int channels){

		this();

		for (int i = 0; i < channels; i++)

			addChannel();
	}

	public Signal(byte[] source, int blockAlign, int channels){

		this(channels);

		importToStrips(bytesToIntegers(source, blockAlign / channels), channels);
	}


	private void addChannel(){ strips.add(Channeling.instanceOf()); }
	private void removeChannel(int index){ strips.remove(index); }



	void importToStrips(Integer[] input, int channels){

		if (strips != null && strips.size() > 0)

			strips.clear();

		for (int i = 0; i < channels; i++) strips.add(Channeling.instanceOf());

		int
			index = 0;

		for (Integer i : input) strips.get(index++ % channels).addSample(Sampleable.instanceOf(i));
	}



	Integer[] bytesToIntegers(byte[] source, int sampleLength){

		Channeling
			strip = Channeling.instanceOf();

		int
			stripLength = source.length / (sampleLength);

		Integer[]
			result = new Integer[stripLength];

		for (int i = 0; i < stripLength; i++)

			result[i] =  FileContentConverter.readDataSample(source, i * sampleLength, sampleLength);

		return result;
	}



	Integer[] consolidateChannels(){

		int
			channels = strips.size(),
			numberOfSamples = strips.get(0).size(),
			resultLength = channels * numberOfSamples;

		Channeling
			sum = Channeling.instanceOf();

		Integer[]
			result = new Integer[resultLength];

		for (int i = 0; i < resultLength; i++) {

			int
				channelIndex = i % channels,
				sampleIndex = (i - channelIndex) / channels;

			result[i] = strips.get(channelIndex).get(sampleIndex).getValue();
		}

		return result;
	}

	/*	byte[] integersToBytes(Integer[] signal, int bitsPerSample){

		int
			sampleLength = bitsPerSample >>> 3;

		byte[]
			result = new byte[signal.length * sampleLength],
			sample;

		for(int i = 0 ; i < signal.length; i++) {

			int
				resultIndex = i * sampleLength;

			sample = writeDataSample(signal[i], sampleLength);

			System.arraycopy(sample, 0, result, resultIndex, sampleLength);
		}

		return result;
	}*/	// ! moved to metaProcessors.FileContentConverter



	/*private boolean coordinatesInRange(int channel, int index){

		if (channel < strips.size()) {

			return index < strips.get(channel).size();
		}

		return false;
	}*/	// coordinates are in range



	public Channeling getStrip(int index){

		return strips.get(index);
	}


	/*public int getSample(int channel, int index){

		if(coordinatesInRange(channel, index))

			return strips.get(channel).get(index).value;

		return 0;
	}*/	// get sample

	/*	public void setSample(int channel, int index, int value){

		if(coordinatesInRange(channel, index))

		strips.get(channel).set(index, new Sample(value));
	}*/	// set sample



		@Override
	public byte[] getSource(int bitsPerSample){

//		return integersToBytes(consolidateChannels(), bitsPerSample);

		return FileContentConverter.integersToBytes(consolidateChannels(), bitsPerSample);
	}

}