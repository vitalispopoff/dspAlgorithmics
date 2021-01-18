package data.structure.signal;

import algorithms.metaProcessors.FileContentConverter;

import java.util.*;

import static algorithms.metaProcessors.FileContentConverter.writeDataSample;

public class Signal extends ArrayList<Integer>{

	public ArrayList<Strip>
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

	public void removeChannel(){

		removeChannel(strips.size() - 1);
	}

	public void removeChannel(int index){

		strips.remove(index);
	}



	/*	public void addSilence(int channel, int fromIndex, int length){

		if(coordinatesInRange(channel, fromIndex)){

			strips.get(channel).addAll(fromIndex, Arrays.asList(new Integer[length]));
		}
	}*/	// add silence - disable

	/*public void removeSamples(int channel, int fromIndex, int toIndex){

		if(coordinatesInRange(channel, fromIndex) && coordinatesInRange(channel, toIndex + 1))
			strips.get(channel).removeSamples(fromIndex, toIndex + 1);
	}*/	// remove samples - disabled



	void importToStrips(Integer[] input, int channels){

		if (strips != null && strips.size() > 0)

			strips.clear();

		for (int i = 0; i < channels; i++)

			strips.add(new Strip());

		int
			index = 0;

		for (Integer i : input)

			strips.get(index++ % channels).add(new Sample(i));
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

			result[i] = strips.get(channelIndex).get(sampleIndex).value;
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



	private boolean coordinatesInRange(int channel, int index){

		if (channel < strips.size()) {

			return index < strips.get(channel).size();
		}

		return false;
	}



	public Strip getStrip(int index){

		return strips.get(index);
	}


	public int getSample(int channel, int index){

		if(coordinatesInRange(channel, index))

			return strips.get(channel).get(index).value;

		return 0;
	}

	public void setSample(int channel, int index, int value){

		if(coordinatesInRange(channel, index))

		strips.get(channel).set(index, new Sample(value));
	}



	public byte[] getSource(int bitsPerSample){

		return integersToBytes(consolidateChannels(), bitsPerSample);
	}

}