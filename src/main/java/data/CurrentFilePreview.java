package data;

import data.structure.signal.Strip;

import java.util.ArrayList;

public class CurrentFilePreview {

	public static ArrayList<Strip>
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

		if (lastMipMapSize > 512)	// 512 - twice the nearest power of 2 less than app window min. width
			{

			Strip
				stripSource = currentMipMap.get(mipMapLastIndex),
				newStrip = new Strip();

			for (int i = 0; i < lastMipMapSize ; i += 2) newStrip.add(stripSource.get(i));

/*			for (int i = 0; i < lastMipMapSize; i += 4){

				int
					a = stripSource.get(i),
					b = stripSource.get(i+1),
					c = stripSource.get(i+2),
					d = stripSource.get(i+3),
					min1 = Math.min(a, b),
					min2 = Math.min(c, d),
					min = Math.min(min1, min2),
					max = Math.max(a == min1 ? b : a, c == min2 ? d : c);

			}*/

			currentMipMap.add(newStrip);
			copyMipMap();
		}
	}
}