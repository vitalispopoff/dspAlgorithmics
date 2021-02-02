package data;

import data.structure.signal.*;
//import data.structure.signal.Strip;

import java.util.ArrayList;

public class CurrentFilePreview {

	public static ArrayList<Channeling>
		currentMipMap = new ArrayList<>();

	private static double
		mipMapCopyNumber;

	public static void cleanCurrentFileSignal(){
		currentMipMap.clear();
	}

	public static void loadCurrentFileSignal(){

		currentMipMap.add(FileCache.getFile().getSignal().getStrip(0));

		copyMipMap();

//		System.out.println("	data.CurrentFilePreview.loadCurrentFileSignal : mipmap size = " + currentMipMap.size());
	}

	private static void copyMipMap(){

		int
			mipMapLastIndex = currentMipMap.size() - 1,
			lastMipMapSize = currentMipMap.get(mipMapLastIndex).size();

		Channeling
			stripSource,
			newStrip;

			// 512 - twice the nearest power of 2 less than app window min. width
		if (lastMipMapSize > 512) {

			stripSource = currentMipMap.get(mipMapLastIndex);
			newStrip = new Channel();

			for (int i = 0; i < (lastMipMapSize >> 2); i++){

				int
					j = i << 2;

				Sampleable
					a = stripSource.get(j),
					b = stripSource.get(j+1),
					c = stripSource.get(j+2),
					d = stripSource.get(j+3),
					min1 = a.getValue() - b.getValue() > 0 ? b : a,
					min2 = c.getValue() - d.getValue() > 0 ? d : c,
					max1 = min1 == a ? b : a,
					max2 = min2 == c ? d : c,
					min = min2.getValue() - min1.getValue() > 0 ? min1 : min2,
					max = max2.getValue() - max1.getValue() > 0 ? max2 : max1;

				if (min == a || max == a) newStrip.addSample(a);
				if (min == b || max == b) newStrip.addSample(b);
				if (min == c || max == c) newStrip.addSample(c);
				if (min == d || max == d) newStrip.addSample(d);
			}

//			for (int i = 0; i < lastMipMapSize ; i += 2) newStrip.add(stripSource.get(i));

			currentMipMap.add(newStrip);
			copyMipMap();
		}
	}
}