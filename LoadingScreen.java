package project.game.view;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import project.game.controller.Controller;
import project.game.java.Music;

public class LoadingScreen extends JPanel {

	private ImageIcon loadingImage;
	private ImageIcon temp;
	private Image wallpaper;
	private static int j = 0;
	private static String[] loadingScreens = { "vsImageGokuBroly.jpg", "vsImageGokuCell.png", "vsImageGokuVegeta.png",
			"winScreen.jpg", };

	// The LoadingScreen class displays which loading screen is up next,
	// depending on which enemy the player is facing. If the player happens to
	// lose, though, the losing screen is displayed instead. The victory screen
	// is handled elsewhere
	public LoadingScreen() {
		setLoadingScreen();
	}

	private void setLoadingScreen() {
		if (Controller.getLevel().isLost() == true) {
			temp = new ImageIcon("loss.jpg");
			wallpaper = temp.getImage();
			return;
		}
		temp = new ImageIcon(loadingScreens[j]);
		if (j < 3)
			j++;
		wallpaper = temp.getImage();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(wallpaper, 0, 0, getWidth(), getHeight(), this);
	}
}
