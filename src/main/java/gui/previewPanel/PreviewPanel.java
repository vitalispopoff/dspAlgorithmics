package gui.previewPanel;

import gui.Root;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;


public class PreviewPanel extends StackPane {

	private final Root
		root;

	private static final double
		margin = 20.;

	private final Font
		font = new Font("Arial", 10);

	private boolean
		theFlag = true;


	public PreviewPanel(Root r) {

		root = r;
	}
}