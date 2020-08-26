

package data;

import java.util.ArrayList;

public class FileCache extends ArrayList<Wave> {

	static FileCache
		fileCache = new FileCache();

	static int
		currentIndex = 0;

//	--------------------------------------------------------------------------------------------------------------------

	private FileCache( ){ }

//	--------------------------------------------------------------------------------------------------------------------

	public static void addToCache(Wave wave){

		fileCache.add(wave);

		int
			index = fileCache.size() - 1;

		System.out.println(
			"\n\tThe file is added to the cache at index: "
			+ (index) + '\n'
		);
	}

	public static Wave loadFromCache(int index){

		currentIndex = index;

		return fileCache.get(index);
	}

	public static Wave loadCurrent(){

		return loadFromCache(currentIndex);
	}



	public static void updatedCache( ){

		if(currentIndex < fileCache.size() - 1) {

			fileCache.removeRange(currentIndex + 1, fileCache.size());

			System.out.println(
				"\n\tThe cache has been updated. Current index ("
				+ currentIndex
				+ ") is the the last."
			);
		}

		else
			System.out.println("\n\tThe cache is already up-to-date.");
	}

	public static void clearCacheHistory( ){

		if (currentIndex > 0){

			fileCache.removeRange(0, currentIndex);
			currentIndex = 0;

			System.out.println("\n\tHistory is cleared. Current index is 0");
		}

		else

			System.out.println("\n\tHistory is already clear.");
	}

	public static void purgeCache( ){

		fileCache.clear();
		currentIndex = 0;

		System.out.println("\n\tCache is empty.");
	}


//	--------------------------------------------------------------------------------------------------------------------

	public static int getCurrentIndex() {

		return currentIndex;
	}
}

//	@formatter:on