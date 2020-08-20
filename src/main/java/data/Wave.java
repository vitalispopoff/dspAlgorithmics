//	@formatter:off

package data;

import static java.util.Arrays.copyOfRange;

import static data.WaveHeader.instanceOf;

import static algorithms.analyzers.FormatTag.FormatTags.starts;

import static algorithms.metaProcessors.FileManager.*;
import static algorithms.metaProcessors.ChannelSplitter.splitChannels;
import static algorithms.metaProcessors.FileContentConverter.dataFrameReader;

public class Wave {

	public String
		fileAddress;

	public WaveHeader
		header;

	public int[][]
		channelSignals;

//	--------------------------------------------------------------------------------------------------------------------

	private Wave() { }

	public Wave (String fileAddress){

		setHeader(null);	// prevents NullPointerException when file is not found.

		if(verifyFile(fileAddress)){

			byte[]
				fileContent = loadFile(fileAddress);

			setFileAddress(fileAddress);
			setHeader(fileContent);
			setChannelSignals(fileContent);
		}
	}

//	--------------------------------------------------------------------------------------------------------------------

	public void setFileAddress(String fileAddress){

		this.fileAddress = fileAddress;
	}

	public String getFileAddress() {

		return fileAddress;
	}


	public void setHeader(byte[] fileContent){

		this.header = instanceOf(fileContent);
	}

	public WaveHeader getHeader(){

		return header;
	}


	public void setChannelSignals(byte[] fileContent){

		int[]
			signal = setSignal(fileContent);

		this.channelSignals = splitChannels(this, signal);
	}

	public int[][] getChannelSignals(){

		return channelSignals;
	}

	private int[] setSignal(byte[] fileContent) {

		int
			dataBlockLength = header.getDataBlockLength(),
			sampleFrameSize = header.getSampleFrameSize(),
			start = starts[header.getFormatOrdinal()],
			index = 0;

		int[]
			signal = new int[dataBlockLength / sampleFrameSize];

		for (int i = start; i < start + dataBlockLength; i += sampleFrameSize){

			byte[]
				bytes = copyOfRange(fileContent, i, i + sampleFrameSize);

			signal[index++] = dataFrameReader(bytes);
		}

		return signal;
	}

//	--------------------------------------------------------------------------------------------------------------------

	@Override
	public String toString() {

		return "Wave{\n"
				+ "fileAddress = "
				+ fileAddress + '\n'
				+ header.toString() + "}\n";
	}

//	--------------------------------------------------------------------------------------------------------------------

	public static void main(String[] args) {

		Wave
			temporal;

		{
			String
				address_folder = "C:\\Users\\Voo\\Desktop\\unpeak\\shortie\\",

				address_0 = "2_samples-mono.wav",
				address_1 = "2_samples-mono-8bit.wav",
				address_2 = "2_samples-mono-byte_unsigned.wav",
				address_3 = "2_samples-mono-8bit.wav",
				address_4 = "2_samples-mono-float.wav",
				address_5 = "2_samples-mono-double.wav",

				address_400 = "*.wav",
				address_404 = "nope.wave",

				address =  address_folder + address_0;

			temporal = new Wave(address);

		}    // * load waveFile

		int
			dataBlockLength = temporal.header.dataBlockLength,
			sampleFrameSize = temporal.header.sampleFrameSize,
			sampleRate = temporal.header.sampleRate,
			channels = temporal.header.numberOfChannels;

	}
}

//	@formatter:on