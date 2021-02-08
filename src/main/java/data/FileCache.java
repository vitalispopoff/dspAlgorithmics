package data;

import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.util.List;

import static data.structure.WaveFileContentStructure.*;

public abstract class FileCache {

//	public static CurrentFilePreview currentFilePreview;

	public static void addToCache(WaveFile waveFile) {

		getFileCache().add(waveFile);
//		if (getFileCache().size() == 1) setCurrentIndex(0);
	}

	public static WaveFile getFile(int index) {

		try {

/*			if (index != getCurrentIndex())
				setCurrentIndex(index);*/
			return getFileCache().get(index);
		}

		catch (ArrayIndexOutOfBoundsException e) {

			System.out.println("FileCache> getFile : no file to load");
		}

		return null;    // dummy return i hope.
	}

	public static WaveFile getFile(){

		if (getFileCacheIsNotEmpty()) return getFileCache().get(getFileCache().size() - 1);
		else return null;
	}

	public static void purgeCache() {

		fileCache.clear();
//		updateCurrentIndex();
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
		fileCacheIsEmpty = new SimpleBooleanProperty();

	public static boolean getFileCacheIsNotEmpty() {

		return !fileCacheIsEmpty.get();
	}

	public static BooleanProperty fileCacheIsEmptyStaticProperty() {

		return fileCacheIsEmpty;
	}

	static {
		fileCacheIsEmpty.bind(fileCache.sizeProperty().isEqualTo(0));
	}



	private static final IntegerBinding
		currentFileSignalLength = new IntegerBinding(){

		{
			super.bind(fileCache.sizeProperty());

		}

		@Override
		protected int computeValue() {

			return
				getFileCacheIsNotEmpty()
				? getFile().getChannelingData().getChannel(0).size()
				: 0;
		}
	};

	public static int getCurrentFileSignalLength(){

		return currentFileSignalLength.get();
	}

	public static IntegerBinding currentFileSignalLengthBinding(){

		return currentFileSignalLength;
	}



	private static final IntegerBinding
		currentFileBitsPerSample = new IntegerBinding() {

		{
			super.bind(fileCache.sizeProperty());
		}


		@Override
		protected int computeValue() {

			return
				getFileCacheIsNotEmpty()
				? getFile().getHeader().getField(BITS_PER_SAMPLE)
				: 0;
		}
	};

	public static int getCurrentFileBitsPerSample(){

		return currentFileBitsPerSample.get();
	}

	public static IntegerBinding currentFileBitsPerSampleBinding(){

		return currentFileBitsPerSample;
	}



	private static final IntegerBinding
		currentFileSamplesPerSecond = new IntegerBinding(){

		{
			super.bind(fileCache.sizeProperty());
		}

		@Override
		protected int computeValue() {

			return
				getFileCacheIsNotEmpty()
				? getFile().getHeader().getField(SAMPLE_PER_SEC)
				: 0;
		}
	};

	public static int getCurrentFileSamplesPerSecond(){

		return currentFileSamplesPerSecond.get();
	}

	public static IntegerBinding currentFileSamplesPerSecondBinding(){

		return currentFileSamplesPerSecond;
	}

}

/*	private static final IntegerProperty
		currentIndex = new SimpleIntegerProperty(-1);

	public static int getCurrentIndex() {

		return currentIndex.get();
	}

	public static void setCurrentIndex(int value) {

		currentIndex.set(value);
	}

	private static void updateCurrentIndex() {

		setCurrentIndex(getFileCache().size() - 1);
	}

	public static IntegerProperty currentIndexProperty() {

		return currentIndex;
	}*/	// TODO - current index property to be fixed