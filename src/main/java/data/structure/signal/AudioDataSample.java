package data.structure.signal;

public class AudioDataSample implements AudioData {


	int
		channel,
		index,
		value;

	AudioData
		next = this,
		tail = this;


	public AudioDataSample(int v){

		value = v;
	}



	@Override
	public int getIndex(){

		return index;
	}

	@Override
	public void setIndex(int i){

		index = i;
	}


	@Override
	public int getValue(){

		return value;
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
	public void setTail(AudioData s){

		if(index == 0) tail = s;
	}


	@Override
	public byte[] getAll(int blockAlign){

		return new byte[0];
	} // ! TODO

/*	@Override
	public int size(){

		return (index == 0 && tail != this) // * the bool is redundant - just in case.
				   ? tail.getIndex() + 1
				   : -1;
	}*/	// size() - temporarily disabled
}