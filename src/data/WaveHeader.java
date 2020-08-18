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

		int
			i = Short.toUnsignedInt(
			ByteBuffer.wrap(fileContent, 20, 2)
						.order(ByteOrder.LITTLE_ENDIAN)
						.getShort());

		this.format = getFormatTag(i);

	}
	public FormatTags getFormat() {

		return format;
	}

	public int getFormatOrdinal(){

		return format.ordinal();
	}

	public void setFileLength(byte[] fileContent){

		this.fileLength = ByteBuffer.wrap(fileContent, 4, 4)
									.order(ByteOrder.LITTLE_ENDIAN)
									.getInt();
	}
	public int getFileLength() {

		return fileLength;
	}

	public void setNumberOfChannels(byte[] fileContent){

		this.numberOfChannels = Short.toUnsignedInt(ByteBuffer.wrap(fileContent, 22, 2)
										.order(ByteOrder.LITTLE_ENDIAN)
										.getShort());
	}
	public int getNumberOfChannels() {

		return numberOfChannels;
	}

	public void setSampleRate(byte[] fileContent){

		this.sampleRate = ByteBuffer.wrap(fileContent, 24, 4)
									.order(ByteOrder.LITTLE_ENDIAN)
									.getInt();
	}
	public int getSampleRate() {

		return sampleRate;
	}

	public void setSampleFrameSize(byte[] fileContent){

		this.sampleFrameSize = Short.toUnsignedInt(ByteBuffer.wrap(fileContent, 32, 2)
									.order(ByteOrder.LITTLE_ENDIAN)
									.getShort());
	}
	public int getSampleFrameSize() {

		return sampleFrameSize;
	}

	public void setBitDepth(byte[] fileContent){

		this.bitDepth = Short.toUnsignedInt(ByteBuffer.wrap(fileContent, 34, 2)
								.order(ByteOrder.LITTLE_ENDIAN)
								.getShort());
	}
	public int getBitDepth() {

		return bitDepth;
	}

	public void setDataBlockLength(byte[] fileContent){

		this.dataBlockLength = ByteBuffer.wrap(fileContent, 40, 4)
											.order(ByteOrder.LITTLE_ENDIAN)
											.getInt();
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