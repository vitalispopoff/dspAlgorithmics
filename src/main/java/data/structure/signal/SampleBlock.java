package data.structure.signal;

public class SampleBlock implements SamplePyramid {


	AudioData
		sample;

	private SampleBlock
		nextKnot = this,
		below = this;

	byte
		level = 0;

	SampleBlock() {}

	private SampleBlock(AudioData s) {

		sample = s;
	}

	public SampleBlock(AudioData s, SampleBlock n, SampleBlock b) {

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


	public void setIndex() {


	}



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

	@Override
	public int size() {

		return 0;
	}
}