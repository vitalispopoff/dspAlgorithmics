//	@formatter:off

package data;

import static data.FileContentStructure.*;
import static algorithms.metaProcessors.FileContentConverter.*;

public class WaveHeader {

	static final byte[]
		fileId = {(byte) 0x52, (byte) 0x49, (byte) 0x46, (byte) 0x46},		// "RIFF"
		waveId = {(byte) 0x57, (byte) 0x41, (byte) 0x56, (byte) 0x45},		// "WAVE"
		fmt_Id = {(byte) 0x66, (byte) 0x6D, (byte) 0x74, (byte) 0x20},		// "fmt "
		dataId = {(byte) 0x64, (byte) 0x61, (byte) 0x74, (byte) 0x61};		// "data"

	public FormatTags
		formatTag;

	public int

		fileSize = 0,

		fmtSize = 0,

		channels = 0,
		samplePerSec = 0,
		avgBytesPerSec = 0,
		blockAlign = 0,
		bitsPerSample =0,

		dataSize =0;

//	--------------------------------------------------------------------------------------------------------------------
	
	private WaveHeader( ){ }
	
	private WaveHeader(byte[] fileContent){

		this();

		if (fileContent != null){

			fileSize = readDataField(fileContent, FILE_SIZE);

			fmtSize = readDataField(fileContent, FMT_SIZE);
			formatTag = readFormatTag(fileContent);
			channels = readDataField(fileContent, CHANNELS);
			samplePerSec = readDataField(fileContent, SAMPLE_PER_SEC);
			avgBytesPerSec = readDataField(fileContent, BLOCK_ALIGN);
			blockAlign = readDataField(fileContent, DATA_SIZE);
			bitsPerSample = readDataField(fileContent, BITS_PER_SAMPLE);
			dataSize = readDataField(fileContent, DATA_SIZE);
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


	public void setFmtSize(int fmtSize) {

		this.fmtSize = fmtSize;
	}

	public int getFmtSize() {

		return fmtSize;
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
			"fileSize = " + fileSize + '\n'
			+ "format = " + formatTag + '\n'
			+ "channels = " + channels + '\n'
			+ "samplePerSec = " + samplePerSec + '\n'
			+ "avgBytePerSec = " + avgBytesPerSec + '\n'
			+ "blockAlign = " + blockAlign + '\n'
			+ "bitsPerSample = " + bitsPerSample + '\n'
			+ "dataSize = " + dataSize + '\n'
		;
	}
}

//	@formatter:on