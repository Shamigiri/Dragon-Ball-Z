package project.game.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameWindow extends JFrame {

	public GameWindow() {

		// This is the class that creates the borderless JFrame, as specified
		// in setExtendedStat(MAXIMIZED_BOTH) below
		setTitle("Legacy Fighter 2");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		ImageIcon icon = new ImageIcon("gameIcon.png");
		setIconImage(icon.getImage());
		setUndecorated(true);
	}
}