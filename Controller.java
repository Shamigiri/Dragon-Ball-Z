package project.game.controller;

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;

import project.game.java.Music;
import project.game.model.Player;
import project.game.model.Projectile;
import project.game.model.Sound;
import project.game.view.*;

public class Controller implements KeyListener {
	private boolean beginGame = false;
	private int choice;
	private int levelMusic = 4;
	private GameWindow window;
	private JButton exit;
	private CharSelect select;
	private TitleScreen titleScreen;
	public static GameLevel level;
	private LoadingScreen load;
	private PauseMenu pauseMenu = new PauseMenu();
	private Timer timer, timer2, timer3, timer4, timer5, timer6, transitionTimer, timerStartMoving, completeTimer,
			exitTimer, loseTimer, initiatingTimer;

	// This is the constructor, where I set initial values and assign
	// occurrences that will take place throughout the game
	public Controller() {
		titleScreen = new TitleScreen();
		window = new GameWindow();
		select = new CharSelect();
		level = new GameLevel();
		window.add(titleScreen);
		
		window.setVisible(true);
		window.addKeyListener(this);
		initiatingTimer = new Timer(1, e -> startGame());
		initiatingTimer.start();
	}

	private void init() {
		timer = new Timer(8000, e -> continueConfirm());
		timer2 = new Timer(4000, e -> showVsScreen());
		timer4 = new Timer(10000, e -> enableControls());
		timer5 = new Timer(4000, e -> transitionLoadingScreen());
		timer6 = new Timer(4000, e -> levelTransition());
		transitionTimer = new Timer(1, e -> startTransition());
		completeTimer = new Timer(4000, e -> displayProps());
		exitTimer = new Timer(4000, e -> exitGame());
		loseTimer = new Timer(4000, e -> youLose());
		window.remove(titleScreen);
		Sound.stopLevelMusic();
//		window = new GameWindow();
//		select = new CharSelect();
		select.getConfirm().addActionListener(e -> confirm());
		select.getLeft().addActionListener(e -> left());
		select.getRight().addActionListener(e -> right());
		window.add(select);
		select.getConfirm().setEnabled(false);
		window.setVisible(true);
		Sound.playLevelTheme(3);
	}

	// enableControls is the timer that allows the player to start moving once
	// the introduction animations of the level finish
	private void enableControls() {
		System.out.println("Success");
		window.addKeyListener(this);
		timer4.stop();
		transitionTimer.start();
	}

	private void startGame(){
		if(beginGame == true)
		{
			init();
			initiatingTimer.stop();
		}
	}
	// startTransition checks whether the level is finished, so it can move on
	// to the next level. If there is no next level, you proceed to the victory
	// screen
	private void startTransition() {
		if (level.isFinished() == true) {
			timer5.start();
		} else if (level.isGameDone() == true)
			completeTimer.start();
		else if (level.isLost() == true) {
			loseTimer.start();
			window.removeKeyListener(this);
		}
	}

	// transitionLoadingScreen is the timer that starts when it is time for a
	// loading screen to appear
	private void transitionLoadingScreen() {
		{
			System.out.println("I came in here");
			Sound.stopLevelMusic();
			Sound.playLoadingMusic();
			window.removeKeyListener(this);
			level.stop();
			level.removeAll();
			level.updateUI();
			window.remove(level);
			load = new LoadingScreen();
			window.add(load);
			window.repaint();
			window.validate();
			// timer4.stop();
			timer5.stop();
			level.setFinished(false);
			timer6.start();
		}

	}

	// levelTransition sets up a new instance of GameLevel, which is the level
	// to be played on
	private void levelTransition() {
		Sound.playLevelTheme(levelMusic);
		levelMusic++;
		level.setEnemyNumber(level.getEnemyNumber() + 1);
		level.counter = 1;
		if(level.getEnemyNumber() != 0)
		level = new GameLevel();
		level.enemy.getLabel().repaint();
		level.enemy.getLabel().validate();
		level.getTimer();
		level.start();
		level.repaint();
		level.validate();
		window.repaint();
		window.validate();
		window.requestFocusInWindow();
		window.remove(load);
		window.add(level);
		window.repaint();
		window.validate();
		timer4.start();
		timer6.stop();

	}

