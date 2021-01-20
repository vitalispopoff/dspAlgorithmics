package data.structure.signal;

import algorithms.metaProcessors.FileContentConverter;

import java.util.*;

import static algorithms.metaProcessors.FileContentConverter.writeDataSample;

public class Signal extends ArrayList<Integer>{

	public ArrayList<Stripable>
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



	public void addChannel(){

		strips.add(new Strip());
	}

	/*public void removeChannel(){

		removeChannel(strips.size() - 1);
	}*/	// remove channel

	/*public void removeChannel(int index){

		strips.remove(index);
	}*/	// remove channel



	void importToStrips(Integer[] input, int channels){

		if (strips != null && strips.size() > 0)

			strips.clear();

		for (int i = 0; i < channels; i++)

			strips.add(new Strip());

		int
			index = 0;

		for (Integer i : input)

			strips.get(index++ % channels).addSample(new Sample(i));
	}



	Integer[] bytesToIntegers(byte[] source, int sampleLength){

		Strip
			strip = new Strip();

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

		Strip
			sum = new Strip();

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

	byte[] integersToBytes(Integer[] signal, int bitsPerSample){

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
	}



	/*private boolean coordinatesInRange(int channel, int index){

		if (channel < strips.size()) {

			return index < strips.get(channel).size();
		}

		return false;
	}*/	// coordinates are in range



	public Strip getStrip(int index){

		return (Strip) strips.get(index);
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



	public byte[] getSource(int bitsPerSample){

		return integersToBytes(consolidateChannels(), bitsPerSample);
	}

}