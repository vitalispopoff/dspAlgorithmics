import java.nio.*;
import java.util.Arrays;

import algorithms.analyzers.FormatTag.*;

import static algorithms.analyzers.FormatTag.FormatTags.WAVE_FORMAT_PCM;
import static algorithms.analyzers.FormatTag.FormatTags.starts;
import static algorithms.analyzers.FormatTag.getFormatTag;
import static algorithms.analyzers.BitRepresent.*;

public class Wave {

	String
		fileAddress;

	byte[]
		wave;

	int[]
		signal;

	String
		header,
		wrapper;

	FormatTags
		format;

	int
		fileLength,
/*		formatSize,*/		// * disposable
		channels,
		sampleRate,
/*		sampleSize,*/		// * disposable ?
		sampleFrameSize,
		bitDepth,
		dataBlockLength;

//	--------------------------------------------------------------------------------------------------------------------

	private Wave(){ }

	public Wave (String fileAddress){

		setFileAddress(fileAddress);
		setWave(fileAddress);
/*		setHeader();*/		// * disposable
/*		setWrapper();*/		// * disposable
		setFormat();
		setFileLength();
/*		setFormatSize();*/		// * disposable
		setChannels();
		setSampleRate();
/*		setSampleSize();*/		// * disposable?
		setSampleFrameSize();
		setBitDepth();
		setDataBlockLength();
		setSignal();
	}

//	--------------------------------------------------------------------------------------------------------------------

/*	private String readWave(int start, int length){

		StringBuilder
				cache = new StringBuilder();

		if (wave.length > 44)

			for (int i = start; i < start + length ; i++)

				cache.append((char) wave[i]);

		return cache.toString();
	}*/	// * disposable

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

/*	public void setHeader(){

		this.header = readWave(0, 44);
	}
	public String getHeader() {
		return header;
	}*/	// * disposable

/*	public void setWrapper(){

		this.wrapper = readWave(0, 4);
	}
	public String getWrapper() {
		return wrapper;
	}*/	// * disposable

	public void setFormat(){

		int i = Short.toUnsignedInt(ByteBuffer.wrap(wave, 20, 2).order(ByteOrder.LITTLE_ENDIAN).getShort());

/*
		int
			msB = wave[21],
			lsB = wave[20];

		if (msB < 0) {

			msB = (msB << 24) >> 16;
			lsB >>>= 24;
		}
		else {

			msB <<= 8;
			lsB >>>=32;
		}

		int index = msB + lsB;
*/	// * disposable

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

/*	public void setFormatSize(){

		this.formatSize = ByteBuffer.wrap(wave, 16, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}
	public int getFormatSize() {
		return formatSize;
	}*/		// * disposable

	public void setChannels(){

		this.channels = Short.toUnsignedInt(ByteBuffer.wrap(wave, 22, 2).order(ByteOrder.LITTLE_ENDIAN).getShort());
	}
	public int getChannels() {
		return channels;
	}

	public void setSampleRate(){

		this.sampleRate = ByteBuffer.wrap(wave, 24, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}
	public int getSampleRate() {
		return sampleRate;
	}

/*	public void setSampleSize(){

		this.sampleSize = ByteBuffer.wrap(wave, 28, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}
	public int getSampleSize() {
		return sampleSize;
	}*/		// * disposable ?

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

	@Override
	public String toString() {
		return "Wave{\n"
				+ "fileAddress = " + fileAddress + '\n'
				+ " wrapper = " + wrapper + '\n'
				+ " format = " + format + '\n'
				+ " fileLength = " + fileLength + '\n'
/*				+ " formatSize = " + formatSize + '\n'*/		// * disposable
				+ " channels = " + channels + '\n'
				+ " sampleRate = " + sampleRate + '\n'
/*				+ " sampleSize = " + sampleSize + '\n'*/		// * disposable ?
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
					adress = "C:\\Users\\Voo\\Desktop\\unpeak\\shortie\\" + adres_5;

			temporal = new Wave(adress);

		}    // * load waveFile

		int
			dataBlockLength = temporal.dataBlockLength,
			sampleFrameSize = temporal.sampleFrameSize,
			sampleRate = temporal.sampleRate,
			channels = temporal.channels;

		int[]
				signal = temporal.signal;

	//	----------------------------------------------------------------------------------------------------------------

		System.out.println(Arrays.toString(temporal.signal));
	}
}