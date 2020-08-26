//	@formatter:off

package data;

//import static data.FileAddress.getTemporalName;
import static data.FileAddress.readFileAddress;
import static data.WaveHeader.instanceOf;

import static algorithms.metaProcessors.FileManager.*;
import static algorithms.metaProcessors.FileContentConverter.*;

public class Wave {

	public FileAddress
		fileAddress;

	public WaveHeader
		header;

	public int[][]
		channelSignals;

//	--------------------------------------------------------------------------------------------------------------------

	private Wave( ){ }

	public Wave(WaveHeader header){

//		this.fileAddress = getTemporalName();
		this.header = header;
	}

	public Wave (String fileAddress){

		boolean
			addressIsValid = verifyFile(fileAddress);

			this.header = WaveHeader.instanceOf(null);	// prevents NullPointerException when file is not found.

		if(addressIsValid){

			byte[]
				fileContent = loadFile(fileAddress);

			this.header = instanceOf(fileContent);
			this.channelSignals = readSignalChannels(fileContent);

			setFileAddress(fileAddress);

			FileCache.addToCache(this);
		}
	}

//	--------------------------------------------------------------------------------------------------------------------

	public void setFileAddress(String fileAddress){

		this.fileAddress = FileAddress.readFileAddress(fileAddress);
	}

	public FileAddress getFileAddress( ){

		return fileAddress;
	}



	public WaveHeader getHeader( ){

		return header;
	}



	public void setChannelSignals(int[][] channelSignals){

		this.channelSignals = channelSignals;
	}

	public int[][] getChannelSignals( ){

		return channelSignals;
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