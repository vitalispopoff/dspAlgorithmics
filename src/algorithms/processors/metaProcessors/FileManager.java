//	@formatter:off

package algorithms.processors.metaProcessors;

import java.io.*;
import java.nio.file.*;

import static java.nio.file.Files.*;

public class FileManager implements Serializable {

	public static boolean verifyFileExistence(String fileAddress){

		boolean result;

		try {

			result = exists(Paths.get(fileAddress));

			if (!result)
				System.out.println("\n\t I can't find file: " + fileAddress + "\n");

			return result;
		}

		catch (InvalidPathException e){

			System.out.println(
				"\n\tI can't read the fileAddress: "
				+ fileAddress
				+ "\n\tYou may need to check the spelling.\n"
			);

			return false;
		}
	}

	public static byte[] loadFile(String fileAddress){

		Path
			filePath = Paths.get(fileAddress);

		byte[]
			wave = { };

		if (verifyFileExistence(fileAddress)) {

			try {

				wave = Files.readAllBytes(filePath);
			}

			catch (IOException e) {

				e.printStackTrace();
			}
		}

		return wave;
	}

	public static void saveFile(String fileAddress, byte[] wave){

		Path
			filePath = Paths.get(fileAddress);

		try {

			write(filePath, wave);
		}

		catch (IOException e) {

			e.printStackTrace();
		}
	}

//	--------------------------------------------------------------------------------------------------------------------

	public static void main(String[] args) {

		String
			address_404 = "404.wav",
			address_mistake = "*.wav",
			address = "C:\\Users\\Voo\\Desktop\\unpeak\\shortie\\" + address_404;

		loadFile(address);
	}
}

//	@formatter:on