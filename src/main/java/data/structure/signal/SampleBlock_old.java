package data.structure.signal;

import java.util.ArrayList;

public class SampleBlock_old extends ArrayList<AudioData> implements SamplePyramid {


	@Override
	public void setSample(AudioData sample) {

	}

	@Override
	public AudioData getSample() {

		return null;
	}

	public void addSampling(AudioData s) {

		super.add(s);
	}


	public AudioData getSampling(int i) {

		return super.get(i);
	}

	public int size() {

		return super.size();
	}

/*	SampleBlock_old(SamplePyramid pyramidLevel) {

		int
			pyramidLevelSize = (pyramidLevel.size() - 1),
			thisLevelSize = pyramidLevelSize >> 2;

		for (int i = 0; i < thisLevelSize; i++) {

			int j = i << 2;

			AudioData
				a = pyramidLevel.getSampling(j),
				b = pyramidLevel.getSampling(j + 1),
				c = pyramidLevel.getSampling(j + 2),
				d = pyramidLevel.getSampling(j + 3);
		}
	}*/

	/*interface Filtering{

		void setSample(AudioData s);
	}

	enum SampleComparison implements Filtering{

		A {
			AudioData sample;

*//*			void setSample(AudioData s) {

				sample = s;
			}*//*
		},
		B {
			AudioData sample;

*//*			void setSample(AudioData s) {

				sample = s;
			}*//*


		},
		C {
			AudioData sample;

*//*			void setSample(AudioData s) {

				sample = s;
			}*//*
		},
		D {
			AudioData sample;

*//*			void setSample(AudioData s) {

				sample = s;
			}*//*
		};

		AudioData sample;

		@Override
		public void setSample(AudioData s) {

			sample = s;
		}
	}*/
}