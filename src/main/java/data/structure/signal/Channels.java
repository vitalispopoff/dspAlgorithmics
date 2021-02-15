package data.structure.signal;

import java.util.*;

import static algorithms.metaProcessors.FileContentConverter.*;

public class Channels implements Channeling {


	public ArrayList<SamplePyramid> _oldSamplePyramid = CurrentFilePreview_old.getCurrentSamplePyramid();

//	public CurrentFilePreview currentFilePreview = CurrentFilePreview.getCurrentSamplePyramid();



	public Channels(int numberOfChannels) {
	}


	Channels(byte[] source, int blockAlign, int numberOfChannels) {

		this(numberOfChannels);

		importToChannels(bytesToIntegers(source, blockAlign / numberOfChannels), numberOfChannels);
	}


	@Override
	public SamplePyramid getChannel(int index) {

		return _oldSamplePyramid.get(index);    //	!--- TODO to be removed
	}


	//	!--- TODO to be relocated ------------------------------

	private void importToChannels(Integer[] input, int numberOfChannels) {

		if (_oldSamplePyramid != null && this._oldSamplePyramid.size() > 0) {
			_oldSamplePyramid.clear();
		}

		for (int i = 0; i < numberOfChannels; i++)
			_oldSamplePyramid.add(SamplePyramid.newInstance());

		int
			index = 0;

		for (Integer i : input)
			_oldSamplePyramid.get(index++ % numberOfChannels).addSampling(i);
	}

}