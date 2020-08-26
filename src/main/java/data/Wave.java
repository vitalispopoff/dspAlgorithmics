//	@formatter:off

package data;

import java.util.Arrays;
import static data.WaveHeader.instanceOf;

/*import static algorithms.metaProcessors.FileContentConverter.*;*/		// * disabled temporarily
import static algorithms.metaProcessors.FileManager.*;

public class Wave {

/*	public int[][]
		channelSignals;*/	// * disabled temporarily

	public FileAddress
		fileAddress;

	byte[]
		signalSource;

	public WaveHeader
		header;

//	--------------------------------------------------------------------------------------------------------------------

	private Wave( ){ }

	public Wave(WaveHeader header){

		this.header = header;
	}

	public Wave (String fileAddress){

		boolean
			addressIsValid = verifyFile(fileAddress);

			this.header = WaveHeader.instanceOf(null);

		if(addressIsValid){

			byte[]
				fileContent = loadFile(fileAddress);

			header = instanceOf(fileContent);
			signalSource = Arrays.copyOfRange(fileContent, 44, fileContent.length - 44);
			setFileAddress(fileAddress);

/*			this.channelSignals = readSignalChannels(fileContent);*/		// * disabled temporarily
/*			FileCache.addToCache(this);*/		// * disabled temporarily
		}
	}

//	--------------------------------------------------------------------------------------------------------------------

	public void setFileAddress(String fileAddress){

		this.fileAddress = FileAddress.readFileAddress(fileAddress);
	}

	public FileAddress getFileAddress( ){

		return fileAddress;
	}

/*	public void setChannelSignals(int[][] channelSignals){

		this.channelSignals = channelSignals;
	}

	public int[][] getChannelSignals( ){

		return channelSignals;
	}*/		// * disabled temporarily

	public WaveHeader getHeader( ){

		return header;
	}

//	--------------------------------------------------------------------------------------------------------------------

	@Override
	public String toString( ){

		return "Wave = [\n"
				+ "fileAddress = "
				+ fileAddress + '\n'
				+ header.toString() + "]\n";
	}
}

//	@formatter:on