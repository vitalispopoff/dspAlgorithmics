package data.structure;


import data.structure.signal.*;

class Scratch {

	enum SampleComparison {

		A,
		B,
		C,
		D;

		private final SamplePyramid block = new SampleBlock();

		public AudioData getSample(){

			return block.getSample();
		}

		public void setSample(AudioData sample){

			block.setSample(sample);
		}

		public int getSampleValue(){
			return block.getSample().getValue();
		}

	}



	public static void main(String[] args) {

		SampleComparison.A.setSample(AudioData.newInstance(128));

		System.out.println(SampleComparison.A.getSampleValue());



	}
}