//	@formatter.off

package data;

import java.util.ArrayList;

public class FileCache extends ArrayList<Wave> {

	static FileCache
		fileCache = new FileCache();

	static int
		currentFileIndex = 0;

//	--------------------------------------------------------------------------------------------------------------------

	private FileCache( ){ }

//	--------------------------------------------------------------------------------------------------------------------

	public static void addToCache(Wave wave){

		fileCache.add(wave);

		System.out.println(
			"\n\tThe file is added to the cache at index: "
			+ (fileCache.size() - 1)
		);
	}

	public static Wave loadFromCache(int index){

		currentFileIndex = index;

		return fileCache.get(index);
	}



	public static void updatedCache( ){

		if(currentFileIndex < fileCache.size() - 1) {

			fileCache.removeRange(currentFileIndex + 1, fileCache.size());

			System.out.println(
				"\n\tThe cache has been updated. Current index ("
				+ currentFileIndex
				+ ") is the the last."
			);
		}

		else
			System.out.println("\n\tThe cache is already up-to-date.");
	}

	public static void clearCacheHistory( ){

		if(currentFileIndex > 0){

			fileCache.removeRange(0, currentFileIndex);

			currentFileIndex = 0;

			System.out.println("\n\tHistory is cleared. Current incdex is 0");
		}

		else
			System.out.println("\n\tHistory is already clear.");
	}

	public static void purgeCache( ){

		fileCache.clear();
		currentFileIndex = 0;

		System.out.println("\n\tCache is empty.");
	}
}

//	@formatter:on