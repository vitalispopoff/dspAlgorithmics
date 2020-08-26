package data;

import java.util.Arrays;
import static algorithms.metaProcessors.FileContentConverter.*;

public class WaveHeader {

	private byte[]
		source;

/*	public FormatTags
		formatTag;

	public int

		fileSize = 0,

		fmtSize = 0,

		channels = 0,
		samplePerSec = 0,
		avgBytesPerSec = 0,
		blockAlign = 0,
		bitsPerSample =0,

		dataSize =0;*/		// ? disposable ?

//	--------------------------------------------------------------------------------------------------------------------
	
	private WaveHeader( ){ }
	
	private WaveHeader(byte[] fileContent){

		this();

		if (fileContent != null){

			source = Arrays.copyOfRange(fileContent, 0, 44);

/*			fileSize = readDataField(fileContent, FILE_SIZE);

			fmtSize = readDataField(fileContent, FMT_SIZE);
			formatTag = readFormatTag(fileContent);
			channels = readDataField(fileContent, CHANNELS);
			samplePerSec = readDataField(fileContent, SAMPLE_PER_SEC);
			avgBytesPerSec = readDataField(fileContent, BLOCK_ALIGN);
			blockAlign = readDataField(fileContent, DATA_SIZE);
			bitsPerSample = readDataField(fileContent, BITS_PER_SAMPLE);
			dataSize = readDataField(fileContent, DATA_SIZE);*/		// ? disposable ?

		}
	}
	
//	--------------------------------------------------------------------------------------------------------------------

	public static WaveHeader instanceOf(byte[] fileContent){

		if (fileContent != null && fileContent.length > 0)

			return new WaveHeader(fileContent);

		else return new WaveHeader();
	}

//	--------------------------------------------------------------------------------------------------------------------

	public int getField(FileContentStructure field){

		return readDataField(source, field);
	}

	public void setField(int value, FileContentStructure field){

		writeDataField(source, value, field);
	}

	//	--------------------------------------------------------------------------------------------------------------------

}