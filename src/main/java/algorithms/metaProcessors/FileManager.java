//	@formatter:off

package algorithms.metaProcessors;

import java.io.*;
import java.nio.file.*;

import static java.nio.file.Files.*;
import static java.nio.file.Paths.get;

public interface FileManager {

	static byte[] loadFile(String fileAddress){

		byte[]
			fileContent = { };

		boolean
			fileExists = verifyFile(fileAddress);

		if (fileExists) {

			Path
				filePath = get(fileAddress);

			try {

				fileContent = readAllBytes(filePath);
			}

			catch (IOException e) {

				e.printStackTrace();
			}
		}

		return fileContent;
	}

	static boolean verifyFile(String fileAddress){

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