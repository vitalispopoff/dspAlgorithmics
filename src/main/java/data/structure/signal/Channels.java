package data.structure.signal;

import java.util.*;

import static algorithms.metaProcessors.FileContentConverter.*;

public class Channels  extends CurrentFilePreview implements Channeling {


	public ArrayList<SamplePyramid> samplePyramid_old = CurrentFilePreview_old.getCurrentSamplePyramid();


	public Channels(int numberOfChannels) {
	}


	Channels(byte[] source, int blockAlign, int numberOfChannels) {

		this(numberOfChannels);

		importToChannels(bytesToIntegers(source, blockAlign / numberOfChannels), numberOfChannels);
	}


	@Override
	public SamplePyramid getChannel(int index) {

		return samplePyramid_old.get(index);    //	!--- TODO to be removed
	}


	//	!--- TODO to be relocated ------------------------------

	private void importToChannels(Integer[] input, int numberOfChannels) {

		if (samplePyramid_old != null && this.samplePyramid_old.size() > 0) {
			samplePyramid_old.clear();
		}

		for (int i = 0; i < numberOfChannels; i++)
			samplePyramid_old.add(SamplePyramid.newInstance());

		int
			index = 0;

		for (Integer i : input)
			samplePyramid_old.get(index++ % numberOfChannels).addSampling(i);
	}

}