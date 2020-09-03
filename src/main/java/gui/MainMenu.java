package gui;

import data.Wave;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.*;

import java.io.File;
import java.util.List;

import static algorithms.metaProcessors.FileManager.saveFile;
import static gui.MenuItems.*;

public class MainMenu extends MenuBar {

	Stage
		stage;

	Menu
		menuFile = MENU_FILE.getMenu(OPEN_FILE, CLOSE_FILE, SEPARATOR, SAVE, SEPARATOR, QUIT),
		menuEdit = MENU_EDIT.getMenu(),
		menuAnalyze = MENU_ANALYZE.getMenu(),
		menuView = MENU_VIEW.getMenu(FILE_WAVEFORM, FILE_AMPLITUDE_DISTRIBUTION, SEPARATOR,  FILE_PROPERTIES),
		menuHelp = MENU_HELP.getMenu();

//	--------------------------------------------------------------------------------------------------------------------

	public MainMenu(Stage stage){

		this.stage = stage;

		this.getMenus().addAll(menuFile, menuEdit, menuAnalyze, menuView, menuHelp);

		setActionsToMenuFile();

	//	! TEMPORAL	//-------------------------------------------------------------------------

		MenuItem
			button = new MenuItem("window size");

		button.setOnAction(e ->{ System.out.println(stage.getWidth() + ", " + stage.getHeight());});

		Menu
			temporal = new Menu("TEMPORAL");

		temporal.getItems().add(button);

		getMenus().add(temporal);

	//	!  //-----------------------------------------------------------------------------------

	}

	private void setActionsToMenuFile(){

		List<MenuItem> items = menuFile.getItems();

		items.get(0).setOnAction((ActionEvent e) -> {

			FileChooser
				browser = new FileChooser();

			browser.setTitle("Open File");

			browser.setInitialDirectory(new File(System.getProperty("user.home")));
			File file = browser.showOpenDialog(stage);
			Wave wave = new Wave(file);
		});

		items.get(3).setOnAction((ActionEvent e) -> {

			FileChooser
				browser = new FileChooser();

			browser.setTitle("Save As");

			File
				file = browser.showSaveDialog(stage);

			saveFile(file);
		});

		items.get(5).setOnAction(close -> stage.close());


	}
}