	// displayProps displays the victory screen if the player beats all three of
	// the enemies
	private void displayProps() {
		Sound.stopLevelMusic();
		Sound.playVictory();
		window.removeKeyListener(this);
		level.stop();
		level.removeAll();
		level.updateUI();
		window.remove(level);
		System.out.println("props brah");
		load = new LoadingScreen();
		window.add(load);
		window.repaint();
		window.validate();
		exit = new JButton();
		exit.setIcon(new ImageIcon("okbutton.jpg"));
		exit.setBounds(750, 600, 100, 106);
		exit.setRolloverIcon(new ImageIcon("okbutton.jpg"));
		load.add(exit);
		exit.addActionListener(e -> exitGame());
		exit.setVisible(true);
		completeTimer.stop();
		transitionTimer.stop();
		loseTimer.stop();
		timer5.stop();
	}

	// exitGame is supposed to allow you to stop the thread entirely, but I have
	// not implemented a pause menu yet
	private void exitGame() {
		window.dispose();
		System.exit(0);
	}

	// youLose is the method that gets called with the player dies
	private void youLose() {
		Sound.stopLevelMusic();
		Sound.playVictory();
		level.stop();
		level.removeAll();
		level.updateUI();
		window.remove(level);
		load = new LoadingScreen();
		window.add(load);
		window.repaint();
		window.validate();
		exit = new JButton();
		exit.setIcon(new ImageIcon("okbutton.jpg"));
		exit.setBounds(750, 600, (int) exit.getPreferredSize().getWidth(), (int) exit.getPreferredSize().getHeight());
		exit.setRolloverIcon(new ImageIcon("okbutton.jpg"));
		load.add(exit);
		exit.addActionListener(e -> exitGame());
		exit.setVisible(true);
		completeTimer.stop();
		transitionTimer.stop();
		loseTimer.stop();
		timer5.stop();
	}

	// showVsScreen is the method that diplays all the aspects of a new
	// LoadingScreen instance
	private void showVsScreen() {
		Sound.stopLevelMusic();
		load = new LoadingScreen();
		Sound.playLoadingMusic();
		window.remove(select);
		window.add(load);
		window.repaint();
		window.validate();

	}

	// confirm is the event that occurs when the player clicks the OK button in
	// character selection.
	public void confirm() {
		if (select.getI() == 1) {
			choice = 1;
			select.setTemp(new ImageIcon("mcreeImageWP.jpg"));
		} else if (select.getI() == 0) {
			choice = 0;
			select.setTemp(new ImageIcon("genjiImageWP.png"));
		} else if (select.getI() == 2) {
			choice = 2;
			select.setTemp(new ImageIcon("gokuImageWP.png"));
		}
		select.disableButtons();
		Sound.playEntrance(choice);
		select.setWallpaper(select.getTemp().getImage());
		select.repaint();
		select.validate();
		timer2.start();
		timer.start();
		window.removeKeyListener(this);
		System.out.println("going to continue");
	}

	// continueConfirm is a helper method that is called with its timer begins
	// running (this loads the actual level panel)
	public void continueConfirm() {
		Sound.playLevelTheme(choice);
		timer2.stop();
		timer.stop();
		System.out.println("entered continue");
//		level = new GameLevel();
		level.enemy.getLabel().repaint();
		level.enemy.getLabel().validate();
		level.start();
		level.repaint();
		level.validate();
		window.repaint();
		window.validate();
		window.requestFocusInWindow();
		window.remove(load);
		window.add(level);
		window.repaint();
		window.validate();
		timer4.start();
	}

	// The left button's action listener calls upon this method whenever the
	// button is pressed. Clicking this button allows the character to change in
	// the character selection screen
	public void left() {
		if (select.getI() == 0) {
			// select.setImage(new ImageIcon(select.getImageList()[2]));
			select.setI(2);
			select.getConfirm().setEnabled(true);
		} else if (select.getI() == 2) {
			// select.setImage(new ImageIcon(select.getImageList()[1]));
			select.setI(1);
			select.getConfirm().setEnabled(false);
		} else if (select.getI() == 1) {
			select.setI(0);
			select.getConfirm().setEnabled(false);
		}
		select.setImage(new ImageIcon(select.getImageList()[select.getI()]));
		select.getCharacter().setIcon(select.getImage());
	}

	// The right button's action listener calls upon this method whenever the
	// button is pressed. Clicking this button allows the character to change in
	// the character selection screen
	public void right() {
		if (select.getI() == 0) {
			// select.setImage(new ImageIcon(select.getImageList()[1]));
			select.setI(1);
			select.getConfirm().setEnabled(false);
		} else if (select.getI() == 1) {
			// select.setImage(new ImageIcon(select.getImageList()[2]));
			select.setI(2);
			select.getConfirm().setEnabled(true);
		} else if (select.getI() == 2) {
			select.setI(0);
			select.getConfirm().setEnabled(false);
		}
		select.setImage(new ImageIcon(select.getImageList()[select.getI()]));
		select.getCharacter().setIcon(select.getImage());
	}

