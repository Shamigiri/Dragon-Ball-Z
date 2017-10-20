package project.game.model;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import project.game.java.Music;
import project.game.view.GameLevel;

public class Projectile extends JPanel {

	public static JLabel projectile1;
	private static Timer timer2 = new Timer();
//	private static int projCoorX = GameLevel.getCharacter().getX();
//	private static int projCoorY = GameLevel.getCharacter().getY();
	
	public static JLabel getProjectile() {
		return projectile1;
	}

	public static void setProjectile(JLabel projectile) {
		Projectile.projectile1 = projectile;
	}

	TimerTask task2 = new TimerTask() {
		public void run(){
			fire();
		}
	};
	public void start(){
		timer2.schedule(task2, 100, 100);
	}
	public Projectile(){
		projectile1 = new JLabel(new ImageIcon("blueKiBlast.gif"));
		setLayout(null);
//		projectile1.setBounds(GameLevel.getCharacter().getX(), GameLevel.getCharacter().getY(), 118, 198);
		add(projectile1);
		//projectile1.setLocation(400, 400);
		repaint();
		validate();
		projectile1.setVisible(true);
		System.out.println("hellooooooooooo");
	}
	
	public static void fire(){
		System.out.println("hiiiiiiiiiiiiiiiiiiiiiii");
		Music.playbrolyEntrance();
		projectile1.repaint();
		projectile1.validate();
		projectile1.setIcon(new ImageIcon("brolyWalking.gif"));
//		projCoorX+=5;
//		projectile1.setLocation(projCoorX, projCoorY);
//		projectile1.repaint();
//		projectile1.validate();
//		if (projCoorX == 1300)
//		{
//			projectile1.setIcon(new ImageIcon("brolyLaugh.gif"));
//			projectile1.setBounds(projCoorX, projCoorY, 125, 198);
			projectile1.repaint();
			projectile1.validate();
			Music.playbrolyEntrance();
			projectile1.setIcon(new ImageIcon("brolyImage.gif"));
		}
	}
//}
