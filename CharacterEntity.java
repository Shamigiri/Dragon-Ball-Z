package project.game.model;

import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import project.game.view.GameLevel;

public abstract class CharacterEntity {

	// This class takes care of any character that is instantiated, regardless
	// of whether it is a player or an Enemy. It assigns each their necessary
	// attributes

	private JLabel label = new JLabel();

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(String type) {
		label.setIcon(new ImageIcon(type));
		label.repaint();
		label.validate();
	}

	private int x;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	private int y;

}
