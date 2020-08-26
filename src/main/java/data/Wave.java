package data;

import data.structure.FileAddress;
import data.structure.WaveHeader;

import java.util.Arrays;
import static data.structure.WaveHeader.instanceOf;

import static algorithms.metaProcessors.FileManager.*;

public class Wave {

	public FileAddress
		fileAddress;

	byte[]
		signalSource;

	public WaveHeader
		header;

//	--------------------------------------------------------------------------------------------------------------------

	private Wave( ){ }

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

//	--------------------------------------------------------------------------------------------------------------------

}