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

public class CharSelect extends JPanel {
	public static int i = 0;
	private JButton confirm;
	private JButton left;
	private JButton right;
	private JLabel character;
	private Image wallpaper;
	private ImageIcon image;
	private ImageIcon temp;
	static String[] imageList = { "genjiImage.gif", "mcreeImage.gif", "gokuImage.gif" };

	// This constructor sets the basis of the character select screen. It is the
	// beginning display, and the first thing the player sees when launching the
	// game
	public CharSelect() {
		temp = new ImageIcon("character selection screen2.jpg");
		wallpaper = temp.getImage();
		setLayout(null);
		character = new JLabel(new ImageIcon("genjiImage.gif"));
		character.setBounds(750, 400, 100, 125);
		add(character);
		right = new JButton();
		right.setIcon(new ImageIcon("rightarrow.gif"));
		right.setBounds(1100, 400, 100, 60);
		right.setRolloverIcon(new ImageIcon("rightarrow.gif"));
		add(right);
		left = new JButton();
		left.setIcon(new ImageIcon("leftarrow.gif"));
		left.setBounds(400, 400, 100, 60);
		left.setRolloverIcon(new ImageIcon("leftarrow.gif"));
		add(left);
		confirm = new JButton();
		confirm.setIcon(new ImageIcon("okbutton.jpg"));
		confirm.setBounds(750, 600, 100, 106);
		confirm.setRolloverIcon(new ImageIcon("okbutton.jpg"));
		add(confirm);
		setVisible(true);
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
		return character;
	}

	public void setCharacter(JLabel character) {
		this.character = character;
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

	public String[] getImageList() {
		return imageList;
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