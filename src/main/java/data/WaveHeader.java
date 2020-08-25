//	@formatter:off

package data;

import static algorithms.metaProcessors.FileContentConverter.*;
import static data.FileContentStructure.*;

public class WaveHeader {

	public FormatTags
		formatTag;
	
	public int
		fileLength = 0,
		numberOfChannels = 0,
		sampleRate = 0,
		sampleFrameSize = 0,
		bitDepth = 0,
		dataBlockLength = 0;

//	--------------------------------------------------------------------------------------------------------------------
	
	private WaveHeader( ){ }
	
	private WaveHeader(byte[] fileContent){

		this();

		if (fileContent != null){

			this.formatTag = readFormat(fileContent);
			this.fileLength = readDataField(fileContent, FILE_SIZE);
			this.numberOfChannels = readDataField(fileContent, CHANNELS);
			this.sampleRate = readDataField(fileContent, SAMPLING_RATE);
			this.sampleFrameSize = readDataField(fileContent, BLOCK_SIZE);
			this.bitDepth = readDataField(fileContent, BIT_DEPTH);
			this.dataBlockLength = readDataField(fileContent, DATA_SIZE);
		}
	}
	
//	--------------------------------------------------------------------------------------------------------------------

	public static WaveHeader instanceOf(byte[] fileContent){

		if (fileContent != null && fileContent.length > 0)
			return new WaveHeader(fileContent);

		else return new WaveHeader();
	}

//	--------------------------------------------------------------------------------------------------------------------


	public void setFormatTag(FormatTags formatTag){

		this.formatTag = formatTag;
	}

	public FormatTags getFormatTag( ){

		return formatTag;
	}


	public void setFileLength(int fileLength){

		this.fileLength = fileLength;
	}

	public int getFileLength( ){

		return fileLength;
	}



	public void setNumberOfChannels(int numberOfChannels){

		this.numberOfChannels = numberOfChannels;
	}

	public int getNumberOfChannels( ){

		return numberOfChannels;
	}



	public void setSampleRate(int sampleFrameSize){

		this.sampleRate = sampleFrameSize;
	}

	public int getSampleRate( ){

		return sampleRate;
	}



	public void setSampleFrameSize(int sampleFrameSize){

		this.sampleFrameSize = sampleFrameSize;
	}

	public int getSampleFrameSize( ){

		return sampleFrameSize;
	}



	public void setBitDepth(int bitDepth){

		this.bitDepth = bitDepth;
	}

	public int getBitDepth( ){

		return bitDepth;
	}



	public void setDataBlockLength(int dataBlockLength){

		this.dataBlockLength = dataBlockLength;
	}

	public int getDataBlockLength( ){

		return dataBlockLength;
	}

//	--------------------------------------------------------------------------------------------------------------------
	
	@Override
	public String toString( ){

		return
			"format = " + formatTag + '\n'
			+ "fileLength = " + fileLength + '\n'
			+ "numberOfChannels = " + numberOfChannels + '\n'
			+ "sampleRate = " + sampleRate + '\n'
			+ "sampleFrameSize = " + sampleFrameSize + '\n'
			+ "bitDepth = " + bitDepth + '\n'
			+ "dataBlockLength = " + dataBlockLength + "\n}";
	}
}

//	@formatter:on