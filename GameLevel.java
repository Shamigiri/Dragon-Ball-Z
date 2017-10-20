package project.game.view;

import java.awt.Image;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import project.game.java.*;
import project.game.model.Enemy;
import project.game.model.EnemyAI;
import project.game.model.Player;
import project.game.model.Projectile;
import project.game.model.Sound;

public class GameLevel extends JPanel {
	private boolean flag = true;
	private ImageIcon temp;
	private Image wallpaper;
	private static Player player;
	public static JLabel charAvatar;
	public static JLabel enAvatar;
	private boolean ulting = false;
	public static ArrayList<JLabel> projectile;
	public static ArrayList<Integer> projx;
	public static ArrayList<Integer> projy;
	public JLabel ultimate = new JLabel(new ImageIcon("gokuUlt.gif"));
	private static int enemyHealth = 10;
	boolean cancelled = false;
	private static boolean finished = false;
	private static int k = -1;
	private static JProgressBar jpPlayer;
	private static JProgressBar jpEnemy;
	private static String[] introImage = { "gokutransformationReal.gif" };
	private static int enemyNumber = 0;
	private TimerTask createEnemyBullet2;
	private long move = 0;
	private int randomDirection;
	private static int playerHealth = 10;
	private boolean enemyMovementBoolean = false;
	TimerTask enemyMovement2;
	private int playerUltX = 1600;
	private int playerUltY = 190;
	private static JProgressBar playerUlt;
	private int playerUltProgress = 0;
	private static String[] enemyNames = { "Broly", "Cell", "Vegeta" };
	private static int healthUpdate = 10;
	private static int enemySpeed = 100;
	private static int changeDirectionRate = 3000;
	private static int enemyBulletSpeed = 3000;
	public static Timer timer = new Timer();
	private static boolean gameDone = false;
	private static boolean lost = false;
	public static int counter = 0;

	public static boolean isLost() {
		return lost;
	}

	public static void setLost(boolean lost) {
		GameLevel.lost = lost;
	}

	private static String[] enemyHurt = { "brolyHurt.gif", "cellHurt.gif", "vegetaHurt.gif" };
	private static String[] enemyDie = { "brolyDie.gif", "cellDie.gif", "vegetaDie.gif" };
	private static String[] playerAvatar = { "gokuAvatarz.PNG" };
	private static String[] enemyAvatar = { "brolyAvatarz.PNG", "cellAvatar.png", "vegetaAvatar.png" };
	private static String[] levelBackground = { "character selection screen.jpg", "waterfallBackground.gif",
			"dbzstage.jpg" };

	// The constructor initializes all of the components of the level. Following
	// the constructor, a bunch of TimerTasks are running alongside the game,
	// each checking specific conditions, like when the enemy should fire, if a
	// player or enemy dies, and if it's ok to fire another bullet from the
	// player's character. This class is also responsible for most of the GUI
	// that is shown and constantly changing
	public GameLevel() {
		init();
		setVisible(true);
	}

	private void init() {
		k++;
		player = new Player();
		enemy = new Enemy();
		projx = new ArrayList<Integer>();
		projy = new ArrayList<Integer>();
		projectile = new ArrayList<JLabel>();
		charAvatar = new JLabel(new ImageIcon(playerAvatar[0]));
		charAvatar.setBounds(0, 0, 184, 184);
		add(charAvatar);
		playerHealthBarCreation();
		playerUltBarCreation();
		enemyCoorInit();
		enAvatar = new JLabel(new ImageIcon(enemyAvatar[enemyNumber]));
		enAvatar.setBounds(1418, 1, 184, 184);
		add(enAvatar);
		enemyHealthBarCreation();
		temp = new ImageIcon(levelBackground[k]);
		wallpaper = temp.getImage();
		setLayout(null);
		enemy.setLabel("brolyWalking.gif");
		enemy.getLabel().setBounds(enemy.getX(), enemy.getY(), (int) enemy.getLabel().getPreferredSize().getWidth(),
				(int) enemy.getLabel().getPreferredSize().getHeight());
		add(enemy.getLabel());
		repaint();
		validate();
		
	}

	private void enemyCoorInit() {
		if (enemyNumber == 0) {
			enemy.setX(1600);
			enemy.setY(550);
		} else {
			enemy.setX(1300);
			enemy.setY(550);
		}
	}

