package data.structure.signal;

import static algorithms.metaProcessors.FileContentConverter.*;

public class Channels  extends CurrentFilePreview implements Channeling {



	Channels(byte[] source, int blockAlign, int numberOfChannels) {

		this(numberOfChannels);

		importToChannels(bytesToIntegers(source, blockAlign / numberOfChannels), numberOfChannels);

		populateSamplePyramid();
	}

	private Channels(int numberOfChannels) {}


	@Override
	public SamplePyramid getSampleLevel(int index) {

		return currentSamplePyramid.get(index);    //	!--- TODO to be removed
	}


	//	!--- TODO to be relocated ------------------------------

	private void importToChannels(Integer[] input, int numberOfChannels) {

		if (currentSamplePyramid != null && this.currentSamplePyramid.size() > 0) {
			currentSamplePyramid.clear();
		}

		for (int i = 0; i < numberOfChannels; i++)
			currentSamplePyramid.add(SamplePyramid.newInstance());

		int
			index = 0;

		for (Integer i : input)
			currentSamplePyramid.get(index++ % numberOfChannels).addSampling(i);
	}

}