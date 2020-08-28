package data.structure;

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static data.structure.FileContentStructure.WAVE_ID;

public class FileAddress {

	private static final HashMap<String, String>
		defaultExtensions = setDefaultExtensions();

	String
		path,
		name,
		extension;

//	--------------------------------------------------------------------------------------------------------------------

	public FileAddress(String path, String name, String extension){

		this.path = path;
		this.name = name;
		this.extension = extension;
	}

//	--------------------------------------------------------------------------------------------------------------------

	public static FileAddress readFileAddress(String input){

		int
			extensionIndex = 0,
			fileNameIndex = 0;

		boolean
			foundExtension = false,
			foundName = false;

		for(int i = input.length() - 1; i >= 0  && !(foundExtension && foundName); i--){

			boolean
				iIsNotLastChar = i < input.length() - 1;

			if(input.charAt(i) == '.' && iIsNotLastChar) {

				extensionIndex = i + 1;
				foundExtension = true;
			}

			if(input.charAt(i) == '\\' && iIsNotLastChar){

				fileNameIndex = i + 1;
				foundName = true;
			}
		}

		String
			extension = /*extensionIndex > 0
				? */input.substring(extensionIndex)
				/*: fileExtensions.get("WAVE")*/,

			name = /*fileNameIndex > 0
				? */input.substring(fileNameIndex, extensionIndex - 1)
				/*: getTemporalName()*/,

			path = /*fileNameIndex > 0
				? */input.substring(0, fileNameIndex)
				/*: defaultCatalogPath*/;

		return new FileAddress(path, name, extension);
	}



	public String getPath( ){

		return path;
	}

	public void setPath(String path){

		this.path = path;
	}

	public void setPathToDefault(){

		path = System.getenv("USERPROFILE") + "Desktop\\";
	}



	public String getName( ){

		return name;
	}

	public void setName(String name){

		this.name = name;
	}

	public void setNameToDefault(){

		LocalDateTime
			now = LocalDateTime.now();

		DateTimeFormatter
			nowFormat = DateTimeFormatter.ofPattern("yyyy-DDD-HH-mm-ss");

		name =  "temp_" + now.format(nowFormat);
	}



	public String getExtension( ){

		return extension;
	}

	public void setExtension(String extension){

		this.extension = extension;
	}

	public void setExtensionToDefault(byte[] fileContent){

		extension = establishExtension(fileContent);
	}



	static String establishExtension(byte[] source){

		int[]
			location = WAVE_ID.getLocation();

		byte[]
			cache = Arrays.copyOfRange(source, location[0], location[1]);

		StringBuilder
			builder = new StringBuilder();

		for(byte b : cache)

			builder.append((char) b);

		String
			address = builder.toString();

		return defaultExtensions.get(address);
	}

//	--------------------------------------------------------------------------------------------------------------------

	private static HashMap<String, String> setDefaultExtensions() {

		HashMap<String, String> map = new HashMap<>();

		map.put("WAVE", "wav");
		map.put("AIFF", "aiff");

		return map;
	}

	@Override
	public String toString(){

		int
			index = path.length() - 1;

		String
			slash = path.charAt(index) == '\\'
				? ""
				: "\\";

		return path + slash + name + '.' + extension;
	}
}