	private void playerUltBarCreation() {
		setPlayerUlt(new JProgressBar());
		getPlayerUlt().setMaximum(5);
		getPlayerUlt().setMinimum(0);
		getPlayerUlt().setBounds(184, 55, 250, 50);
		getPlayerUlt().setStringPainted(true);
		getPlayerUlt().setForeground(Color.yellow);
		// getPlayerUlt().setBackground(Color.red);
		getPlayerUlt().setString("Ultimate");
		getPlayerUlt().setValue(0);
		add(getPlayerUlt());
	}

	private void playerHealthBarCreation() {
		setJpPlayer(new JProgressBar());
		getJpPlayer().setMaximum(10);
		getJpPlayer().setMinimum(0);
		getJpPlayer().setBounds(184, 1, 500, 50);
		getJpPlayer().setStringPainted(true);
		getJpPlayer().setForeground(Color.green);
		getJpPlayer().setBackground(Color.red);
		getJpPlayer().setString("Goku");
		getJpPlayer().setValue(playerHealth);
		// UIManager.put("jpPlayer.background",Color.BLUE);
		// UIManager.put("jpPlayer.foreground",Color.GREEN);
		add(getJpPlayer());
	}

	private void enemyHealthBarCreation() {
		jpEnemy = new JProgressBar();
		jpEnemy.setStringPainted(true);
		jpEnemy.setForeground(Color.green);
		jpEnemy.setMaximum(enemyHealth);
		jpEnemy.setMinimum(0);
		jpEnemy.setBounds(918, 1, 500, 50);
		jpEnemy.setStringPainted(true);
		jpEnemy.setForeground(Color.green);
		jpEnemy.setBackground(Color.red);
		jpEnemy.setString(enemyNames[enemyNumber]);
		jpEnemy.setValue(enemyHealth);
		add(jpEnemy);
	}

	TimerTask enemyIntro = new TimerTask() {
		public void run() {
			if (enemyNumber == 0)
				introduceBroly();
			else if (enemyNumber == 1)
				introduceCell();
			else
				introduceVegeta();
		}
	};

	TimerTask playerIntro = new TimerTask() {
		public void run() {
			introducePlayer();
		}

		private void introducePlayer() {
			if (enemyNumber == 0) {
				player.setLabel(introImage[0]);
				Sound.playTransformationSound();
			} else
				player.setLabel("gokuImage.gif");
			player.setX(150);
			player.setY(600);
			player.getLabel().setBounds(player.getX(), player.getY(), 95, 164);
			add(player.getLabel());
			player.getLabel().setVisible(true);
			playerIntro.cancel();
		}
	};

	TimerTask fire = new TimerTask() {
		public void run() {
			fire();
		}
	};

	TimerTask hit = new TimerTask() {
		public void run() {
			hit();
		}
	};

	TimerTask enemyAttack = new TimerTask() {
		public void run() {
			EnemyAI.attack();

		}
	};

	private void decrementPlayerHealth() {
		EnemyAI.setCollision(false);
		playerHealth--;
		// System.out.println( "decreased health" + playerHealth);
		jpPlayer.setValue(playerHealth);
		return;
	}

	TimerTask createEnemyBullet = new TimerTask() {
		public void run() {
			EnemyAI.createEnemyBullet();
			counter = 0;
		}
	};

	TimerTask enemyMovementUpdate = new TimerTask() {
		public void run() {
			System.out.println("inside");
			randomDirection = EnemyAI.chooseDirection();
		}
	};

	TimerTask enemyMovement = new TimerTask() {
		public void run() {
			EnemyAI.enemyMove(randomDirection);
		}
	};

	TimerTask checkTransform = new TimerTask() {
		public void run() {
			if(enemyNumber == 2 && enemyHealth <= 15)
			{
				createEnemyBullet.cancel();
				enemyMovement.cancel();
				enemyMovementUpdate.cancel();
				enemyMovement2 = new TimerTask() {
					public void run() {

						EnemyAI.enemyMove(randomDirection);
					}
				};
				createEnemyBullet2 = new TimerTask() {
					public void run() {
						EnemyAI.createEnemyBullet();
						counter = 0;
					}
				};
				if (enemyHealth > 0) {
					timer.scheduleAtFixedRate(enemyMovement2, 5000, enemySpeed);
					timer.scheduleAtFixedRate(createEnemyBullet2, 5000, enemyBulletSpeed);
				}
				boolean bool = true;
				if(bool = true)
				{
					bool = false;
					System.out.println("we made it into the transform");
					startTransformation();
//				timer.scheduleAtFixedRate(enemyMovement1, 5000, enemySpeed);
//				timer.scheduleAtFixedRate(enemyMovementUpdate1, 5000, changeDirectionRate);
				}
				checkTransform.cancel();
			}
		}
	};
	
