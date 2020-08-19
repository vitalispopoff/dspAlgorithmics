//	@formatter:off

package data;

import algorithms.analyzers.FormatTag.FormatTags;

import java.nio.*;

import static algorithms.analyzers.FormatTag.getFormatTag;

public class WaveHeader {
	
	public FormatTags
		format;
	
	public int
		fileLength = 0,
		numberOfChannels = 0,
		sampleRate = 0,
		sampleFrameSize = 0,
		bitDepth = 0,
		dataBlockLength = 0;
	
//	--------------------------------------------------------------------------------------------------------------------
	
	private WaveHeader() { }
	
	private WaveHeader(byte[] fileContent){

		setFormat(fileContent);
		setFileLength(fileContent);
		setNumberOfChannels(fileContent);
		setSampleRate(fileContent);
		setSampleFrameSize(fileContent);
		setBitDepth(fileContent);
		setDataBlockLength(fileContent);
	}
	
//	--------------------------------------------------------------------------------------------------------------------

	public static WaveHeader instanceOf(byte[] fileContent){

		if (fileContent != null && fileContent.length > 0)
			return new WaveHeader(fileContent);

		else return new WaveHeader();
	}

//	--------------------------------------------------------------------------------------------------------------------

	public void setFormat(byte[]fileContent){

		ByteBuffer
			buffer = ByteBuffer.wrap(fileContent, 20, 2);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		short
			s = buffer.getShort();

		int
			i = Short.toUnsignedInt(s);

		this.format = getFormatTag(i);
	}

	public FormatTags getFormat() {

		return format;
	}

	public int getFormatOrdinal(){

		return format.ordinal();
	}


	public void setFileLength(byte[] fileContent){

		ByteBuffer
			buffer = ByteBuffer.wrap(fileContent, 4, 4);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		this.fileLength = buffer.getInt();
	}

	public int getFileLength() {

		return fileLength;
	}


	public void setNumberOfChannels(byte[] fileContent){

		ByteBuffer
			buffer = ByteBuffer.wrap(fileContent, 22, 2);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		short
			s = buffer.getShort();

		this.numberOfChannels = Short.toUnsignedInt(s);
	}

	public int getNumberOfChannels() {

		return numberOfChannels;
	}


	public void setSampleRate(byte[] fileContent){

		ByteBuffer
			buffer = ByteBuffer.wrap(fileContent, 24, 4);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		this.sampleRate = buffer.getInt();
	}

	public int getSampleRate() {

		return sampleRate;
	}


	public void setSampleFrameSize(byte[] fileContent){

		ByteBuffer
			buffer = ByteBuffer.wrap(fileContent, 32, 2);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		short
			s = buffer.getShort();

		this.sampleFrameSize = Short.toUnsignedInt(s);
	}

	public int getSampleFrameSize() {

		return sampleFrameSize;
	}


	public void setBitDepth(byte[] fileContent){

		ByteBuffer
			buffer = ByteBuffer.wrap(fileContent, 34, 2);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		short
			s = buffer.getShort();

		this.bitDepth = Short.toUnsignedInt(s);
	}

	public int getBitDepth() {

		return bitDepth;
	}


	public void setDataBlockLength(byte[] fileContent){

		ByteBuffer
			buffer = ByteBuffer.wrap(fileContent, 40, 4);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		this.dataBlockLength = buffer.getInt();
	}

	public int getDataBlockLength() {

		return dataBlockLength;
	}

//	--------------------------------------------------------------------------------------------------------------------
	
	@Override
	public String toString(){

		return
			"format = " + format + '\n'
			+ "fileLength = " + fileLength + '\n'
			+ "numberOfChannels = " + numberOfChannels + '\n'
			+ "sampleRate = " + sampleRate + '\n'
			+ "sampleFrameSize = " + sampleFrameSize + '\n'
			+ "bitDepth = " + bitDepth + '\n'
			+ "dataBlockLength = " + dataBlockLength + "\n}";
	}
}

//	@formatter:on