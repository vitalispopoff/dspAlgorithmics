package data.structure.signal;

import java.util.ArrayList;

public interface Previewing {



	ArrayList<SamplePyramid> getCurrentSamples();

	SamplePyramid getCurrentSamples(int index)/*{

		return getCurrentSamplePyramid().get(index);
	}*/
	;

	/*void loadCurrentChan(){

		addLevelToCurrentSamplePyramid();
	}
	;*/	// ? disposable ?


	//	!--- TODO to be removed	--------------------------------

	boolean
		_switcher1 = true;	// * run new implementation ?

	Temporal t = new Temporal();

	class Temporal { static{ System.out.println("Previewing > new ver = " + Previewing._switcher1); }}	// ? _switcher - disposable

	static ArrayList<SamplePyramid> getCurrentSamples_old(){

		return CurrentFilePreview_old.getCurrentSamplePyramid();
	}

	static SamplePyramid getCurrentSamples_old(int index){

		return CurrentFilePreview_old.getCurrentSamplePyramid().get(index);
	}

	static void loadCurrentChan_old(){

		CurrentFilePreview_old.addLevelToCurrentSamplePyramid();
	}

	//	!--- TODO ----------------------------------------------
}