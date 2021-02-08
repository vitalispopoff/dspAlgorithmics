package data.structure.signal;

public class AudioDataSample implements AudioData {


	int
		value = 0;

	AudioData
		next = this;


	public AudioDataSample(int v){

		value = v;
	}



	@Override
	public int getValue(){

		return value;
	}

	@Override
	public void setValue(int v){

		value = v;
	}



	@Override
	public AudioData getNext(){

		return next;
	}

	@Override
	public void setNext(AudioData s){

		next = s;
	}

}