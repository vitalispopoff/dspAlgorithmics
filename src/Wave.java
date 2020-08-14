import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

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

		for(int i = 0 ; i < dataBlockLength ; i++)
			signal[i] = Byte.toUnsignedInt(wave[i + 44]) /*- (1 << (bitDepth - 1))*/;

	}

	private int convertFrom2sComplement(byte[] sample){

//		String[] cache = new String[sample.length];

	return 0;
	}

//	--------------------------------------------------------------------------------------------------------------------

	public static void main(String[] args) {

		String
			adres_0 = "2_samples-mono.wav",
			adres_1 = "2_samples.wav",
			adres_2 = "2_samples-mono-8bit.wav",
			adres_3 = "shortie-mono-16bit.wav";


		Wave temporal = new Wave();

		temporal.setFileAddress("C:\\Users\\Voo\\Desktop\\unpeak\\shortie\\"+adres_0);
		temporal.setWave(temporal.fileAddress);
		temporal.setHeader();
		temporal.setWrapper();
		temporal.setType();
		temporal.setFileLength();
		temporal.setFormatSize();
		temporal.setChannels();
		temporal.setSampleRate();
		temporal.setSampleSize();
		temporal.setSampleFrameSize();
		temporal.setBitDepth();
		temporal.setDataBlockLength();
//		temporal.setSignal();


		int
			dataBlockLength = temporal.dataBlockLength,
			sampleFrameSize = temporal.sampleFrameSize;
		byte[] wave = temporal.wave;


//		-------------------------------------------------

//		System.out.println(sampleFrameSize);



		byte[]
			byteCache = Arrays.copyOfRange(wave, 44, 44 + dataBlockLength);

//		System.out.println(Arrays.toString(byteCache));


		int[]
			samples = new int[dataBlockLength / sampleFrameSize];

		samples[0] =
				Byte.toUnsignedInt(byteCache[0])
				+ Byte.toUnsignedInt(byteCache[1]) << 8
				+ Byte.toUnsignedInt(byteCache[2]) << 16;

/*
		byte[] sample0 = new byte[4];
		sample0[0] = byteCache[0];
		sample0[2] = (byte) (byteCache[1] >> 4);
		sample0[1] = (byte) (byteCache[1] - sample0[2]);
		sample0[3] = byteCache[2];
*/	// disposable


		int[] sample1 = new int[4];
		sample1[0] = byteCache[0];
		sample1[2] = (byteCache[1] >> 4);
		sample1[1] = (byte) (byteCache[1] - sample1[2] << 4);
		sample1[3] = byteCache[2];

		int[] sample2 = new int[2];

		sample2[0] = byteCache[0] + (byteCache[1] - (byteCache[1] >> 4) << 4) << 8;
		sample2[1] = (byteCache[1] >> 4) + byteCache[2] << 8;

		;











	}
}