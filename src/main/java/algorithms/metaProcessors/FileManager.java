package algorithms.metaProcessors;

import data.Wave;
import data.structure.FileAddress;

import java.io.*;
import java.nio.file.*;

import static java.nio.file.Files.*;
import static java.nio.file.Paths.get;

public interface FileManager {

	static void saveFile(Wave wave){

	FileAddress
		address = new FileAddress(wave.getFileAddress().getPath(), "", "wav");

	address.setNameToDefault();


	byte[]
		source = wave.getSource();

	Path
		path = get(address.toString());

		try {

			createFile(path);

			write(path, source);

			System.out.println(
				"\n\tFile saved to "
				+ address
				+'\n'
			);
		}

		catch (IOException e) {

			e.printStackTrace();

			System.out.println("\n\tI'm sorry. Something went wrong.");
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