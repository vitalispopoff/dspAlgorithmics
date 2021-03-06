package algorithms.metaProcessors;

import data.FileCache;
import data.AudioFile;
import data.FileAddress;
import javafx.beans.property.*;

import java.io.*;
import java.nio.file.*;
import java.util.Arrays;

import static java.nio.file.Files.*;
import static java.nio.file.Paths.get;

public interface FileManager {

	static void saveFile(File file){

		AudioFile
			audioFile = FileCache.getFile();

		byte[]
			source = audioFile.releaseSource();

		try {

			write(file.toPath(), source);
		}

		catch (IOException e) {

			System.out.println("error in FileManager.saveFile() :\n");
			e.printStackTrace();
		}
	}

	static void autoSaveFile(){

		AudioFile
			audioFile = FileCache.getFile();

		FileAddress
			address = new FileAddress(audioFile.getFileAddress().getPath(), "", "wav");

		address.setNameToDefault();

		Path
			path = get(address.toString());

		File
			file = new File(address.toString());

		saveFile(file);
	}


	static byte[] loadFile(File file){

		try { return readAllBytes(file.toPath()); }

		catch (IOException e){

			System.out.println(
				"FileManager> loadFile> readAllBytes has failed");

			e.printStackTrace();
		}

		return new byte[0];
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

			try { fileContent = readAllBytes(filePath); }

			catch (IOException e) {

				System.out.println(
					"FileManager> loadFile> readAllBytes has failed"
				);

				e.printStackTrace(); }
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
				"FileManager> verifyFile> get has failed"
			);

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
				input = new FileInputStream(filePath);	// TODO this calls for a further investigation

			System.out.println(
				"FileManager> verifyFileType> fileInputStream initialized"
			);

			input.read(bytes, 0, 12);

			System.out.println(
				"FileManager> verifyFileType> read succeeded"
			);

			input.close();

			System.out.println(
				"FileManager> verifyFileType> FileInputStream closed"
			);
		}

		catch (IOException e) {

			System.out.println(
				"FileManager> verifyFileType> FileInputStream failed"
			);

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



//	-------------------------------------------------------------------------------------------

	abstract class FileManagerSettings {

	// default path ---------------------------------------------------------------------------

		private static final String
//			"user.dir"
//			"user.home"

			defPath = System.getProperty("user.dir");

		private static final StringProperty
			currentDefaultPathDue = new SimpleStringProperty(defPath);


		public static String getCurrentDefaultPath(){

			return currentDefaultPathDue.get();
		}

		public static void setCurrentDefaultPath(String path){

			currentDefaultPathDue.set(path);

/*
			System.out.println(
				"FileManager> Settings> setCurrentDefaultPath : path = " + getCurrentDefaultPath()
			);
*/	// ? disposable ?

		}

		public static StringProperty getCurrentDefaultPathDueProperty(){

			return currentDefaultPathDue;
		}

		public static void resetCurrentDefaultPath(){

			setCurrentDefaultPath(defPath);
		}

	//	autoSave flag  -----------------------------------------------------------------------------


		private	static final BooleanProperty
			autoSaveDue = new SimpleBooleanProperty(false);

		public static boolean getAutoSave(){

			return autoSaveDue.get();
		}

		public static void setAutoSave(boolean b){

			autoSaveDue.set(b);
		}

		public static BooleanProperty getAutoSaveDueProperty(){

			return autoSaveDue;
		}
	}
}