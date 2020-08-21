//	@formatter:off

package algorithms.metaProcessors;

import java.io.*;
import java.nio.file.*;

import static java.nio.file.Files.*;
import static java.nio.file.Paths.get;

public abstract class FileManager implements Serializable {

	static int
		currentFileIndex = 0;

//	--------------------------------------------------------------------------------------------------------------------

	public static void saveFile(String fileAddress, byte[] wave){

		Path
			filePath = get(fileAddress);

		try {

			write(filePath, wave);
		}

		catch (IOException e) {

			e.printStackTrace();
		}
	}

	public static byte[] loadFile(String fileAddress){

		byte[]
			wave = { };

		boolean
			fileExists = verifyFile(fileAddress);

		if (fileExists) {

			Path
				filePath = get(fileAddress);

			try {

				wave = readAllBytes(filePath);
			}

			catch (IOException e) {

				e.printStackTrace();
			}
		}

		return wave;
	}



	public static boolean verifyFile(String fileAddress){

		boolean
				fileExists;

		try {

			Path
				filePath = get(fileAddress);

			fileExists = exists(filePath);

			if (!fileExists)

				System.out.println(
					"\n\t I can't find file: "
					+ fileAddress + "\n"
				);

			return fileExists;
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
}

//	@formatter:on