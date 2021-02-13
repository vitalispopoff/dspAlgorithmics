package data.structure;

import data.FileCache;
import data.structure.signal.*;

import java.util.ArrayList;
import java.util.List;

public abstract class CurrentFilePreview_0 implements Previewing {

	public static ArrayList<SamplePyramid>
		currentSamplePyramid = new ArrayList<>();

	public static ArrayList<SamplePyramid> getCurrentSamplePyramid(){

		return currentSamplePyramid;
	}


	public static List<SamplePyramid> getCurrentChannel(){

		return currentSamplePyramid;
	}

	public static void addLevelToCurrentSamplePyramid(){

		currentSamplePyramid.add(FileCache.getFile().getChannelAnchor().getChannel(0));
		populateSamplePyramid();
	}

	private static void populateSamplePyramid(){

		int
			chanLastLevel = getCurrentChannel().size() - 1,
			chanLastLevelSize = getCurrentChannel().get(chanLastLevel).size();

		SamplePyramid
			channelSource,
			newChannel;

		if (chanLastLevelSize > 512) {

			channelSource = getCurrentChannel().get(chanLastLevel);
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

			getCurrentChannel().add(newChannel);
			populateSamplePyramid();
		}
	}
}