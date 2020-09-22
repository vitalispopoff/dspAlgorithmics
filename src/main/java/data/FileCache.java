package data;

import data.structure.FileContentStructure;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.util.List;

public abstract class FileCache {

	public static void addToCache(WaveFile waveFile) {

		getFileCache().add(waveFile);
		updateCurrentIndex();
	}

	public static WaveFile getFile(int index) {

		try {

			if (index != getCurrentIndex())
				setCurrentIndex(index);


			return getFileCache().get(index);
		}

		catch (ArrayIndexOutOfBoundsException e) {

			System.out.println("FileCache> getFile : no file to load");
		}

		return null;    // dummy return i hope.
	}

	public static WaveFile getCurrentFile() {

		return getFile(getCurrentIndex());
	}

	public static void purgeCache() {

		fileCache.clear();
		updateCurrentIndex();
	}


	private static final SimpleListProperty<WaveFile>
		fileCache = new SimpleListProperty<>(FXCollections.observableArrayList());

	public static List<WaveFile> getFileCache() {

		return fileCache;
	}

	public static SimpleListProperty<WaveFile> fileCacheProperty() {

		return fileCache;
	}


	private static final BooleanProperty
		cacheIsEmpty = new SimpleBooleanProperty();

	public static boolean getCacheIsEmpty() {

		return cacheIsEmpty.get();
	}

	public static BooleanProperty cacheIsEmptyStaticProperty() {

		return cacheIsEmpty;
	}

	static {
		cacheIsEmpty.bind(fileCache.sizeProperty().isEqualTo(0));
	}



	private static final IntegerProperty
		currentIndex = new SimpleIntegerProperty(-1);

	public static int getCurrentIndex() {

		return currentIndex.get();
	}

	public static void setCurrentIndex(int value) {

		currentIndex.set(value);
	}

	private static void updateCurrentIndex() {

		setCurrentIndex(fileCache.size() - 1);
	}

	public static IntegerProperty currentIndexProperty() {

		return currentIndex;
	}


	private static final IntegerBinding
		temporalBinding = new IntegerBinding() {

		{
			super.bind(
				fileCache.sizeProperty()
			);
		}


		@Override
		protected int computeValue() {

			return fileCache.size() > 0 ? getFile(0).getHeader().getField(FileContentStructure.BITS_PER_SAMPLE) : 0;
		}
	};

	public static int getTemporalBinding(){

		return temporalBinding.get();
	}

	public static IntegerBinding temporalBindingProperty(){

		return temporalBinding;
	}

	static {

		temporalBinding.addListener((observable, oldValue, newValue) -> {

			System.out.println(newValue);
		});
	}

}