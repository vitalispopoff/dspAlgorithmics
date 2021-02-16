package data.structure.signal;

public interface SamplePyramid {

	//	!--- to be removed	------------------------------------

	boolean
		_switcher = false;

	//	@format:off
	_tmp t = new _tmp();

	class _tmp {

		static {
			System.out.println("SamplePyramid > new ver = " + _switcher);
		}
	}
//	@format:on

	//	?-------------------------------------------------------


	static SamplePyramid newInstance() {

		return _switcher
				   ? new SampleBlock()
				   : new SampleBlock_old();
	}

	static SamplePyramid newInstance(AudioData sample) {

		return new SampleBlock(sample);
	}

	void setSample(AudioData sample);

	AudioData getSample();

	default void addSampling(Integer v) {

		addSampling(AudioData.newInstance(v));
	}


	void addSampling(AudioData s);

	AudioData getSampling(int i);

	default int size() { return 0;}
}