//	@formatter:off

package algorithms.metaProcessors;

import data.FileAddress;
import data.Wave;
import java.io.*;
import java.nio.file.*;

import static data.FileCache.*;
import static java.nio.file.Files.*;
import static java.nio.file.Paths.get;

public interface FileManager {

	static void exportToFile(Wave file){

		FileAddress
			fileAddress = file.getFileAddress();

		fileAddress.setFileName(FileAddress.getTemporalName());

		Path
			filePath = get(fileAddress.toString());

		byte[]
			export = FileContentConverter.convertToBytes(file);

		try {

			write(filePath, export);

			System.out.println(
				"\n\tFile successfully saved as : "
					+ fileAddress.toString() + '\n'
			);
		}

		catch (IOException e) {

			System.out.println(
				"\n\tI'm sorry. Something went awfully wrong.\n\tFor more read the stack trace, please.\n"
			);

			e.printStackTrace();
		}

	}

	static void exportCurrentToFile(){

		Wave
			file = loadCurrent();

		FileAddress
			fileAddress = file.getFileAddress();

		Path
			filePath = get(fileAddress.toString());

		byte[]
			export = FileContentConverter.convertToBytes(file);

		try {

			write(filePath, export);

			System.out.println(
				"\n\tFile successfully saved as : "
				+ fileAddress.toString() + '\n'
			);
		}

		catch (IOException e) {

			System.out.println(
				"\n\tI'm sorry. Something went awfully wrong.\n\tFor more read the stack trace, please.\n"
			);

			e.printStackTrace();
		}
	}



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