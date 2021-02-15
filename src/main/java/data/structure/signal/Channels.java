package data.structure.signal;

import java.util.ArrayList;

import static algorithms.metaProcessors.FileContentConverter.*;

public class Channels implements Channeling {


	public ArrayList<SamplePyramid>
		currentSamplePyramid = new ArrayList<>();


	Channels(byte[] source, int blockAlign, int numberOfChannels) {

		this(numberOfChannels);

		importToChannels(bytesToIntegers(source, blockAlign / numberOfChannels), numberOfChannels);

		populateSamplePyramid();
	}

	private Channels(int numberOfChannels) {}


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

	private void populateSamplePyramid(){

		int
			chanLastLevel = currentSamplePyramid.size() - 1,
			chanLastLevelSize = currentSamplePyramid.get(chanLastLevel).size();

		SamplePyramid
			channelSource,
			newChannel;

		if (chanLastLevelSize > 512) {

			channelSource = currentSamplePyramid.get(chanLastLevel);
			newChannel = SamplePyramid.newInstance();

			for (int i = 0; i < (chanLastLevelSize >> 2); i++){

				int
					j = i << 2;

				AudioData
					a = channelSource.getSampling(j),
					b = channelSource.getSampling(j+1),
					c = channelSource.getSampling(j+2),
					d = channelSource.getSampling(j+3),
					min1 = a.getValue() - b.getValue() > 0 ? b : a,
					min2 = c.getValue() - d.getValue() > 0 ? d : c,
					max1 = min1 == a ? b : a,
					max2 = min2 == c ? d : c,
					min = min2.getValue() - min1.getValue() > 0 ? min1 : min2,
					max = max2.getValue() - max1.getValue() > 0 ? max2 : max1;

				if (min == a || max == a) newChannel.addSampling(a);
				if (min == b || max == b) newChannel.addSampling(b);
				if (min == c || max == c) newChannel.addSampling(c);
				if (min == d || max == d) newChannel.addSampling(d);
			}

			currentSamplePyramid.add(newChannel);
			populateSamplePyramid();
		}
	}


	@Override
	public SamplePyramid getSampleLevel(int index) {

		return currentSamplePyramid.get(index);    //	!--- TODO to be removed
	}
}