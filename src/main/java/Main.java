import data.*;

public class Main {

	public static void main(String[] args){

		Wave
			temporal,
			temporal1;

		String
			address;

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

			temporal = new Wave(address);

		}    // * load waveFile
	}
}