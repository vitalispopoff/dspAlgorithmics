//	@formatter:off

import algorithms.analyzers.BitRepresent;import algorithms.analyzers.Peaks;import algorithms.processors.Difference;import data.Wave;import java.util.Arrays;

public class Main {

	public static void main(String[] args){

		Wave
			temporal;

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
				address_404 = "nope.wave",

				address =  address_folder_1 + address_shortie;

			temporal = new Wave(address);

		}    // * load waveFile

		int
			dataBlockLength =
				temporal.header.dataBlockLength,
			sampleFrameSize =
				temporal.header.sampleFrameSize,
			sampleRate =
				temporal.header.sampleRate,
			channels =
				temporal.header.numberOfChannels;

		System.out.println(temporal);
	}
}

//	@formatter:on