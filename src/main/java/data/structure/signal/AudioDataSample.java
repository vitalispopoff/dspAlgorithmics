package data.structure.signal;

public class AudioDataSample implements AudioData {


	int
		value;

	AudioData
		next = this,
		tail = this;


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


	@Override
	public byte[] getAll(int blockAlign){

		return new byte[0];
	} // ! TODO


	public AudioData getTail(){

		return tail;
	}

	public void setTail(AudioData s){

		tail = s;
	}


}