package data.structure.signal;

public class Ch_Knot implements Channeling{


	Sampling
		sample;

	private Ch_Knot
		next = this,
		below = this;



	private Ch_Knot(Sampling s){

		sample = s;
	}

	public Ch_Knot(Sampling s, Ch_Knot n, Ch_Knot b){

		this(s);
		next = n;
		below = b;
	}



	public Sampling getSample() {

		return sample;
	}

	public void setSample(Sampling sample) {

		this.sample = sample;
	}



	public Ch_Knot getNext() {

		return next;
	}

	public void setNext(Ch_Knot n) {

		next = n;
	}



	public Ch_Knot getBelow() {

		return below;
	}

	public void setBelow(Ch_Knot b) {

		below = b;
	}


	public Sampling getSampling(int i) {

		return null;
	}

	public void addSampling(Sampling s) {

		Ch_Knot
			knot = new Ch_Knot(s);

		if (next == this) next = knot;

		else {
			knot.setNext(next);
			next = knot;
		}

	}

	public void setIndex(){




	}


	@Override
	public int size(){

		return 0;
	}

}