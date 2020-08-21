//	@formatter:off

package data;

import static data.WaveHeader.instanceOf;

import static algorithms.metaProcessors.FileManager.*;
import static algorithms.metaProcessors.FileContentConverter.*;
import static algorithms.metaProcessors.ChannelSplitter.splitChannels;

public class Wave {

	public String
		fileAddress;

	public WaveHeader
		header;

	public int[][]
		channelSignals;

//	--------------------------------------------------------------------------------------------------------------------

	private Wave( ){ }

	public Wave (String fileAddress){

		setHeader(null);	// prevents NullPointerException when file is not found.

		if(verifyFile(fileAddress)){

			byte[]
				fileContent = loadFile(fileAddress);

			setFileAddress(fileAddress);
			setHeader(fileContent);
			setChannelSignals(fileContent);

			FileCache.addToCache(this);
		}
	}

//	--------------------------------------------------------------------------------------------------------------------

	public void setFileAddress(String fileAddress){

		this.fileAddress = fileAddress;
	}

	public String getFileAddress( ){

		return fileAddress;
	}



	public void setHeader(byte[] fileContent){

		this.header = instanceOf(fileContent);
	}

	public WaveHeader getHeader( ){

		return header;
	}



	public void setChannelSignals(byte[] fileContent){

		this.channelSignals = splitChannels(this, readSignal(fileContent));
	}

	public int[][] getChannelSignals( ){

		return channelSignals;
	}

//	--------------------------------------------------------------------------------------------------------------------

	@Override
	public String toString( ){

		return "Wave{\n"
				+ "fileAddress = "
				+ fileAddress + '\n'
				+ header.toString() + "}\n";
	}

//	--------------------------------------------------------------------------------------------------------------------

	public static void main(String[] args){

		Wave
			temporal;

		{
			String
				address_folder = "src\\main\\resources\\",

				address_0 = "sample-mono.wav",
				address_1 = "sample-mono-byte.wav",
				address_2 = "sample-mono-byte_unsigned.wav",
				address_3 = "sample-mono-byte.wav",
				address_4 = "sample-mono-float.wav",
				address_5 = "sample-mono-double.wav",

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