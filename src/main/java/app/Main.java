package app;

import gui.*;
import javafx.application.Application;
import javafx.stage.*;

import static gui.StageParams.*;

public class Main extends Application {

	@Override
	public void start(Stage stage) {

		stage.setMinWidth(320 + stageWAdjust);
		stage.setMinHeight(240 + stageHAdjust);

		Root
			root = new Root(stage);

		MainScene
			scene = new MainScene(root);

		stage.setWidth(getInitialStageWidth() + stageWAdjust);
		stage.setHeight(getInitialStageHeight() + stageHAdjust);
		stage.setResizable(true);

		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {

		launch(args);
	}
}