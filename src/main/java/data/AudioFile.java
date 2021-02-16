package data;

import data.structure.header.FileHeader;
import data.structure.signal.*;

public interface AudioFile {


	void setFileAddress(String fileAddress);

	FileAddress getFileAddress();


	FileHeader getHeader();


	Channeling getChannelAnchor();

	byte[] releaseSource();
}