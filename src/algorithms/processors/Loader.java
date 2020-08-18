package algorithms.processors;

import com.sun.deploy.security.SelectableSecurityManager;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

import static java.nio.file.Files.exists;

public class Loader implements Serializable {


	public static boolean verifyFileExistence(String fileAddress){

		return exists(Paths.get(fileAddress));
	}

	public static byte[] loadFile(String fileAddress){

		Path
			filePath = Paths.get(fileAddress);
		byte[]
			wave = {};


		if (exists(filePath)) {
			try {

				wave = Files.readAllBytes(filePath);
			}
			catch (IOException e) {

				e.printStackTrace();
			}
		}

		else System.out.println("I can't find file: "+ filePath +'.');

		return wave;
	}

	public static void saveFile(ArrayList<Byte> wave, String fileAddress){

		try {

			FileOutputStream
				fos = new FileOutputStream(fileAddress);
			ObjectOutputStream
				oos = new ObjectOutputStream(fos);

			oos.writeObject(wave);
			oos.close();
			fos.close();


		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

}
