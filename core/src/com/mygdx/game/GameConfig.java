package com.mygdx.game;

import java.io.IOException;
import java.util.Properties;

public class GameConfig
{
    public interface ConfigLoader
    {
        Properties loadConfig(String filename) throws IOException;
    }

    private int screenWidth = 720;
    private int screenHeight = 540;

    public GameConfig(ConfigLoader loader, String configFile)
    {
        try
        {
            Properties properties = loader.loadConfig(configFile);

            screenWidth = Integer.parseInt(properties.getProperty("screenWidth", "720"));
            screenHeight = Integer.parseInt(properties.getProperty("screenHeight", "540"));
        } catch (Exception e)
        {

        }
    }

    public int getScreenWidth()
    {
        return screenWidth;
    }

    public int getScreenHeight()
    {
        return screenHeight;
    }
}
