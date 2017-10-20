package project.game.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import project.game.controller.Controller;

public class PauseMenu extends JFrame implements KeyListener {

	private static boolean interrupted;
	private JButton button = new JButton("close");
	private JButton button2 = new JButton("open");

	// This class has not yet been fully implemented and does not take place in
	// the project I am submitting
	public PauseMenu() {
		setInterrupted(false);
	}

	public void init() {
		setTitle("Pause Menu");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(500, 300);
		setUndecorated(true);
		setLocationRelativeTo(null);
		setVisible(true);
		button.addActionListener(e -> close());
		button.setPreferredSize(new Dimension(60, 60));
		button2.setPreferredSize(new Dimension(40, 40));
		JPanel panel = new JPanel();
		panel.add(button);
		panel.add(button2);

		add(panel, BorderLayout.CENTER);
		button.setVisible(true);
	}

	private void close() {
		dispose();
	}

	public void isInterrupted() {
		if (getInterrupted() == true)
			System.out.println("hello");
		else if (getInterrupted() == false) {
			System.out.println("heyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
			// requestFocusInWindow();
			dispose();
			setVisible(false);
			return;
		}

	}

	public boolean getInterrupted() {
		return interrupted;
	}

	public void setInterrupted(boolean interrupted) {
		this.interrupted = interrupted;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_ESCAPE:

			System.out.println("ggggggggggggggggggggggggggggggggggggggggggggggggggg");

		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
