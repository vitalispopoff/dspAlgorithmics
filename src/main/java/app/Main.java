package app;

import data.WaveFile;
import gui.*;
import javafx.application.Application;
import javafx.stage.*;

import java.io.File;

import static gui.StageParams.*;

public class Main extends Application {

	@Override
	public void start(Stage stage) {

		stage.setMinWidth(320 + stageWAdjust);
		stage.setMinHeight(240 + stageHAdjust);

		stage.setWidth(getInitialStageWidth() + stageWAdjust);
		stage.setHeight(getInitialStageHeight() + stageHAdjust);
		stage.setResizable(true);

		Root
			root = new Root(stage);

		MainScene
			scene = new MainScene(root);

		//	! TEMPORAL ----------------------------------------

		String
			adr0 = "src\\main\\resources\\shortie-mono-16bit.wav",
			adr1 = "H:\\_LIBS\\User Lib\\Samples\\Own shots\\6425-klik\\001.wav",
			adr2 = "H:\\_LIBS\\User Lib\\Samples\\Full Loops\\fsol-papua-ethnic.wav";

		new WaveFile(new File(adr0));

		// ! --------------------------------------------------

		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {

		launch(args);

	}
}