package app;

import gui.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.*;

import static gui.StageParams.*;

public class Main extends Application {

	@Override
	public void start(Stage stage) {

		Scene
			scene = new MainScene(stage);

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