	// the main method
	public static void main(String[] args) {
		new Controller();
	}

	// keyPressed is part of the KeyListener interface. I implement it here by
	// assigning conditions under which the player may or may not move on the
	// map
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (level.isFinished() == false) {
			if (level.isUlting() == false)
				level.getPlayer().getLabel().setVisible(true);
			switch (key) {
			case KeyEvent.VK_W:
				if (level.isUlting() == false && beginGame == true) {
					level.repaint();
					level.validate();
					window.repaint();
					window.validate();
					if (level.getPlayer().getY() > 200)
						level.getPlayer().setY(level.getPlayer().getY() - 15);
					level.getPlayer().setLabel("gokuMovingUp1.gif");
					level.getPlayer().getLabel().setBounds(level.getPlayer().getX(), level.getPlayer().getY(), 101,
							162);
				}
				break;
			case KeyEvent.VK_S:
				if (level.isUlting() == false && beginGame == true) {
					level.repaint();
					level.validate();
					window.repaint();
					window.validate();
					if (level.getPlayer().getY() < 600) {
						level.getPlayer().setY(level.getPlayer().getY() + 15);
						level.getPlayer().setLabel("gokuMovingDown.gif");
						level.getPlayer().getLabel().setBounds(level.getPlayer().getX(), level.getPlayer().getY(), 85,
								163);
					}
				}
				break;
			case KeyEvent.VK_A:
				if (level.isUlting() == false && beginGame == true) {
					level.repaint();
					level.validate();
					window.repaint();
					window.validate();
					if (level.getPlayer().getX() > 150) {
						level.getPlayer().setX(level.getPlayer().getX() - 15);
						level.getPlayer().setLabel("gokuMovingLeft.gif");
						level.getPlayer().getLabel().setBounds(level.getPlayer().getX(), level.getPlayer().getY(), 167,
								104);
					}
				}
				break;
			case KeyEvent.VK_D:
				if (level.isUlting() == false && beginGame == true) {
					level.repaint();
					level.validate();
					window.repaint();
					window.validate();
					if (level.getPlayer().getX() < 700) {
						level.getPlayer().setX(level.getPlayer().getX() + 15);
						level.getPlayer().setLabel("gokuMovingRight.gif");
						level.getPlayer().getLabel().setBounds(level.getPlayer().getX(), level.getPlayer().getY(), 170,
								105);
					}
				}
				break;
			case KeyEvent.VK_SPACE:
				if (level.isUlting() == false && beginGame == true)
					level.newProj((level.getPlayer().getX()), (level.getPlayer().getY()));
				break;
			case KeyEvent.VK_Q:
				if (level.isUlting() == false && beginGame == true)
					level.ultimateMove((level.getPlayer().getX()), (level.getPlayer().getY()));
				break;
			case KeyEvent.VK_ENTER:
				beginGame = true;
				break;
			case KeyEvent.VK_ESCAPE:
				exitGame();
				// if (pauseMenu.getInterrupted() == false) {
				// try {
				// level.wait();
				// } catch (InterruptedException e2) {
				// // TODO Auto-generated catch block
				// e2.printStackTrace();
				// }
				// pauseMenu.init();
				// pauseMenu.setInterrupted(true);
				// System.out.println("pause value is " +
				// pauseMenu.getInterrupted());
				// } else if (pauseMenu.getInterrupted() == true) {
				// pauseMenu.dispose();
				// pauseMenu.setVisible(false);
				// pauseMenu.setInterrupted(false);
				// }
				// pauseMenu.isInterrupted();
				// break;
			}

			level.repaint();
			level.validate();
			window.repaint();
			window.validate();
		}
			
	}

	// keyReleased is part of the KeyListener interface. I implement it here by
	// stating what happens whenever any of the pressed keys is released (In
	// this case, the player reverts back to his standing pose)
	@Override
	public void keyReleased(KeyEvent e) {
		level.repaint();
		level.validate();
		level.getPlayer().setLabel("gokuImage.gif");
		level.getPlayer().getLabel().setBounds(level.getPlayer().getX(), level.getPlayer().getY(), 100, 125);
		window.repaint();
		window.validate();

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public static GameLevel getLevel() {
		return level;
	}

	public static void setLevel(GameLevel level) {
		Controller.level = level;
	}
	// @Override
	// public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub

	// }

}