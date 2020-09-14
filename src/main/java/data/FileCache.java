package data;

import javafx.beans.property.*;
import java.util.ArrayList;

public class FileCache extends ArrayList<WaveFile> {

	public static FileCache
		fileCache = new FileCache();

	private static final IntegerProperty
		currentIndexDue = new SimpleIntegerProperty(-1);

//	--------------------------------------------------------------------------------------------------------------------

	private FileCache(){ }

//	--------------------------------------------------------------------------------------------------------------------

	public static void addToCache(WaveFile waveFile){

		fileCache.add(waveFile);
		updateCurrentIndex();

		System.out.println(
			"\n\tThe file is added to the cache at index: " + (getCurrentIndex()) + '\n');
	}



	public static WaveFile loadFromCache(int index){

//		if(index >= 0 && index < fileCache.size())

		try {

			if (index != getCurrentIndex())

				setCurrentIndex(index);

			return fileCache.get(index);
		}

		catch (ArrayIndexOutOfBoundsException e){

			System.out.println("WaveFile> loadFromCache> no file to load");
		}

		return null;	// dummy return i hope.
	}

	public static WaveFile loadCurrent(){

		return loadFromCache(getCurrentIndex());
	}



	public static void updatedCache( ){

		int
			cI = getCurrentIndex();

		if(cI < fileCache.size() - 1) {

			fileCache.removeRange(cI + 1, fileCache.size());

			System.out.println(
				"\n\tThe cache has been updated. Current index (" + cI + ") is the the last.");
		}

		else
			System.out.println("\n\tThe cache is already up-to-date.");
	}

	public static void clearCacheHistory( ){

		int
			cI = getCurrentIndex();

		if (cI > 0){

			fileCache.removeRange(0, cI);
			setCurrentIndex(0);

			System.out.println("\n\tHistory is cleared. Current index is 0");
		}

		else

			System.out.println("\n\tHistory is already clear.");
	}

	public static void purgeCache( ){

		fileCache.clear();
//		updateCurrentIndex();

		System.out.println("TEMPORAL : fileCache.size = " + fileCache.size());

		System.out.println("FileCache>\n\tCache is empty.");
	}

//	--------------------------------------------------------------------------------------------------------------------

	public static int getCurrentIndex() {

		return currentIndexDue.get();
	}

	public static void setCurrentIndex(int value){

		currentIndexDue.set(value);
	}

	public static IntegerProperty currentIndexDueProperty(){

		return currentIndexDue;
	}

	private static void updateCurrentIndex(){

		setCurrentIndex(fileCache.size() - 1);
	}
}