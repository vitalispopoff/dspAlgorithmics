package data.structure;

import java.util.*;

import data.CurrentFilePreview;
import data.FileCache;
import data.structure.signal.Channeling;
import data.structure.signal.Sampling;

public interface Previewing {


	static List<Channeling> getCurrentMipMap(){

		return CurrentFilePreview.currentMipMap;
	}

	static void cleanCurrentFileSignal(){

		getCurrentMipMap().clear();
	}

	static void loadCurrentFileSignal(){

		getCurrentMipMap().add(FileCache.getFile().getSignal().getChannel(0));

		copyMipMap();
	}

	static void copyMipMap(){

		int
			mipMapLastIndex = getCurrentMipMap().size() - 1,
			lastMipMapSize = getCurrentMipMap().get(mipMapLastIndex).size();

		Channeling
			channelSource,
			newChannel;

		// 512 - twice the nearest power of 2 less than app window min. width
		if (lastMipMapSize > 512) {

			channelSource = getCurrentMipMap().get(mipMapLastIndex);
			newChannel = Channeling.newInstance();

			for (int i = 0; i < (lastMipMapSize >> 2); i++){

				int
					j = i << 2;

				Sampling
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

			getCurrentMipMap().add(newChannel);
			copyMipMap();
		}
	}


}
