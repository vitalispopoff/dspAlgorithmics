package algorithms.processors;

import java.nio.*;

import algorithms.analyzers.FormatTag.*;

import static algorithms.analyzers.FormatTag.FormatTags.starts;
import static algorithms.analyzers.FormatTag.getFormatTag;

public class Wave {

	String
		fileAddress;

	byte[]
		wave;

	int[]
		signal;

	int[][]
		channels;

	FormatTags
		format;

	int
		fileLength,
		numberOfChannels,
		sampleRate,
		sampleFrameSize,
		bitDepth,
		dataBlockLength;

//	--------------------------------------------------------------------------------------------------------------------

	private Wave(){ }

	public Wave (String fileAddress){

		if(Loader.verifyFileExistence(fileAddress)){

			setFileAddress(fileAddress);
			setWave(fileAddress);
			setFormat();
			setFileLength();
			setNumberOfChannels();
			setSampleRate();
			setSampleFrameSize();
			setBitDepth();
			setDataBlockLength();

			setSignal();
		}
	}

//	--------------------------------------------------------------------------------------------------------------------

	public void setFileAddress(String fileAddress){

		this.fileAddress = fileAddress;
	}
	public String getFileAddress() {

		return fileAddress;
	}

	public void setWave(String fileAddress){

		this.wave = Loader.loadFile(fileAddress);
	}
	public byte[] getWave() {

		return wave;
	}

	public void setSignal() {

		signal = new int[dataBlockLength / sampleFrameSize];

		int
				start = starts[format.ordinal()],
				index = 0;

		for (int i = start; i < start + dataBlockLength; i += sampleFrameSize) {

			int
					sample = wave[i + sampleFrameSize - 1];

			boolean
					flag = sample < 0;

			if (flag)
				sample <<= (sampleFrameSize - 1) << 3;

			else
				sample <<= (sampleFrameSize - 1) << 3;

			for (int j = 0; j < sampleFrameSize - 1; j++) {

				int
						aByte = wave[j + i] & 0xFF;

				if (flag)
					aByte <<= (j) << 3;

				else
					aByte <<= j << 3;

				sample |= aByte;
			}

			signal[index++] = sample;
		}
	}
	public int[] getSignal() {

		return signal;
	}

	public void setChannels(){

		this.channels = ChannelSplitter.splitChannels(this);
	}
	public int[][] getChannels(){

		return channels;
	}

	public void setFormat(){

		int
			i = Short.toUnsignedInt(
					ByteBuffer.wrap(wave, 20, 2)
					.order(ByteOrder.LITTLE_ENDIAN).getShort());

		this.format = getFormatTag(i);

	}
	public FormatTags getFormat() {

		return format;
	}

	public void setFileLength(){

		this.fileLength = ByteBuffer.wrap(wave, 4, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}
	public int getFileLength() {

		return fileLength;
	}

	public void setNumberOfChannels(){

		this.numberOfChannels = Short.toUnsignedInt(ByteBuffer.wrap(wave, 22, 2).order(ByteOrder.LITTLE_ENDIAN).getShort());
	}
	public int getNumberOfChannels() {

		return numberOfChannels;
	}

	public void setSampleRate(){

		this.sampleRate = ByteBuffer.wrap(wave, 24, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}
	public int getSampleRate() {

		return sampleRate;
	}

	public void setSampleFrameSize(){

		this.sampleFrameSize = Short.toUnsignedInt(ByteBuffer.wrap(wave, 32, 2).order(ByteOrder.LITTLE_ENDIAN).getShort());
	}
	public int getSampleFrameSize() {

		return sampleFrameSize;
	}

	public void setBitDepth(){

		this.bitDepth = Short.toUnsignedInt(ByteBuffer.wrap(wave, 34, 2).order(ByteOrder.LITTLE_ENDIAN).getShort());
	}
	public int getBitDepth() {

		return bitDepth;
	}

	public void setDataBlockLength(){

		this.dataBlockLength = ByteBuffer.wrap(wave, 40, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}
	public int getDataBlockLength() {

		return dataBlockLength;
	}



	@Override
	public String toString() {
		return "algorithms.processors.Wave{\n"
				+ "fileAddress = " + fileAddress + '\n'
				+ " format = " + format + '\n'
				+ " fileLength = " + fileLength + '\n'
				+ " numberOfChannels = " + numberOfChannels + '\n'
				+ " sampleRate = " + sampleRate + '\n'
				+ " sampleFrameSize = " + sampleFrameSize + '\n'
				+ " bitDepth = " + bitDepth + '\n'
				+ " dataBlockLength = " + dataBlockLength
				+ "\n}";
	}

//	--------------------------------------------------------------------------------------------------------------------

	public static void main(String[] args) {

		Wave
				temporal;

		{
			String
					adres_0 = "2_samples-mono.wav",
					adres_1 = "2_samples.wav",
					adres_2 = "2_samples-mono-8bit.wav",
					adres_3 = "shortie-mono-16bit.wav",
					adres_4 = "2_samples-mono-temp.wav",
					adres_5 = "1kHz_16_mono.wav",
					adres_null = "nope.wave",
					adress = "C:\\Users\\Voo\\Desktop\\unpeak\\shortie\\" + adres_null;

			temporal = new Wave(adress);

		}    // * load waveFile

		int
			dataBlockLength = temporal.dataBlockLength,
			sampleFrameSize = temporal.sampleFrameSize,
			sampleRate = temporal.sampleRate,
			channels = temporal.numberOfChannels;

		int[]
				signal = temporal.signal;

	//	----------------------------------------------------------------------------------------------------------------

		System.out.println(temporal.toString());
	}
}