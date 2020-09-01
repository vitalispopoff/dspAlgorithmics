package algorithms.metaProcessors;

import data.FileCache;
import data.Wave;
import data.structure.FileAddress;

import java.io.*;
import java.nio.file.*;
import java.util.Arrays;

import static java.nio.file.Files.*;
import static java.nio.file.Paths.get;

public interface FileManager {

	static void saveFile(File file){

		Wave
			wave = FileCache.loadCurrent();

		byte[]
			source = wave.getSource();

		try {

			write(file.toPath(), source);
		}

		catch (IOException e) {

			e.printStackTrace();
		}
	}

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

	static byte[] loadFile(File file){

		byte[]
			fileContent = { };

		try{

			fileContent = readAllBytes(file.toPath());

		}

		catch (IOException e){

			e.printStackTrace();;
		}

		return fileContent;
	}

	static byte[] loadFile(String fileAddress){

		byte[]
			fileContent = { };

		boolean
			fileExists = verifyFile(fileAddress),
			fileIsValid = verifyFileType(fileAddress);

		if (fileExists && fileIsValid) {

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

	static boolean verifyFileType(String filePath){

		byte[]
			bytes = new byte[12],
			riff = new byte[]{(byte) 0x52, (byte) 0x49, (byte) 0x46, (byte) 0x46},
			wave = new byte[]{(byte) 0x57, (byte) 0x41, (byte) 0x56, (byte) 0x45},
			aiff = new byte[]{(byte) 0x41, (byte) 0x49, (byte) 0x46, (byte) 0x46};

		try {

			FileInputStream
				input = new FileInputStream(filePath);

			input.read(bytes, 0, 12);

			input.close();
		}

		catch (IOException e) {

			e.printStackTrace();
			return false;
		}

		byte[]
			fileId = Arrays.copyOfRange(bytes, 0, 4),
			waveId = Arrays.copyOfRange(bytes, 8, 12);

		boolean
			fileIsRIFF = Arrays.equals(fileId, riff),
			fileIsWAVE = Arrays.equals(waveId, wave),
			fileIsAIFF = Arrays.equals(waveId, aiff);

		return fileIsRIFF && (fileIsWAVE || fileIsAIFF);
	}
}