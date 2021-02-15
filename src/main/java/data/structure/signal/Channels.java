package data.structure.signal;

import java.util.*;

import static algorithms.metaProcessors.FileContentConverter.*;

public class Channels  extends CurrentFilePreview implements Channeling {


//	public ArrayList<SamplePyramid> currentSamplePyramid = CurrentFilePreview_old.getCurrentSamplePyramid();


	public Channels(int numberOfChannels) {
	}


	Channels(byte[] source, int blockAlign, int numberOfChannels) {

		this(numberOfChannels);

		importToChannels(bytesToIntegers(source, blockAlign / numberOfChannels), numberOfChannels);

		populateSamplePyramid();
	}


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