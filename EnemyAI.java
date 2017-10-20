package project.game.model;

import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import project.game.controller.Controller;
import project.game.view.GameLevel;

public class EnemyAI {

	// The EnemyAI class controls the enemy's automated intelligence. It is
	// responsible for the enemy's movement, the enemy's blast firing
	// functionality, and the type of enemy to be displayed

	private static JLabel enemyBullet;
	private static int enemyBulletCoorX;
	private static int enemyBulletCoorY;
	private int enemyDirection;
	private static boolean collision = false;
	private static String[] enemyFire = { "brolyFire.png", "cellFire.gif", "vegetaFire.png" };
	private static String[] idleState = { "brolyImage.gif", "cellImage.gif", "vegetaImage.gif" };
	private static String[] leftMove = { "brolyMovingLeft.gif", "cellMovingLeft.gif", "vegetaMovingLeft.gif" };
	private static String[] rightMove = { "brolyMovingRight.gif", "cellMovingRight.gif", "vegetaMovingRight.gif" };
	private static String[] upMove = { "brolyMovingUp.gif", "cellMovingUp.gif", "vegetaMovingUp.gif" };
	private static String[] downMove = { "brolyMovingDown.gif", "cellMovingDown.gif", "vegetaMovingDown.gif" };

	public static int getEnemyBulletCoorX() {
		return enemyBulletCoorX;
	}

	public static void setEnemyBulletCoorX(int enemyBulletCoorX) {
		EnemyAI.enemyBulletCoorX = enemyBulletCoorX;
	}

	public static void attack() {

		if (getEnemyBullet() != null) {
			enemyBulletCoorX -= 1;
			getEnemyBullet().setLocation(enemyBulletCoorX, enemyBulletCoorY);
			getEnemyBullet().repaint();
			getEnemyBullet().validate();
			if (getEnemyBullet().getX() + 150 > GameLevel.getPlayer().getX())
				if (getEnemyBullet().getX() + 150 < GameLevel.getPlayer().getX() + 127)
					if (getEnemyBullet().getY() + 150 > GameLevel.getPlayer().getY())
						if (getEnemyBullet().getY() < GameLevel.getPlayer().getY() + 90) {
							Controller.level.remove(getEnemyBullet());
							if (isCollision() == false && GameLevel.counter == 0)
								setCollision(true);
						}
		}
	}

	public static void createEnemyBullet() {
		System.out.println("entered the broly");
		enemyBulletCoorX = GameLevel.enemy.getX();
		enemyBulletCoorY = GameLevel.enemy.getY();
		GameLevel.getEnemy().setLabel(enemyFire[GameLevel.getEnemyNumber()]);
		Sound.playBulletSound(1);
		setEnemyBullet(new JLabel(new ImageIcon("enemyBullet.gif")));
		GameLevel.getEnemy().getLabel().setBounds(GameLevel.getEnemy().getX(), GameLevel.getEnemy().getY(), (int) GameLevel.getEnemy().getLabel().getPreferredSize().getWidth(),
				(int) GameLevel.getEnemy().getLabel().getPreferredSize().getHeight());
		getEnemyBullet().setBounds(enemyBulletCoorX, enemyBulletCoorY, 150, 150);
		Controller.level.add(getEnemyBullet());

	}

	public static void enemyMove(int number) {
		// System.out.println("he's walking");
		if (number == 1) {
			System.out.println("walking left");
			if (GameLevel.getEnemy().getX() == 800) {
				GameLevel.getEnemy().setLabel(idleState[GameLevel.getEnemyNumber()]);
			}
			if (GameLevel.getEnemy().getX() > 800) {
				GameLevel.getEnemy().setX(GameLevel.getEnemy().getX() - 10);
				GameLevel.getEnemy().setLabel(leftMove[GameLevel.getEnemyNumber()]);
			}
		} else if (number == 2) {
			System.out.println("walking right");
			if (GameLevel.getEnemy().getX() == 1300) {
				GameLevel.getEnemy().setLabel(idleState[GameLevel.getEnemyNumber()]);
			}
			if (GameLevel.getEnemy().getX() < 1300) {
				GameLevel.getEnemy().setX(GameLevel.getEnemy().getX() + 10);
				GameLevel.getEnemy().setLabel(rightMove[GameLevel.getEnemyNumber()]);
			}
		} else if (number == 3) {
			System.out.println("walking up");
			if (GameLevel.getEnemy().getY() == 200) {
				GameLevel.getEnemy().setLabel(idleState[GameLevel.getEnemyNumber()]);
			}
			if (GameLevel.getEnemy().getY() > 200) {
				GameLevel.getEnemy().setY(GameLevel.getEnemy().getY() - 10);
				GameLevel.getEnemy().setLabel(upMove[GameLevel.getEnemyNumber()]);
			}
		} else if (number == 4) {
			System.out.println("walking down");
			if (GameLevel.getEnemy().getY() == 600) {
				GameLevel.getEnemy().setLabel(idleState[GameLevel.getEnemyNumber()]);
			}
			if (GameLevel.getEnemy().getY() < 600) {
				GameLevel.getEnemy().setY(GameLevel.getEnemy().getY() + 10);
				GameLevel.getEnemy().setLabel(downMove[GameLevel.getEnemyNumber()]);
			}
		}
		GameLevel.enemy.getLabel().setBounds(GameLevel.enemy.getX(), GameLevel.enemy.getY(), (int) GameLevel.enemy.getLabel().getPreferredSize().getWidth(),
				(int) GameLevel.enemy.getLabel().getPreferredSize().getHeight());
		GameLevel.getEnemy().getLabel().repaint();
		GameLevel.getEnemy().getLabel().validate();
	}

	public static int chooseDirection() {
		Random random = new Random();
		int number;
		number = random.nextInt(4) + 1;
		while (GameLevel.getEnemy().getX() == 800 && number == 1)// 1
			number = random.nextInt(4) + 1;
		while (GameLevel.getEnemy().getX() == 1300 && number == 2)// 2
			number = random.nextInt(4) + 1;
		while (GameLevel.getEnemy().getY() == 200 && number == 3)// 3
			number = random.nextInt(4) + 1;
		while (GameLevel.getEnemy().getY() == 600 && number == 4)// 4
			number = random.nextInt(4) + 1;
		System.out.println("newly chosen direction is" + number);
		return number;
	}

	public static boolean isCollision() {
		return collision;
	}

	public static void setCollision(boolean collision) {
		EnemyAI.collision = collision;
	}

	public static JLabel getEnemyBullet() {
		return enemyBullet;
	}

	public static void setEnemyBullet(JLabel enemyBullet) {
		EnemyAI.enemyBullet = enemyBullet;
	}
}
