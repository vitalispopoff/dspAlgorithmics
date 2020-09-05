package gui;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

import static javafx.scene.input.KeyCombination.*;

public enum MenuItems {

	MENU_FILE,
		OPEN_FILE,
		CLOSE_FILE,
		SAVE,
		QUIT,

	MENU_EDIT,

	MENU_ANALYZE,

	MENU_VIEW,
		FILE_WAVEFORM,
		FILE_AMPLITUDE_DISTRIBUTION,
		FILE_PROPERTIES,

	MENU_HELP,
		AUTO_SAVE,

	SEPARATOR {

		@Override
		public KeyCombination getKeyCombination(){

			return null;
		}

		@Override
		public Menu getMenu(MenuItems... items){

			return null;
		}

		@Override
		public MenuItem getMenuItem(){

			return new SeparatorMenuItem();
		}
	};

//	--------------------------------------------------------------------------------------------------------------------

	public KeyCombination getKeyCombination(){

		return (KeyCombination) menuItemParams[this.ordinal()][1];
	}

	public MenuItem getMenuItem (){

		MenuItem
			item = new MenuItem((String) menuItemParams[this.ordinal()][0]);

		item.setAccelerator(this.getKeyCombination());

		return item;
	}

	public Menu getMenu(MenuItems... items){

		Menu
			menu = new Menu((String) menuItemParams[this.ordinal()][0]);

		menu.setMnemonicParsing(true);

		KeyCombination
			k = (KeyCombination) menuItemParams[this.ordinal()][1];

		if (k != null) menu.setAccelerator(k);

		for(MenuItems item : items) {

			menu.getItems().add(item.getMenuItem());
			item.getMenuItem().setMnemonicParsing(k != null);
		}
		return menu;
	}

//	--------------------------------------------------------------------------------------------------------------------

	Object[][]
		menuItemParams = {

			{"_File", keyCombination("Alt+F"), "Menu"},
				{"_Open", keyCombination("Ctrl+O"), "MenuItem"},
				{"Close", keyCombination("Ctrl+W"), "MenuItem"},
				{"_Save", keyCombination("Ctrl+S"), "MenuItem"},
				{"_Quit", keyCombination("Ctrl+Q"), "MenuItem"},

			{"_Edit", keyCombination("Alt+E"), "Menu"},

			{"_Analyze", keyCombination("Alt+A"), "Menu"},

			{"_View", keyCombination("Alt+V"), "Menu"},
				{"_Waveform", keyCombination("Alt+W"), "MenuItem"},
				{"Amplitude _Distribution", keyCombination("Alt+D"), "MenuItem"},
				{"File _Properties", keyCombination("Alt+P"), "MenuItem"},

			{"Help", keyCombination("Alt+H"), "Menu"},
			{"Autosave",null, "checkItem"}
	};
}