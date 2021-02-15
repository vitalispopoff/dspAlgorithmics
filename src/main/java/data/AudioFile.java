package data;

import data.structure.header.FileHeader;
import data.structure.signal.*;

public interface AudioFile {


	void setFileAddress(String fileAddress);

	FileAddress getFileAddress();


	FileHeader getHeader();

	/*AudioData getAudioAnchor();*/ // ? disposable

	Channeling getChannelAnchor();

	byte[] releaseSource();
}