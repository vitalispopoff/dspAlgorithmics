package data;

import data.structure.Previewing;
import data.structure.signal.*;

import java.util.ArrayList;
import java.util.List;

public abstract class CurrentFilePreview implements Previewing {

	static ArrayList<SamplePyramid>
		currentChan = new ArrayList<>();

	public static List<SamplePyramid> getCurrentChan(){

		return currentChan;
	}

	public static void loadCurrentChan(){


		getCurrentChan().add(FileCache.getFile().getChannelAnchor().getChannel(0));

		constructChanStructure();
	}

	private static void constructChanStructure(){

		int
			chanLastLevel = getCurrentChan().size() - 1,
			chanLastLevelSize = getCurrentChan().get(chanLastLevel).size();

		SamplePyramid
			channelSource,
			newChannel;

		// 512 - twice the nearest power of 2 less than app window min. width
		if (chanLastLevelSize > 512) {

			channelSource = getCurrentChan().get(chanLastLevel);
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

//			for (int i = 0; i < lastMipMapSize ; i += 2) newStrip.add(stripSource.get(i));

			getCurrentChan().add(newChannel);
			constructChanStructure();
		}
	}

}