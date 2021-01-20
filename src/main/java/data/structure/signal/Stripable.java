package data.structure.signal;

public interface Stripable {

	int size();

	Sampling get(int i);

	void addSample(Sampling s);

}