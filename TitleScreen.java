package project.game.view;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import project.game.java.Music;
import project.game.model.Sound;

public class TitleScreen extends JPanel {
	public static int i = 0;
	private JButton confirm;
	private JButton left;
	private JButton right;
	private JLabel title;
	private JLabel go;
	private Image wallpaper;
	private ImageIcon image;
	private ImageIcon temp;

	// This constructor sets the basis of the character select screen. It is the
	// beginning display, and the first thing the player sees when launching the
	// game
	public TitleScreen() {
		temp = new ImageIcon("IntroImage.jpg");
		wallpaper = temp.getImage();
		setLayout(null);
		title = new JLabel(new ImageIcon("dbz title.png"));
		title.setBounds(600, 200, (int) title.getPreferredSize().getWidth(),
				(int) title.getPreferredSize().getHeight());
		add(title);
		go = new JLabel(new ImageIcon("confirmation.gif"));
		go.setBounds(250, 600, (int) go.getPreferredSize().getWidth(),
				(int) go.getPreferredSize().getHeight());
		add(go);
		setVisible(true);
		Sound.playLevelTheme(6);
	}

	private Image createImage(String imageurl) {
		URL imageurl3 = getClass().getClassLoader().getResource(imageurl);
		Image tempor = Toolkit.getDefaultToolkit().getImage(imageurl);
		return tempor;
	}

	private URL createUrl(String name) {
		URL imageurl3 = getClass().getClassLoader().getResource(name);
		return imageurl3;
	}

	public void setI(int i) {
		this.i = i;
	}

	public void setWallpaper(Image wallpaper) {
		this.wallpaper = wallpaper;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}

	public void setTemp(ImageIcon temp) {
		this.temp = temp;
	}

	public int getI() {
		return i;
	}

	public JButton getConfirm() {
		return confirm;
	}

	public JButton getLeft() {
		return left;
	}

	public JButton getRight() {
		return right;
	}

	public JLabel getCharacter() {
		return title;
	}

	public void setCharacter(JLabel character) {
		this.title = character;
	}

	public Image getWallpaper() {
		return wallpaper;
	}

	public ImageIcon getImage() {
		return image;
	}

	public ImageIcon getTemp() {
		return temp;
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(wallpaper, 0, 0, getWidth(), getHeight(), this);
	}

	public void disableButtons() {
		confirm.setEnabled(false);
		left.setEnabled(false);
		right.setEnabled(false);

	}

}