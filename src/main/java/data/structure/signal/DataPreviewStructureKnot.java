package data.structure.signal;

public class DataPreviewStructureKnot implements DataPreviewStructure {


	AudioData
		sample;

	private DataPreviewStructureKnot
		nextKnot = this,
//		above = this,
		below = this;

	byte
		level = 0;

	private DataPreviewStructureKnot(AudioData s){

		sample = s;
	}

	public DataPreviewStructureKnot(AudioData s, DataPreviewStructureKnot n, DataPreviewStructureKnot b){

		this(s);
		nextKnot = n;
		below = b;
	}



	public AudioData getSample() {

		return sample;
	}

	public void setSample(AudioData sample) {

		this.sample = sample;
	}



	public DataPreviewStructureKnot getNextKnot() {

		return nextKnot;
	}

	public void setNextKnot(DataPreviewStructureKnot n) {

		nextKnot = n;
	}



	public DataPreviewStructureKnot getBelow() {

		return below;
	}

	public void setBelow(DataPreviewStructureKnot b) {

		below = b;
	}



	public void addSampling(AudioData s) {

		DataPreviewStructureKnot
			knot = new DataPreviewStructureKnot(s);

		if (nextKnot == this) nextKnot = knot;

		else {
			knot.setNextKnot(nextKnot);
			nextKnot = knot;
		}

	}

	public void setIndex(){




	}


	@Override
	public int size(){

		return 0;
	}

}