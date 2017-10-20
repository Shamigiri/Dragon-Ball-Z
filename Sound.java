package project.game.model;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import project.game.view.CharSelect;
import project.game.view.GameLevel;

public class Sound {

	private static File sound;
	private static Clip audioEntrance;
	private static Clip audioLevel;
	private static Clip audioBullet;
	private static Clip ultSound;
	private static Clip victory;

	public static Clip getVictory() {
		return victory;
	}

	public static void setVictory(Clip victory) {
		Sound.victory = victory;
	}

	static boolean loop = true;

	public static Clip getUltSound() {
		return ultSound;
	}

	public static void setUltSound(Clip ultSound) {
		Sound.ultSound = ultSound;
	}

	private static Clip audioLoading;
	private static Clip audioTransform;

	public static Clip getAudioTransform() {
		return audioTransform;
	}

	public static void setAudioTransform(Clip audioTransform) {
		Sound.audioTransform = audioTransform;
	}

	public static Clip getAudioLoading() {
		return audioLoading;
	}

	public static void setAudioLoading(Clip audioLoading) {
		Sound.audioLoading = audioLoading;
	}

	private static String[] charEntrance = { "genjiEntrance.wav", "mcreeEntrance.wav", "gokuEntrance.wav",
			"brolyEntranceLine.wav", "cellEntranceLine.wav", "vegetaEntranceLine.wav" };
	private static String[] levelTheme = { "genjiLevelTrack.wav", "mcreeLevelTheme.wav", "levelOneMusicGoku.wav",
			"charSelectMusic.wav", "levelTwoMusicGoku.wav", "levelThreeMusicGoku.wav", "titleScreenMusic.wav" };
	private static String[] bulletSounds = { "Ki_blast.wav", "enemyShot.wav" };

	// In this Sound class, I make methods that I call on whenever I want to
	// play a certain type of audio

	public static void playEntrance(int selection) {
		try {
			sound = new File(charEntrance[selection]);
			setAudioEntrance(AudioSystem.getClip());
			getAudioEntrance().open(AudioSystem.getAudioInputStream(sound));
			getAudioEntrance().start();
		} catch (Exception e) {
			System.out.println("Music failed to start");
		}
	}

	public static void playLevelTheme(int selection) {
		try {
			sound = new File(levelTheme[selection]);
			setAudioLevel(AudioSystem.getClip());
			getAudioLevel().open(AudioSystem.getAudioInputStream(sound));
			getAudioLevel().start();
		} catch (Exception e) {
			System.out.println("Music failed to start");
		}
	}

	public static void playBulletSound(int selection) {
		try {
			sound = new File(bulletSounds[selection]);
			setAudioBullet(AudioSystem.getClip());
			getAudioBullet().open(AudioSystem.getAudioInputStream(sound));
			getAudioBullet().start();
		} catch (Exception e) {
			System.out.println("Music failed to start");
		}
	}

	public static void playLoadingMusic() {
		try {
			sound = new File("nextBattle.wav");
			setAudioLoading(AudioSystem.getClip());
			getAudioLoading().open(AudioSystem.getAudioInputStream(sound));
			getAudioLoading().start();
		} catch (Exception e) {
			System.out.println("Music failed to start");
		}
	}

	public static void playTransformationSound() {
		try {
			sound = new File("sayainSoundEffect1.wav");
			setAudioTransform(AudioSystem.getClip());
			getAudioTransform().open(AudioSystem.getAudioInputStream(sound));
			getAudioTransform().start();
		} catch (Exception e) {
			System.out.println("Music failed to start");
		}
	}

	public static void playUltSound() {
		try {
			sound = new File("gokuUlt2.wav");
			setUltSound(AudioSystem.getClip());
			getUltSound().open(AudioSystem.getAudioInputStream(sound));
			getUltSound().start();
		} catch (Exception e) {
			System.out.println("Music failed to start");
		}
	}

	public static void playVictory() {
		try {
			if (GameLevel.isLost() == true)
				sound = new File("lose.wav");
			else
				sound = new File("victory.wav");
			setVictory(AudioSystem.getClip());
			getVictory().open(AudioSystem.getAudioInputStream(sound));
			getVictory().start();
		} catch (Exception e) {
			System.out.println("Music failed to start");
		}
	}

	public static Clip getAudioEntrance() {
		return audioEntrance;
	}

	public static void setAudioEntrance(Clip audioEntrance) {
		Sound.audioEntrance = audioEntrance;
	}

	public static Clip getAudioLevel() {
		return audioLevel;
	}

	public static void setAudioLevel(Clip audioLevel) {
		Sound.audioLevel = audioLevel;
	}

	public static void stopLevelMusic() {
		getAudioLevel().stop();
	}

	public static Clip getAudioBullet() {
		return audioBullet;
	}

	public static void setAudioBullet(Clip audioBullet) {
		Sound.audioBullet = audioBullet;
	}
}
