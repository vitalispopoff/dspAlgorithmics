package gui;

import data.Wave;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.*;

import java.io.File;
import java.util.List;

import static gui.MenuItems.*;

public class MainMenu extends MenuBar {

	Stage
		stage;

	Menu
		menuFile = MENU_FILE.getMenu(OPEN_FILE, CLOSE_FILE, SEPARATOR, SAVE_AS, SEPARATOR, EXIT_APP),
		menuEdit = MENU_EDIT.getMenu(),
		menuView = MENU_VIEW.getMenu(),
		menuHelp = MENU_HELP.getMenu();

	FileChooser
		browser = new FileChooser();

//	--------------------------------------------------------------------------------------------------------------------

	public MainMenu(Stage stage){

		this.stage = stage;

		this.getMenus().addAll(menuFile, menuEdit, menuView, menuHelp);

		setActionsToMenuFile();
	}



	private void setActionsToMenuFile(){

		List<MenuItem> items = menuFile.getItems();

		items.get(0).setOnAction((ActionEvent e) -> {

			browser.setInitialDirectory(new File(System.getProperty("user.home")));
			File file = browser.showOpenDialog(stage);
			Wave wave = new Wave(file);
		});

/*		items.get(3).setOnAction((ActionEvent e) -> {

			browser.showOpenDialog(stage);
		});*/	// * TODO save as

		items.get(5).setOnAction(close -> stage.close());


	}
}