	TimerTask decrementHealth = new TimerTask() {
		public void run() {
			if (EnemyAI.isCollision() == true) {

				if (counter == 0) {
					counter++;
					decrementPlayerHealth();
					System.out.println("this is the new player health bois" + playerHealth);
					EnemyAI.setCollision(false);
				}
				if (counter > 0)
					return;
			}
			System.out.println(playerHealth);
		}
	};

	TimerTask checkEnemyDeath = new TimerTask() {
		public void run() {
			if (enemyHealth <= 0) {
				enemyMovement.cancel();
				enemyAttack.cancel();
				createEnemyBullet.cancel();
				enemy.setLabel(enemyDie[enemyNumber]);
				enemy.getLabel().setBounds(enemy.getX(), enemy.getY(),
						(int) enemy.getLabel().getPreferredSize().getWidth(),
						(int) enemy.getLabel().getPreferredSize().getHeight());
				enemy.getLabel().repaint();
				enemy.getLabel().revalidate();
				if (enemyNumber == 2)
					setGameDone(true);
				else
					setFinished(true);
				healthUpdate += 10;
				System.out.println("The level has been completed");
				enemyHealth += healthUpdate;
				playerHealth = 10;
				ultimate.setVisible(false);
				player.getLabel().setVisible(true);
				enemySpeed -= 40;
				changeDirectionRate -= 1200;
				enemyBulletSpeed -= 500;
			}
		}
	};

	TimerTask checkPlayerDeath = new TimerTask() {
		public void run() {
			if (playerHealth <= 0) {
				player.setLabel("gokuDie.gif");
				player.getLabel().setBounds(player.getX(), player.getY(),
						(int) player.getLabel().getPreferredSize().getWidth(),
						(int) player.getLabel().getPreferredSize().getHeight());
				enemyMovement.cancel();
				enemyAttack.cancel();
				createEnemyBullet.cancel();
				setLost(true);
				System.out.println("The level has been completed");
				ultimate.setVisible(false);
				// character.setVisible(true);
				player.getLabel().setVisible(true);
			}
		}
	};

	public void hit() {
		for (int i = 0; i < projectile.size(); i++) {
			if (projx.get(i) + 117 > enemy.getX())
				if (projx.get(i) + 117 < enemy.getX() + 118)
					if (projy.get(i) + 99 > enemy.getY())
						if (projy.get(i) < enemy.getY() + 198) {
							remove(projectile.get(i));
							projectile.remove(i);
							projx.remove(i);
							projy.remove(i);
							enemy.setLabel(enemyHurt[getEnemyNumber()]);
							enemy.getLabel().setBounds(enemy.getX(), enemy.getY(),
									(int) enemy.getLabel().getPreferredSize().getWidth(),
									(int) enemy.getLabel().getPreferredSize().getHeight());
							enemy.getLabel().repaint();
							enemy.getLabel().revalidate();
							repaint();
							validate();
							enemyHealth--;
							jpEnemy.setValue(enemyHealth);
							playerUltProgress++;
							playerUlt.setValue(playerUltProgress);
						}
		}
	}

	// Instantiates a new player projectile (The player blast)
	public void newProj(int x, int y) {
		if (flag) {
			projx.add(x);
			projy.add(y);
			projectile.add(new JLabel(new ImageIcon("blueKiBlast.gif")));
			projectile.get(projectile.size() - 1).setBounds(x, y, 117, 99);
			add(projectile.get(projectile.size() - 1));
			projectile.get(projectile.size() - 1).repaint();
			projectile.get(projectile.size() - 1).validate();
			repaint();
			validate();
			player.setLabel("gokuFire.gif");
			Sound.playBulletSound(0);
			player.getLabel().setBounds(player.getX(), player.getY(), 131, 144);
			player.getLabel().repaint();
			player.getLabel().revalidate();
			flag = false;
		}
	}

	private void enemyStop() {
		if (enemyMovement2 != null)
			enemyMovement2.cancel();
	}

	private void bulletStop() {
		if (createEnemyBullet2 != null)
			createEnemyBullet2.cancel();
	}

