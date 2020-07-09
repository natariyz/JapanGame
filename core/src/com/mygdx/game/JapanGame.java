package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.mygdx.game.Screens.MenuScreen;
import com.mygdx.game.Screens.ScreenManager;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class JapanGame extends Game {

	private GameConfig gameConfig;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Skin gameSkin;
	private ScreenManager screenManager;

	public JapanGame(GameConfig gameConfig) {
		this.gameConfig = gameConfig;
		this.screenManager = new ScreenManager(this);
	}

	@Override
	public void create () {
		gameSkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

		batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, gameConfig.getScreenWidth(), gameConfig.getScreenHeight());

        setScreen(new MenuScreen(this));
	}

	@Override
	public void pause() {
		super.pause();

		screenManager.openScreen(new MenuScreen(this));
	}

	@Override
	public void resume() {
		super.resume();

		screenManager.popScreen();
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public ScreenManager getScreenManager() {
		return screenManager;
	}

	public Skin getGameSkin() {
		return gameSkin;
	}
}
