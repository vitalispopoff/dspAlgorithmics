import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import static algorithms.analyzers.BitRepresent.bitRepresent;

public class Wave {

	String
		fileAddress;

	byte[]
		wave;

	int[]
		signal;

	String
		header,
		wrapper,
		type;

	int
		fileLength,
		formatSize,
		channels,
		sampleRate,
		sampleSize,
		sampleFrameSize,
		bitDepth,
		dataBlockLength;

//	--------------------------------------------------------------------------------------------------------------------

	private Wave(){ }

	public Wave (String fileAddress){

		setFileAddress(fileAddress);
		setWave(fileAddress);
		setHeader();
		setWrapper();
		setType();
		setFileLength();
		setFormatSize();
		setChannels();
		setSampleRate();
		setSampleSize();
		setBitDepth();
		setDataBlockLength();
	}

//	--------------------------------------------------------------------------------------------------------------------

	private String readWave(int start, int length){

		StringBuilder
				cache = new StringBuilder();

		if (wave.length > 44)

			for (int i = start; i < start + length ; i++)

				cache.append((char) wave[i]);

		return cache.toString();
	}

	public void setFileAddress(String fileAddress){

		this.fileAddress = fileAddress;
	}

	public void setWave(){

		setWave(fileAddress);
	}

	public void setWave(String fileAddress){

		this.wave = Loader.loadFile(fileAddress);
	}

	public void setHeader(){

		this.header = readWave(0, 44);
	}

	public void setWrapper(){

		this.wrapper = readWave(0, 4);
	}

	public void setType(){

		this.type = readWave(8, 4);
	}

	public void setFileLength(){

		this.fileLength = ByteBuffer.wrap(wave, 4, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}

	public void setFormatSize(){

		this.formatSize = ByteBuffer.wrap(wave, 16, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}

	public void setChannels(){

		this.channels = Short.toUnsignedInt(ByteBuffer.wrap(wave, 22, 2).order(ByteOrder.LITTLE_ENDIAN).getShort());
	}

	public void setSampleRate(){

		this.sampleRate = ByteBuffer.wrap(wave, 24, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}

	public void setSampleSize(){

		this.sampleSize = ByteBuffer.wrap(wave, 28, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}

	public void setSampleFrameSize(){

		this.sampleFrameSize = Short.toUnsignedInt(ByteBuffer.wrap(wave, 32, 2).order(ByteOrder.LITTLE_ENDIAN).getShort());
	}

	public void setBitDepth(){

		this.bitDepth = Short.toUnsignedInt(ByteBuffer.wrap(wave, 34, 2).order(ByteOrder.LITTLE_ENDIAN).getShort());
	}

	public void setDataBlockLength(){

		this.dataBlockLength = ByteBuffer.wrap(wave, 40, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}

	public void setSignal(){

		signal = new int[dataBlockLength];

		byte[]
				byteCache = Arrays.copyOfRange(wave, 44, 44 + dataBlockLength);
		int
			sample = 0;

		for (int i = 0 ; i < byteCache.length ; i += sampleFrameSize) {

			for (int j = sampleFrameSize - 1; j >= 0; j--) {

				if (j == sampleFrameSize - 1) {

					int
							cache = (byte) (byteCache[i + j] >>> 7);

					sample = cache << 31;
					cache = (byte) (byteCache[i + j] - (byte) (cache << 7));
					sample += cache << (j << 3);
				}

				else {

					sample += (byteCache[i + j] << 24) >>> ((sampleFrameSize - j) << 3);
				}
			}
		}
	}

//	--------------------------------------------------------------------------------------------------------------------

	public static void main(String[] args) {

		Wave
			temporal;

		{
			String
				adres_0 = "2_samples-mono.wav",
				adres_1 = "2_samples.wav",
				adres_2 = "2_samples-mono-8bit.wav",
				adres_3 = "shortie-mono-16bit.wav",
				adres_4 = "2_samples-mono-temp.wav",
				adress = "C:\\Users\\Voo\\Desktop\\unpeak\\shortie\\" + adres_0;

			temporal = new Wave(adress);

		}	// load waveFile

			int
				dataBlockLength = temporal.dataBlockLength,
				sampleFrameSize = temporal.sampleFrameSize,
				sampleRate = temporal.sampleRate;

			byte[]
				wave = temporal.wave;

//		----------------------------------------------------------------------------------------------------------------

/*
		byte[] byteCache = { (byte) 0b0000001, (byte) 0b00000000, (byte) 0b11111111 };
		int sample = 0;

		for (int i = 0 ; i < byteCache.length ; i += sampleFrameSize) {

			int
				j = sampleFrameSize - 1;

			for ( ; j >= 0 ; j-- ){

				if (j == sampleFrameSize - 1){

				int
					cache = (byte) (byteCache[i + j] >>> 7);

					sample = cache << 31;

					cache = (byte) (byteCache[i + j] - (byte) (cache << 7));

					sample += cache << (j << 3) ;
				}
				else{

					sample += (byteCache[i + j] << 24) >>> ((sampleFrameSize - j) << 3);

				}
			}
		}
*/	// disposable

		Byte[] look = {64,61,74,61};

		System.out.println(Arrays.toString(wave));

	}
}