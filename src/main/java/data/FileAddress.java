//	@formatter:off

package data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FileAddress {

	private static final HashMap<String, String>
		fileExtensions = new HashMap<>();

	static {

		fileExtensions.put("WAVE", "wav");
		fileExtensions.put("AIFF", "aiff");

	}	// * fileExtensions population

	public static String
		defaultCatalogPath = System.getenv("USERPROFILE") + "Desktop\\";

	String
		catalogPath,
		fileName,
		fileExtension;

//	--------------------------------------------------------------------------------------------------------------------

	public FileAddress(String catalogPath, String fileName, String fileExtension){

		this.catalogPath = catalogPath;
		this.fileName = fileName;
		this.fileExtension = fileExtension;
	}

	public FileAddress(String catalogPath, String fileExtension){

//		this(catalogPath, getTemporalName(), fileExtension);
	}

	public FileAddress(String catalogPath){

		this(catalogPath, fileExtensions.get("WAVE"));
	}

	public FileAddress(){

		this(defaultCatalogPath, fileExtensions.get("WAVE"));
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
			extension = extensionIndex > 0
				? input.substring(extensionIndex)
				: fileExtensions.get("WAVE"),

			name = fileNameIndex > 0
				? input.substring(fileNameIndex, extensionIndex - 1)
				: getTemporalName(),

			path = fileNameIndex > 0
				? input.substring(0, fileNameIndex)
				: defaultCatalogPath;

		System.out.println(
			"\textension : " + extension
			+ "\n\tname : " + name
			+ "\n\tpath : " + path
			+ '\n'
		);

		return new FileAddress(path, name, extension);
	}



	public String getCatalogPath( ){

		return catalogPath;
	}

	public void setCatalogPath(String catalogPath){

		this.catalogPath = catalogPath;
	}



	public String getFileName( ){

		return fileName;
	}

	public void setFileName(String fileName){

		this.fileName = fileName;
	}



	public static String getTemporalName(){

		LocalDateTime
			now = LocalDateTime.now();

		DateTimeFormatter
			nowFormat = DateTimeFormatter.ofPattern("yyyy-DDD-HH-mm-ss");

		return "temp_" + now.format(nowFormat);
	}



	public String getFileExtension( ){

		return fileExtension;
	}

	public void setFileExtension(String fileExtension){

		this.fileExtension = fileExtension;
	}

	public void setFileExtensions(byte[] fileContent){

		this.fileExtension = establishFileExtension(fileContent);
	}



	static String establishFileExtension(byte[] fileContent){

		int
			start = FileContentStructure.WAVE_ID.getStart(),
			end = start + FileContentStructure.WAVE_ID.getLength();

		byte[]
			cache = Arrays.copyOfRange(fileContent, start, end);

		StringBuilder
			builder = new StringBuilder();

		for(byte b : cache)

			builder.append((char) b);

		String
			address = builder.toString();

		return fileExtensions.get(address);
	}

//	--------------------------------------------------------------------------------------------------------------------

	@Override
	public String toString(){

		String
			slash = catalogPath.charAt(catalogPath.length() - 1) == '\\'
				? ""
				: "\\";

		return catalogPath + slash +  fileName + '.' +  fileExtension;
	}
}
//	@formatter:on