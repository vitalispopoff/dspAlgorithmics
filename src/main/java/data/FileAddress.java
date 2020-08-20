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

	String
		catalogPath,
		fileName,
		fileExtension;

//	--------------------------------------------------------------------------------------------------------------------

	public FileAddress(String catalogPath, String fileName, String fileExtension) {

		this.catalogPath = catalogPath;
		this.fileName = fileName;
		this.fileExtension = fileExtension;
	}

	public FileAddress(String catalogPath, String fileExtension) {

		this(catalogPath, getTemporalName(), fileExtension);
	}

	public FileAddress(String catalogPath){

		this(catalogPath, "wav");
	}

//	--------------------------------------------------------------------------------------------------------------------

	public String getCatalogPath() {

		return catalogPath;
	}

	public void setCatalogPath(String catalogPath) {

		this.catalogPath = catalogPath;
	}



	public String getFileName() {

		return fileName;
	}

	public void setFileName(String fileName) {

		this.fileName = fileName;
	}

	private static String getTemporalName() {

		LocalDateTime
			now = LocalDateTime.now();

		DateTimeFormatter
			nowFormat = DateTimeFormatter.ofPattern("yyyy-DDD-HH-mm-ss");

		return "temp_" + now.format(nowFormat);
	}



	public String getFileExtension() {

		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {

		this.fileExtension = fileExtension;
	}

	public void setFileExtensions(byte[] fileContent){

		this.fileExtension = establishFileExtension(fileContent);
	}



	static String establishFileExtension(byte[] fileContent){

		byte[]
			cache = Arrays.copyOfRange(fileContent, 8, 12);

		StringBuilder
			builder = new StringBuilder();

		for(byte b : cache)
			builder.append((char) b);

		return fileExtensions.get(builder.toString());
	}

//	--------------------------------------------------------------------------------------------------------------------

	public int dupa(){
		return 0;
	}

	public static void main(String[] args) {


	}
}

//	@formatter:on