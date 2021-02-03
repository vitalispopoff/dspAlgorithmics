package data;

import data.structure.Previewing;
import data.structure.signal.*;
//import data.structure.signal.Strip;

import java.util.ArrayList;

public abstract class CurrentFilePreview implements Previewing {

	public static ArrayList<Channeling>
		currentMipMap = new ArrayList<>();

	private static double
		mipMapCopyNumber;

	/*public static void cleanCurrentFileSignal(){
		currentMipMap.clear();
	}*/

	/*public static void loadCurrentFileSignal(){

		currentMipMap.add(FileCache.getFile().getSignal().getChannel(0));

		copyMipMap();
	}*/

	/*private static void copyMipMap(){

		int
			mipMapLastIndex = currentMipMap.size() - 1,
			lastMipMapSize = currentMipMap.get(mipMapLastIndex).size();

		Channeling
			channellSource,
			newChannel;

			// 512 - twice the nearest power of 2 less than app window min. width
		if (lastMipMapSize > 512) {

			channellSource = currentMipMap.get(mipMapLastIndex);
			newChannel = Channeling.newInstance();

			for (int i = 0; i < (lastMipMapSize >> 2); i++){

				int
					j = i << 2;

				Sampling
					a = channellSource.getSampling(j),
					b = channellSource.getSampling(j+1),
					c = channellSource.getSampling(j+2),
					d = channellSource.getSampling(j+3),
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

			currentMipMap.add(newChannel);
			copyMipMap();
		}
	}*/
}