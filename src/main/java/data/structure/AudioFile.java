package data.structure;

import data.structure.header.WaveHeader;
import data.structure.signal.AudioData;
import data.structure.signal.Channeling;

public interface AudioFile {



	void setFileAddress(String fileAddress);

	FileAddress getFileAddress();


	WaveHeader getHeader();

	AudioData getAudioAnchor();

	Channeling getChannelAnchor();

	byte[] releaseSource();


}
