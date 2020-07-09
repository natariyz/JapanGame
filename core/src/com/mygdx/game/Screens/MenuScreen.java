package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.JapanGame;

public class MenuScreen implements Screen {

    private Stage menuStage;
    private JapanGame japanGame;

    public MenuScreen(JapanGame japanGame) {
        this.japanGame = japanGame;

        menuStage = new Stage(new ScreenViewport());

        Label title = new Label("Main menu", japanGame.getGameSkin());

        TextButton resumeButton = new TextButton("Resume", japanGame.getGameSkin());
        resumeButton.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                japanGame.resume();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        TextButton playButton = new TextButton("Play", japanGame.getGameSkin());
        playButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                ScreenManager screenManager = japanGame.getScreenManager();

                if (!screenManager.getScreens().isEmpty()) {
                    screenManager.getScreens().remove(screenManager.getScreens().size() - 1);
                }
                screenManager.openScreen(new GameScreen(japanGame));
            }

            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        TextButton exitButton = new TextButton("Exit", japanGame.getGameSkin());
        exitButton.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.exit(0);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        Table table = new Table();
        table.add(title);
        table.row();
        table.add(resumeButton);
        table.row();
        table.add(playButton);
        table.row();
        table.add(exitButton);
        table.setFillParent(true);

        menuStage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(menuStage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        menuStage.act();
        menuStage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
