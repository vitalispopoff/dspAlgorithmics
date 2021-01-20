package data.structure.signal;

public class Sample implements Sampling {

	public int
		value;

	public Sampling
		next;

	public Sample(int v){

		value = v;
	}

	public int getValue(){
		return value;
	}

	public void setValue(int v){
		value = v;
	}

	public Sampling getNext(){
		return next;
	}

	public void setNext(Sampling s){
		next = s;
	}


/*	public static Sample min(Sample sample1, Sample sample2){

		return sample2.value - sample1.value > 0 ? sample1 : sample2;
	}

	public static Sample max(Sample sample1, Sample sample2){

		return sample2.value - sample1.value > 0 ? sample2 : sample1;
	}*/
}