package data;

import data.structure.FileAddress;
import data.structure.Signal;
import data.structure.WaveHeader;

import java.util.Arrays;

import static data.structure.FileContentStructure.*;
import static data.structure.WaveHeader.instanceOf;

import static algorithms.metaProcessors.FileManager.*;

public class Wave {

	public FileAddress
		fileAddress;

	public WaveHeader
		header;

	Signal
		signal;

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

			int
				start = 44,
				end = 44 + header.getField(DATA_SIZE);
			byte[]
				signalSource = Arrays.copyOfRange(fileContent, start, end);

			signal = new Signal(signalSource, header.getField(AV_BYTE_PER_SEC), header.getField(CHANNELS));

			setFileAddress(fileAddress);
		}
	}

	public static void main(String[] args) {


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

	public Signal getSignal(){

		return signal;
	}

	public byte[] getSource(){

		byte[]
			headerSource = header.getSource(),
			signalSource = signal.getSource(header.getField(AV_BYTE_PER_SEC)),
			result = new byte[headerSource.length + signalSource.length];

		System.arraycopy(headerSource, 0, result, 0, 44);
		System.arraycopy(signalSource, 0, result, 44, signalSource.length);

		return result;
	}

//	--------------------------------------------------------------------------------------------------------------------

}