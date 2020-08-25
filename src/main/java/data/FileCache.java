

package data;

import algorithms.metaProcessors.FileManager;

import java.io.File;
import java.util.ArrayList;

import static algorithms.metaProcessors.FileContentConverter.*;
import static data.FileContentStructure.*;

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



	public static void exportCurrentFile(){

		Wave
			source = loadFromCache(currentFileIndex);

		WaveHeader
			header = source.header;

		int
			fileLength = source.header.getFileSize() + 8,
			signalLength = 0;

/*		byte[]
			export = new byte[fileLength],

			fileSize = writeDataField(header.getFileSize(), FILE_SIZE),
			channels = writeDataField(header.getChannels(), CHANNELS),
			samplePerSec = writeDataField(header.getChannels(), SAMPLE_PER_SEC),
			avBytePerSec = writeDataField(header.getAvgBytesPerSec(), AV_BYTE_PER_SEC),
			blockAlign = writeDataField(header.getBlockAlign(), BLOCK_ALIGN),
			bitsPerSample = writeDataField(header.getBitsPerSample(), BITS_PER_SAMPLE),

			dataSize = writeDataField(header.getDataSize(), DATA_SIZE);*/

/*		byte[][]
			fields = {

			WaveHeader.getFileId(),
			fileSize,
			WaveHeader.getWaveId(),

			WaveHeader.getFmt_Id(),
			new byte[FMT_SIZE.getLength()],
			header.getFormatTag().getBytes(),
			channels,
			samplePerSec,
			avBytePerSec,
			blockAlign,

			WaveHeader.getDataId(),
			dataSize,
			writeSignalChannels(source.getChannelSignals(), header.getBitsPerSample())
		};*/

/*		for (int i = 0; i < fields.length ; i++) {

			int
				start = values()[i].getStart(),
				length = values()[i] == SIGNAL
					? header.getDataSize()
					: values()[i].getLength();

			System.arraycopy(fields[i], 0, export, start, length);
		}*/

//		return export;
	}
}

//	@formatter:on