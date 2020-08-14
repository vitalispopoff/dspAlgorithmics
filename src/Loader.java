import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class Loader implements Serializable {

	public static byte[] loadFile(String fileAddress){

		Path
			filePath = Paths.get(fileAddress);
		byte[]
			wave = {};

		try {

			wave = Files.readAllBytes(filePath);
		}
		catch (IOException e) {

			e.printStackTrace();
		}

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
