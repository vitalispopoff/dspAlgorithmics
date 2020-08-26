//	@formatter:off

import algorithms.metaProcessors.FileContentConverter;
import data.*;

public class Main {

	public static void main(String[] args){

		Wave
			temporal,
			temporal1;

		String
			address,
			address1,
			address_temp = "";

		{
			String
				address_folder_0 = "src\\main\\resources\\",
				address_folder_1 = "C:\\Users\\Voo\\Desktop\\unpeak\\shortie\\",

				address_shortie = "shortie-mono-16bit.wav",

				address_0 = "sample-mono.wav",
				address_1 = "sample-mono-byte.wav",
				address_2 = "sample-mono-byte_unsigned.wav",
				address_3 = "sample-mono-byte.wav",
				address_4 = "sample-mono-float.wav",
				address_5 = "sample-mono-double.wav",

				address_400 = "*.wav",
				address_404 = "nope.wave";

				address =  address_folder_0 + address_0;
				address1 =  address_folder_0 + address_temp;

			temporal = new Wave(address);

			if (address_temp.length() > 0)
				temporal1 = new Wave(address1);


		}    // * load waveFile

		int
			dataBlockLength =
				temporal.header.blockAlign,
			sampleFrameSize =
				temporal.header.avgBytesPerSec,
			sampleRate =
				temporal.header.samplePerSec,
			channels = temporal.header.channels;

		FileAddress
			fileAddress = temporal.getFileAddress();

		byte[]
			given = FileContentConverter.convertToBytes(temporal),
			correct = new byte[]{

				(byte) 0x52, (byte) 0x49, (byte) 0x46, (byte) 0x46,	//	 0	RIFF 	fileId
				(byte) 0x58, (byte) 0x00, (byte) 0x00, (byte) 0x00,	//	 4			fileSize
				(byte) 0x57, (byte) 0x41, (byte) 0x56, (byte) 0x45,	//	 8	WAVE	waveId

				(byte) 0x66, (byte) 0x6D, (byte) 0x74, (byte) 0x20,	//	12	fmt 	fmt Id
				(byte) 0x10, (byte) 0x00, (byte) 0x00, (byte) 0x00,	//	16			fmtSize
				(byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x00,	//	20			formatTag / channels
				(byte) 0x44, (byte) 0xAC, (byte) 0x00, (byte) 0x00,	//	24			samplePerSec
				(byte) 0xCC, (byte) 0x04, (byte) 0x02, (byte) 0x00,	//	28			avBytePerSec
				(byte) 0x03, (byte) 0x00, (byte) 0x18, (byte) 0x00,	//	32			blockAlign / bitsPerSample

				(byte) 0x64, (byte) 0x61, (byte) 0x74, (byte) 0x61,	//	36	data	dataId
				(byte) 0x0C, (byte) 0x00, (byte) 0x00, (byte) 0x00,	//	40			dataSize

				(byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x01,	//	44			signal
				(byte) 0x00, (byte) 0x00, (byte) 0xFF, (byte) 0xFF,	//	48
				(byte) 0x7F, (byte) 0x00, (byte) 0x00, (byte) 0x80,	//	52
				(byte) 0x53, (byte) 0x41, (byte) 0x55, (byte) 0x52,	//	56
				(byte) 0x20, (byte) 0x00, (byte) 0x00, (byte) 0x00,	//	60
				(byte) 0x31, (byte) 0x2E, (byte) 0x32, (byte) 0x2E,	//	64
				(byte) 0x30, (byte) 0x2E, (byte) 0x30, (byte) 0x00,	//	68
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,	//	72
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,	//	76
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,	//	80
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,	//	84
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,	//	88
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
			};

		for (int i = 0; i < correct.length; i++){

			if(correct[i] != given[i])

				System.out.println(
					"index: " + i
					+ ",\t correct = " + correct[i]
					+ ",\t\t given = " + given[i]
				);
		}


	}
}

//	@formatter:on