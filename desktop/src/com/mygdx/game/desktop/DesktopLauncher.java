package com.mygdx.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglFileHandle;
import com.mygdx.game.GameConfig;
import com.mygdx.game.JapanDef;

import java.io.IOException;
import java.util.Properties;

public class DesktopLauncher {

	private static final String CONFIG_FILE = "../../game.config";

	public static void main (String[] arg) {
		GameConfig gameConfig = new GameConfig(desktopConfigLoader, CONFIG_FILE);
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = gameConfig.getScreenWidth();
		config.height = gameConfig.getScreenHeight();
		new LwjglApplication(new JapanDef(gameConfig), config);
	}

	private final static GameConfig.ConfigLoader desktopConfigLoader = new GameConfig.ConfigLoader() {
		@Override
		public Properties loadConfig(String filename) throws IOException
		{
			Properties properties = new Properties();
			properties.load(new LwjglFileHandle(CONFIG_FILE, Files.FileType.Internal).reader());
			return properties;
		}
	};
}
