package data;

import data.structure.Strip;

import java.util.ArrayList;

public class CurrentFilePreview {

	public static ArrayList<Strip>
		currentMipMap = new ArrayList<>();

	private static double
		mipMapCopyNumber;

	public static void loadCurrentFileSignal(){

		currentMipMap.add(FileCache.getFile().getSignal().getStrip(0));

		copyMipMap();

		System.out.println(currentMipMap.size());
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

			currentMipMap.add(newStrip);

			for (int i = 0; i < lastMipMapSize ; i += 2){

				newStrip.add(stripSource.get(i));
			}
			copyMipMap();
		}
	}
}