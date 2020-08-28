package data.structure;

import algorithms.metaProcessors.FileContentConverter;

import java.util.*;

import static algorithms.metaProcessors.FileContentConverter.writeDataSample;

public class Signal extends ArrayList<Integer>{

	public ArrayList<Strip>
		strips;

//	--------------------------------------------------------------------------------------------------------------------

	public Signal(){

		strips = new ArrayList<>();
	}

	public Signal(int channels){

		this();

		for (int i = 0; i < channels; i++)

			addChannel();
	}

	public Signal(byte[] source, int sampleSize, int channels){

		this(channels);

		importToStrips(bytesToIntegers(source, sampleSize), channels);
	}

//	--------------------------------------------------------------------------------------------------------------------

	public void addChannel(){

		strips.add(new Strip());
	}

	public void removeChannel(){

		removeChannel(strips.size() - 1);
	}

	public void removeChannel(int index){

		strips.remove(index);
	}



	public void addSilence(int channel, int fromIndex, int length){

		if(coordinatesInRange(channel, fromIndex)){

			strips.get(channel).addAll(fromIndex, Arrays.asList(new Integer[length]));
		}
	}

	public void removeSamples(int channel, int fromIndex, int toIndex){

		if(coordinatesInRange(channel, fromIndex) && coordinatesInRange(channel, toIndex + 1))

			strips.get(channel).removeSamples(fromIndex, toIndex + 1);
	}



	void importToStrips(Integer[] input, int numberOfChannels){

		if (strips != null && strips.size() > 0)

			strips.clear();

		for (int i = 0; i < numberOfChannels; i++)

			strips.add(new Strip());

		int
			index = 0;

		for (Integer i : input)

			strips.get(index++ % numberOfChannels).add(i);
	}		// * tested

	Integer[] bytesToIntegers(byte[] source, int sampleSize){

		Strip
			strip = new Strip();

		int
			stripLength = source.length / sampleSize;

		Integer[]
			result = new Integer[stripLength];

		for (int i = 0; i < stripLength; i++)

			result[i] =  FileContentConverter.readDataSample(source, i * sampleSize, sampleSize);

		return result;
	}		// * tested



	Integer[] consolidateChannels(){

		int
			numberOfChannels = strips.size(),
			numberOfSamples = strips.get(0).size(),
			resultLength = numberOfChannels * numberOfSamples;

		Strip
			sum = new Strip();

		Integer[]
			result = new Integer[resultLength];

		for (int i = 0; i < resultLength; i++) {

			int
				channelIndex = i % numberOfChannels,
				sampleIndex = (i - channelIndex) / numberOfChannels;

			result[i] = strips.get(channelIndex).get(sampleIndex);
		}

		return result;
	}								// * tested

	byte[] integersToBytes(Integer[] signal, int sampleSize){

		byte[]
			result = new byte[signal.length * sampleSize],
			sample;

		for(int i = 0 ; i < signal.length; i++) {

			int
				resultIndex = i * sampleSize;

			sample = writeDataSample(signal[i], sampleSize);

			System.arraycopy(sample, 0, result, resultIndex, sampleSize);
		}

		return result;
	}		// * tested



	private boolean coordinatesInRange(int channel, int index){

		if (channel < strips.size()) {

			return index < strips.get(channel).size();
		}

		return false;
	}

//	--------------------------------------------------------------------------------------------------------------------

	public int getSample(int channel, int index){

		if(coordinatesInRange(channel, index))

			return strips.get(channel).get(index);

		return 0;
	}

	public void setSample(int channel, int index, int value){

		if(coordinatesInRange(channel, index))

		strips.get(channel).set(index, value);
	}



	public byte[] getSource(int sampleSize){

		return integersToBytes(consolidateChannels(), sampleSize);
	}

//	--------------------------------------------------------------------------------------------------------------------

	class Strip extends ArrayList<Integer>{

		public void removeSamples(int fromIndex, int toIndex){

			super.removeRange(fromIndex, toIndex);
		}

	}
}