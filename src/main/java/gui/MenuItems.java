package gui;

import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;

import static javafx.scene.input.KeyCombination.*;

public enum MenuItems {

	MENU_FILE,
		OPEN_FILE,
		CLOSE_FILE,
		SAVE_AS,
		EXIT_APP,

	MENU_EDIT,

	MENU_VIEW,

	MENU_HELP,

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

		menu.setAccelerator((KeyCombination) menuItemParams[this.ordinal()][1]);

		for(MenuItems item : items)

			menu.getItems().add(item.getMenuItem());

		return menu;
	}



	Object[][]
		menuItemParams = {

			{"File", keyCombination("Alt+F")},
			{"Open", keyCombination("Ctrl+O")},
			{"Close", keyCombination("Ctrl+W")},
			{"Save as", keyCombination("Ctrl+Shift+S")},
			{"Exit", keyCombination("Ctrl+Q"), },

			{"Edit", keyCombination("Alt+E")},

			{"View", keyCombination("Alt+V"), },

			{"Help", keyCombination("Alt+H"), }
	};

	ActionEvent[]
		actions = {

			new ActionEvent(),


	};
}
