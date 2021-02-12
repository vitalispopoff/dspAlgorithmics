package data;

import data.structure.header.WaveHeader;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.util.List;

import static data.structure.header.WaveFileContentStructure.*;

public abstract class FileCache {


	public static void addToCache(WaveFile waveFile) {

		getFileCache().add(waveFile);
	}

	/*	public static WaveFile getFile(int index) {

		try { return getFileCache().get(index); }

		catch (ArrayIndexOutOfBoundsException e) {

			System.out.println("FileCache> getFile : no file to load");
		}
		return null;
	}*/	// ? unused - disposable ?

	public static WaveFile getFile(){

		try {return getFileCache().get(getFileCache().size() - 1);}

		catch (Exception e) { return null; }
	}

	public static void purgeCache() {

		fileCache.clear();
	}

	// ----- properties ------------------------------------------------------------------------

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

	public static BooleanProperty fileCacheIsEmptyStaticProperty() {

		return fileCacheIsEmpty;
	}

	static {
		fileCacheIsEmpty.bind(fileCache.sizeProperty().isEqualTo(0));
	}

	// ----- bindings --------------------------------------------------------------------------

	private static final IntegerBinding
		currentFileSignalLength = new IntegerBinding(){

		{super.bind(fileCache.sizeProperty());}

		@Override
		protected int computeValue() {

			try {

				WaveHeader
					header = getFile().getHeader();

				int
					channels = header.getField(CHANNELS),
					blockAlign = header.getField(BLOCK_ALIGN),
					dataSize = header.getField(DATA_SIZE);

				return dataSize / (blockAlign * channels);
			}
			catch (Exception e) {return 0;}
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

			try{
				return getFile().getHeader().getField(BITS_PER_SAMPLE);
			}
			catch (Exception e) {return 0;}
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

			try{ return getFile().getHeader().getField(SAMPLE_PER_SEC);}
			catch (Exception e){ return 0;}
		}
	};

	public static int getCurrentFileSamplesPerSecond(){

		return currentFileSamplesPerSecond.get();
	}

	public static IntegerBinding currentFileSamplesPerSecondBinding(){

		return currentFileSamplesPerSecond;
	}

}