package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.Game_Engine;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("INF1009-OOP-Game // Recycle Hero");
		config.setWindowedMode(1200, 800); // Replace 800 and 600 with your desired dimensions

		// Optionally, prevent the window from being resizable
		config.setResizable(false);
		new Lwjgl3Application(new Game_Engine(), config);
	}
}