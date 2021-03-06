package data;

import data.structure.header.FileHeader;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.util.List;

import static data.structure.WaveFileStructure.*;

public abstract class FileCache {


	public static void addToCache(AudioFile audioFile) {

		getFileCache().add(audioFile);
	}

	/*	public static WaveFile getFile(int index) {

		try { return getFileCache().get(index); }

		catch (ArrayIndexOutOfBoundsException e) {

			System.out.println("FileCache> getFile : no file to load");
		}
		return null;
	}*/	// ? unused - disposable ?

	public static AudioFile getFile() {

		try {return getFileCache().get(getFileCache().size() - 1);}

		catch (Exception e) { return null; }
	}

	public static void purgeCache() {

		fileCache.clear();
	}



	// ----- properties ------------------------------------------------------------------------

	private static final SimpleListProperty<AudioFile>
		fileCache = new SimpleListProperty<>(FXCollections.observableArrayList());

	private static final BooleanProperty
		fileCacheIsEmpty = new SimpleBooleanProperty();


	public static List<AudioFile> getFileCache() {

		return fileCache;
	}

	public static SimpleListProperty<AudioFile> fileCacheProperty() {

		return fileCache;
	}



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

				FileHeader
					header = getFile().getHeader();

				int
					channels = header.getField(CHANNELS),
					blockAlign = header.getField(BLOCK_ALIGN),
					dataSize = header.getField(DATA_SIZE);

				return dataSize / (blockAlign * channels);
			}
			catch (Exception e) {return 0;}
		}
	},

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
	},

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



	public static int getCurrentFileSignalLength(){

		return currentFileSignalLength.get();
	}

	public static IntegerBinding currentFileSignalLengthBinding(){

		return currentFileSignalLength;
	}



	public static int getCurrentFileBitsPerSample(){

		return currentFileBitsPerSample.get();
	}

	public static IntegerBinding currentFileBitsPerSampleBinding(){

		return currentFileBitsPerSample;
	}



	public static int getCurrentFileSamplesPerSecond(){

		return currentFileSamplesPerSecond.get();
	}

	public static IntegerBinding currentFileSamplesPerSecondBinding(){

		return currentFileSamplesPerSecond;
	}
}