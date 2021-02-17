package data.structure;


import com.sun.scenario.effect.Brightpass;
import data.structure.signal.*;

import static data.structure.SampleComparison.*;

enum SampleComparison {

	A, B, C, D;


	private final SamplePyramid
		block = new SampleBlock();


	public AudioData getSample() {

		return block.getSample();
	}

	public void setSample(AudioData sample) {

		block.setSample(sample);
	}

	public int getSampleValue() {

		return block.getSample().getValue();
	}


	static boolean compare(SampleComparison first, SampleComparison second) {

		return first.getSampleValue() <= second.getSampleValue();
	}

}

class SolutionTree {

	static SolutionTree
		_111 = new SolutionTree(C, D),
		_110 = new SolutionTree(A, B),
		_101 = new SolutionTree(B, D),
		_100 = new SolutionTree(A, C),
		_011 = _111,
		_010 = _110,
		_001 = new SolutionTree(B, C),
		_000 = new SolutionTree(A, D),
		_11 = new SolutionTree(_110, _111),
		_10 = new SolutionTree(_100, _101),
		_01 = new SolutionTree(_010, _011),
		_00 = new SolutionTree(_000, _001),
		_1 = new SolutionTree(_10, _11),
		_0 = new SolutionTree(_00, _01),
		solution = new SolutionTree(_0, _1);


	SolutionTree
		right,
		left;

	SampleComparison
		first,
		second;

	SolutionTree(SolutionTree l, SolutionTree r) {

		right = r;
		left = l;
	}

	SolutionTree(SampleComparison f, SampleComparison s) {

		first = f;
		second = s;
	}

	SolutionTree climbTree(boolean comparison){

		return comparison ? right : left;
	}

	void getSolution(){

		System.out.println(first.ordinal()  + " & " + second.ordinal());

	}

	static void solve(){

		boolean
			AtoB = compare(A, B),
			CtoD = compare(C, D),

			firstCascade = AtoB ^ CtoD,

			Ato_ = compare(A, firstCascade ? D : C),
			Bto_ = compare(B, firstCascade ? C : D),

			secondCascade = Ato_ ^ Bto_,
			thirdCascade = AtoB ^ Ato_;

		SolutionTree
			result = solution.climbTree(firstCascade);
		result = result.climbTree(secondCascade);
		result = result.climbTree(thirdCascade);

		result.getSolution();
	}
}

class Scratch {

	public static void main(String[] args) {



		int
			i0 = 5, i1 = 1, i2 = 2, i3 = 3;

		AudioData
			a = AudioData.newInstance(i0),
			b = AudioData.newInstance(i1),
			c = AudioData.newInstance(i2),
			d = AudioData.newInstance(i3);

		A.setSample(a);
		B.setSample(b);
		C.setSample(c);
		D.setSample(d);

		SolutionTree.solve();
	}
}
