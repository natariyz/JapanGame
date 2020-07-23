package com.mygdx.game.Screens;

import com.badlogic.gdx.Screen;
import com.mygdx.game.JapanGame;

import java.util.Stack;

public class ScreenManager {

    private JapanGame japanGame;
    private Stack<Screen> screens;

    public ScreenManager(JapanGame japanGame) {
        this.japanGame = japanGame;
        this.screens = new Stack<>();
    }

    public void openScreen(Screen screen)
    {
        japanGame.setScreen(screen);
        screens.add(screen);
    }

    public Screen popScreen()
    {
        Screen screen = screens.pop();
        if (!screens.empty())
        {
            japanGame.setScreen(screens.peek());
        }
        else
        {
            japanGame.setScreen(null);
        }
        return screen;
    }

    public Stack<Screen> getScreens() {
        return screens;
    }
}
