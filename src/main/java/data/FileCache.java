package data;

import javafx.beans.property.*;
import javafx.collections.FXCollections;

public abstract class FileCache{

	public static void addToCache(WaveFile waveFile){

		fileCache.add(waveFile);
		updateCurrentIndex();
	}

	public static WaveFile getFile(int index){

		try {

			if (index != getCurrentIndex())

				setCurrentIndex(index);

			return fileCache.get(index);
		}

		catch (ArrayIndexOutOfBoundsException e){

			System.out.println("FileCache> getFile : no file to load");
		}

		return null;	// dummy return i hope.
	}

	public static WaveFile getCurrentFile(){

		return getFile(getCurrentIndex());
	}

	public static void purgeCache( ){

		fileCache.clear();
		updateCurrentIndex();
	}



	private static final SimpleListProperty<WaveFile>
		fileCache = new SimpleListProperty<>(FXCollections.observableArrayList());

	public static SimpleListProperty<WaveFile> getFileCache(){

		return fileCache;
	}

	public static SimpleListProperty<WaveFile> fileCacheProperty(){

		return fileCache;
	}



	private static final BooleanProperty
		cacheIsEmpty = new SimpleBooleanProperty();

	public static boolean getCacheIsEmpty(){

		return cacheIsEmpty.get();
	}

	public static BooleanProperty cacheIsEmptyStaticProperty(){

		return cacheIsEmpty;
	}

	static {cacheIsEmpty.bind(fileCache.sizeProperty().isEqualTo(0));}



	private static final IntegerProperty
		currentIndex = new SimpleIntegerProperty(-1);

	public static int getCurrentIndex() {

		return currentIndex.get();
	}

	public static void setCurrentIndex(int value){

		currentIndex.set(value);
	}

	private static void updateCurrentIndex(){

		setCurrentIndex(fileCache.size() - 1);
	}

	public static IntegerProperty currentIndexProperty(){

		return currentIndex;
	}
}