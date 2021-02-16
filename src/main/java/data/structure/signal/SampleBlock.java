package data.structure.signal;

public class SampleBlock implements SamplePyramid {

	static{ System.out.println("SampleBlock called out"); }


	AudioData
		sample;

	private SampleBlock
		nextKnot = this,
		below = this;

	byte
		level = 0;

	public SampleBlock() {}

	SampleBlock(AudioData s) {

		sample = s;
	}

	/*public SampleBlock(AudioData s, SampleBlock n, SampleBlock b) {

		this(s);
		nextKnot = n;
		below = b;
	}*/ // ? disposable?


	public AudioData getSample() {

		return sample;
	} // ? disposable

	public void setSample(AudioData sample) {

		this.sample = sample;
	} // ? disposable


	/*public SampleBlock getNextKnot() {

		return nextKnot;
	}*/ // ? disposable

	public void setNextKnot(SampleBlock n) {

		nextKnot = n;
	}


	/*public SampleBlock getBelow() {

		return below;
	}*/	// ? disposable

	/*public void setBelow(SampleBlock b) {

		below = b;
	}*/ // ? disposable


	/*public void setIndex() {


	}*/ // ? disposable



	@Override
	public void addSampling(AudioData s) {

		SampleBlock
			knot = new SampleBlock(s);

		if (nextKnot == this) {
			nextKnot = knot;
		}

		else {
			knot.setNextKnot(nextKnot);
			nextKnot = knot;
		}

	}

	@Override
	public AudioData getSampling(int i) {

		int
			sampleIndex = i - nextKnot.sample.getIndex();

		SampleBlock
			temp = nextKnot;

		while(sampleIndex > 0) {
			temp = temp.nextKnot;
			sampleIndex--;
		}

		return temp.sample;
	}

}