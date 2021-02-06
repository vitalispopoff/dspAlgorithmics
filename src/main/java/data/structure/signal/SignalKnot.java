package data.structure.signal;

public class SignalKnot implements SignalTree {


	Sampling
		sample;

	private SignalKnot
		nextKnot = this,
		below = this;



	private SignalKnot(Sampling s){

		sample = s;
	}

	public SignalKnot(Sampling s, SignalKnot n, SignalKnot b){

		this(s);
		nextKnot = n;
		below = b;
	}



	public Sampling getSample() {

		return sample;
	}

	public void setSample(Sampling sample) {

		this.sample = sample;
	}



	public SignalKnot getNextKnot() {

		return nextKnot;
	}

	public void setNextKnot(SignalKnot n) {

		nextKnot = n;
	}



	public SignalKnot getBelow() {

		return below;
	}

	public void setBelow(SignalKnot b) {

		below = b;
	}


	public Sampling getSampling(int i) {

		return null;
	}

	public void addSampling(Sampling s) {

		SignalKnot
			knot = new SignalKnot(s);

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