	// The method that is called upon when the player decides to unleash his/her
	// ultimate move. This also cancels other enemy TimerTasks so that the enemy
	// would remain locked into position until the player finishes using the
	// ultimate move
	public void ultimateMove(int x, int y) {
		if (playerUltProgress >= 5) {
			playerUltProgress = 0;
			playerUlt.setValue(playerUltProgress);
			setUlting(true);
			TimerTask ultMovement = new TimerTask() {
				public void run() {

					if (isUlting() == true) {
						enemyMovement.cancel();
						createEnemyBullet.cancel();
						enemyStop();
						bulletStop();
						ultimate.setBounds(player.getX(), player.getY(), 1600, 190);
						ultimate.setVisible(true);
						add(ultimate);
						player.getLabel().setVisible(false);
						// character.repaint();
						// character.validate();
						// repaint();
						validate();
						Sound.playUltSound();
						while (Sound.getUltSound().getMicrosecondLength() != Sound.getUltSound()
								.getMicrosecondPosition()) {
							ultimate.setVisible(true);
							ultimate.repaint();
							ultimate.validate();
							repaint();
							validate();
							ultimate.setIcon(new ImageIcon("gokuUlt.gif"));
							ultimate.repaint();
							ultimate.validate();
						}
						if (player.getX() < enemy.getX())
						// if (playerUltX + 1600 < getBrolyCoorX() + 118)
						// if (playerUltY + 190 > brolyCoorY)
						// if (playerUltY < brolyCoorY + 198)
						{
							enemyHealth -= 5;
							jpEnemy.setValue(enemyHealth);
							if (enemyHealth <= 0) {
								enemyMovementUpdate.cancel();
								System.out.println("the dude is dead.");
								return;
							}

						}
						remove(ultimate);
						setUlting(false);
					}
					player.getLabel().setVisible(true);
					// newEnemyMovementInstance();

					System.out.println("made it out");
					enemyMovement2 = new TimerTask() {
						public void run() {

							EnemyAI.enemyMove(randomDirection);
						}
					};
					createEnemyBullet2 = new TimerTask() {
						public void run() {
							EnemyAI.createEnemyBullet();
							counter = 0;
						}
					};
					if (enemyHealth > 0) {
						timer.scheduleAtFixedRate(enemyMovement2, 0, enemySpeed);
						timer.scheduleAtFixedRate(createEnemyBullet2, 0, enemyBulletSpeed);
					}
				}
			};

			timer.schedule(ultMovement, 0);
		}
	}

	public void fire() {
		if (projectile.size() != 0) {
			for (int i = 0; i < projectile.size(); i++) {
				if (projx.get(i) != null && projy.get(i) != null) {
					int t = projx.get(i);
					t += 1;
					projx.set(i, t);
					projectile.get(i).setLocation(t, projy.get(i));
					projectile.get(i).repaint();
					projectile.get(i).validate();
				}
			}
		}
	}

	TimerTask refresh = new TimerTask() {
		public void run() {
			for (int i = 0; i < projx.size(); i++) {
				if (projx.get(i) > 1600) {
					projx.remove(i);
					projy.remove(i);
					remove(projectile.get(i));
					projectile.remove(i);
					repaint();
					validate();
				}
				for (int j = 0; j < projectile.size(); j++) {
					projectile.get(j).repaint();
					projectile.get(j).validate();
				}
			}
			resetFlag();
		}
	};

	public void resetFlag() {
		flag = true;
	}

