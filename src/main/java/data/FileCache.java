package data;

import javafx.beans.property.*;
import javafx.collections.FXCollections;

public abstract class FileCache{

	public static SimpleListProperty<WaveFile>
		fileCache = new SimpleListProperty<>(FXCollections.observableArrayList());

	private static final BooleanProperty
		cacheIsEmpty = new SimpleBooleanProperty(/*fileCache.size() == 0*/);

	static {cacheIsEmpty.bind(fileCache.sizeProperty().isEqualTo(0));}

	private static final IntegerProperty
		currentIndexDue = new SimpleIntegerProperty(-1);



	public static void addToCache(WaveFile waveFile){

		fileCache.add(waveFile);
		updateCurrentIndex();

		System.out.println(
			"\n\tThe file is added to the cache at index: " + (getCurrentIndex()) + '\n');
	}

	public static WaveFile loadFromCache(int index){

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

	public static void purgeCache( ){

		fileCache.clear();
		updateCurrentIndex();

		System.out.println("TEMPORAL : fileCache.size = " + fileCache.size());

		System.out.println("FileCache>\n\tCache is empty.");
	}



	public static boolean cacheIsEmpty(){

		return cacheIsEmpty.get();
	}

	public final boolean getCacheIsEmptyDue(){

		return cacheIsEmpty.get();
	}

	public final BooleanProperty cacheIsEmptyProperty(){

		return cacheIsEmpty;

	}

	public static BooleanProperty cacheIsEmptyStaticProperty(){

		return cacheIsEmpty;
	}



	public static int getCurrentIndex() {

		return currentIndexDue.get();
	}

	public static void setCurrentIndex(int value){

		currentIndexDue.set(value);
	}

	public static IntegerProperty currentIndexProperty(){

		return currentIndexDue;
	}

	private static void updateCurrentIndex(){

		setCurrentIndex(fileCache.size() - 1);
	}
}