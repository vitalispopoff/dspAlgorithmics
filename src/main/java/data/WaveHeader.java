//	@formatter:off

package data;

import static algorithms.metaProcessors.FileContentConverter.*;
import static data.FileContentStructure.*;

public class WaveHeader {

	static byte[]
		fileId = {(byte) 0x52, (byte) 0x49, (byte) 0x46, (byte) 0x46},
		waveId = {(byte) 0x57, (byte) 0x41, (byte) 0x56, (byte) 0x45},
		fmt_Id = {(byte) 0x66, (byte) 0x6D, (byte) 0x74, (byte) 0x20},
		dataId = {(byte) 0x64, (byte) 0x61, (byte) 0x74, (byte) 0x61};

	public FormatTags
		formatTag;

	public int
		fileSize = 0,

		fmtSize = 0,
		channels = 0,
		samplePerSec = 0,
		avgBytesPerSec = 0,
		bitsPerSec = 0,
		blockAlign = 0,
		bitsPerSample =0,

		dataSize =0;

//	--------------------------------------------------------------------------------------------------------------------
	
	private WaveHeader( ){ }
	
	private WaveHeader(byte[] fileContent){

		this();

		if (fileContent != null){

			this.formatTag = readFormatTag(fileContent);
			this.fileSize = readDataField(fileContent, FILE_SIZE);

			this.fmtSize = readDataField(fileContent, FMT_SIZE);
			this.channels = readDataField(fileContent, CHANNELS);
			this.samplePerSec = readDataField(fileContent, SAMPLE_PER_SEC);
			this.avgBytesPerSec = readDataField(fileContent, BLOCK_ALIGN);
			this.bitsPerSec = readDataField(fileContent, BITS_PER_SAMPLE);
			this.blockAlign = readDataField(fileContent, DATA_SIZE);
			this.bitsPerSample = readDataField(fileContent, BITS_PER_SAMPLE);
			this.dataSize = readDataField(fileContent, DATA_SIZE);
		}
	}
	
//	--------------------------------------------------------------------------------------------------------------------

	public static WaveHeader instanceOf(byte[] fileContent){

		if (fileContent != null && fileContent.length > 0)
			return new WaveHeader(fileContent);

		else return new WaveHeader();
	}

//	--------------------------------------------------------------------------------------------------------------------

	public static byte[] getFileId() {

		return fileId;
	}

	public static byte[] getWaveId() {

		return waveId;
	}

	public static byte[] getDataId() {

		return dataId;
	}

	public static byte[] getFmt_Id() {

		return fmt_Id;
	}



	public void setFormatTag(FormatTags formatTag){

		this.formatTag = formatTag;
	}

	public FormatTags getFormatTag( ){

		return formatTag;
	}



	public void setFileSize(int fileSize){

		this.fileSize = fileSize;
	}

	public int getFileSize( ){

		return fileSize;
	}



	public void setChannels(int channels){

		this.channels = channels;
	}

	public int getChannels( ){

		return channels;
	}



	public void setSamplePerSec(int sampleFrameSize){

		this.samplePerSec = sampleFrameSize;
	}

	public int getSamplePerSec( ){

		return samplePerSec;
	}



	public void setAvgBytesPerSec(int avgBytesPerSec){

		this.avgBytesPerSec = avgBytesPerSec;
	}

	public int getAvgBytesPerSec( ){

		return avgBytesPerSec;
	}



	public void setBitsPerSec(int bitsPerSec){

		this.bitsPerSec = bitsPerSec;
	}

	public int getBitsPerSec( ){

		return bitsPerSec;
	}



	public void setBlockAlign(int blockAlign){

		this.blockAlign = blockAlign;
	}

	public int getBlockAlign( ){

		return blockAlign;
	}



	public int getBitsPerSample() {

		return bitsPerSample;
	}

	public void setBitsPerSample(int bitsPerSample) {

		this.bitsPerSample = bitsPerSample;
	}



	public int getDataSize() {

		return dataSize;
	}

	public void setDataSize(int dataSize) {

		this.dataSize = dataSize;
	}

	//	--------------------------------------------------------------------------------------------------------------------
	
	@Override
	public String toString( ){

		return
			"format = " + formatTag + '\n'
			+ "fileLength = " + fileSize + '\n'
			+ "numberOfChannels = " + channels + '\n'
			+ "sampleRate = " + samplePerSec + '\n'
			+ "sampleFrameSize = " + avgBytesPerSec + '\n'
			+ "bitDepth = " + bitsPerSec + '\n'
			+ "dataBlockLength = " + blockAlign + "\n";
	}
}

//	@formatter:on