	// start is the method that schedules all of the TimerTasks, allowing them
	// to begin running until they are cancelled
	public void start() {
		timer.scheduleAtFixedRate(enemyIntro, 0, 100);
		timer.scheduleAtFixedRate(playerIntro, 0, 100);
		timer.scheduleAtFixedRate(fire, 10000, 1);
		timer.scheduleAtFixedRate(refresh, 10000, 1000);
		timer.scheduleAtFixedRate(enemyAttack, 10000, 1);
		timer.schedule(hit, 10000, 1);
		timer.scheduleAtFixedRate(checkEnemyDeath, 7000, 500);
		timer.scheduleAtFixedRate(checkPlayerDeath, 7000, 500);
		timer.scheduleAtFixedRate(createEnemyBullet, 10000, enemyBulletSpeed);
		timer.scheduleAtFixedRate(enemyMovement, 10000, enemySpeed);
		timer.scheduleAtFixedRate(enemyMovementUpdate, 10000, changeDirectionRate);
		timer.scheduleAtFixedRate(decrementHealth, 10000, 100);
//		timer.schedule(checkTransform, 1, 1000);
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	// The following three introductory methods are called for each respective
	// level. Since each enemy has his own way of introducing himself into the
	// level, I made three different methods, each getting called depending on
	// which enemy the player is facing
	public void introduceBroly() {
		enemy.getLabel().repaint();
		enemy.getLabel().validate();
		enemy.setLabel("brolyWalking.gif");
		enemy.setX(enemy.getX() - 5);
		enemy.getLabel().setLocation(enemy.getX(), enemy.getY());
		enemy.getLabel().repaint();
		enemy.getLabel().validate();
		repaint();
		validate();
		if (enemy.getX() == 1300) {
			Sound.playEntrance(3);
			while (Sound.getAudioEntrance().getMicrosecondLength() != Sound.getAudioEntrance()
					.getMicrosecondPosition()) {
				enemy.setLabel("brolyLaugh.gif");
				enemy.getLabel().setBounds(enemy.getX(), enemy.getY(),
						(int) enemy.getLabel().getPreferredSize().getWidth(),
						(int) enemy.getLabel().getPreferredSize().getHeight());
				enemy.getLabel().repaint();
				enemy.getLabel().validate();
			}
			enemy.setLabel("brolyImage.gif");
			enemyIntro.cancel();
		}
	}

	public void introduceCell() {
		enemy.getLabel().repaint();
		enemy.getLabel().validate();
		enemy.setLabel("cellEntrance.gif");
		enemy.getLabel().setBounds(enemy.getX(), enemy.getY(), (int) enemy.getLabel().getPreferredSize().getWidth(),
				(int) enemy.getLabel().getPreferredSize().getHeight());
		enemy.getLabel().repaint();
		enemy.getLabel().validate();
		repaint();
		validate();
		Sound.playEntrance(4);
		enemyIntro.cancel();
	}

	public void introduceVegeta() {
		enemy.getLabel().repaint();
		enemy.getLabel().validate();
		enemy.setLabel("vegetaEntrance.gif");
		enemy.getLabel().setBounds(enemy.getX(), enemy.getY(), (int) enemy.getLabel().getPreferredSize().getWidth(),
				(int) enemy.getLabel().getPreferredSize().getHeight());
		enemy.getLabel().repaint();
		enemy.getLabel().validate();
		repaint();
		validate();
		Sound.playEntrance(5);
		enemyIntro.cancel();
	}
	
	public void startTransformation() {
		enemy.getLabel().repaint();
		enemy.getLabel().validate();
		enemy.setLabel("vegetaTransformation1.gif");
		enemy.getLabel().setBounds(enemy.getX(), enemy.getY(), (int) enemy.getLabel().getPreferredSize().getWidth(),
				(int) enemy.getLabel().getPreferredSize().getHeight());
		enemy.getLabel().repaint();
		enemy.getLabel().validate();
		repaint();
		validate();
		Sound.playEntrance(4);
		enemyIntro.cancel();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(wallpaper, 0, 0, getWidth(), getHeight(), this);
	}

	public boolean isUlting() {
		return ulting;
	}

	public void setUlting(boolean ulting) {
		this.ulting = ulting;
	}

	public static boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public static JProgressBar getPlayerUlt() {
		return playerUlt;
	}

	public static void setPlayerUlt(JProgressBar playerUlt) {
		GameLevel.playerUlt = playerUlt;
	}

	public static int getEnemyNumber() {
		return enemyNumber;
	}

	public static void setEnemyNumber(int enemyNumber) {
		GameLevel.enemyNumber = enemyNumber;
	}

	public static Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public static Enemy enemy;

	public static Enemy getEnemy() {
		return enemy;
	}

	public static boolean isGameDone() {
		return gameDone;
	}

	public static void setGameDone(boolean gameDone) {
		GameLevel.gameDone = gameDone;
	}

	// stop is the method that cancels all of the TimerTasks, stopping them from
	// being run
	public void stop() {
		enemyIntro.cancel();
		playerIntro.cancel();
		fire.cancel();
		refresh.cancel();
		enemyAttack.cancel();
		hit.cancel();
		checkEnemyDeath.cancel();
		checkPlayerDeath.cancel();
		createEnemyBullet.cancel();
		enemyMovement.cancel();
		enemyMovementUpdate.cancel();
		decrementHealth.cancel();
	}

	public static JProgressBar getJpPlayer() {
		return jpPlayer;
	}

	public static void setJpPlayer(JProgressBar jpPlayer) {
		GameLevel.jpPlayer = jpPlayer;
	}

}