package data.structure.signal;

public class SampleBlock implements SamplePyramid {


	AudioData
		sample;

	private SampleBlock
		nextKnot = this,
//		above = this,
		below = this;

	byte
		level = 0;

	private SampleBlock(AudioData s){

		sample = s;
	}

	public SampleBlock(AudioData s, SampleBlock n, SampleBlock b){

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



	public SampleBlock getNextKnot() {

		return nextKnot;
	}

	public void setNextKnot(SampleBlock n) {

		nextKnot = n;
	}



	public SampleBlock getBelow() {

		return below;
	}

	public void setBelow(SampleBlock b) {

		below = b;
	}



	public void addSampling(AudioData s) {

		SampleBlock
			knot = new SampleBlock(s);

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