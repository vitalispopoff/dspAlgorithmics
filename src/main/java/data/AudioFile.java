package data;

import data.structure.FileAddress;
import data.structure.header.FileHeader;
import data.structure.signal.*;

public interface AudioFile {



	void setFileAddress(String fileAddress);

	FileAddress getFileAddress();


	FileHeader getHeader();

	AudioData getAudioAnchor();

	Channeling getChannelAnchor();

	byte[] releaseSource();
}