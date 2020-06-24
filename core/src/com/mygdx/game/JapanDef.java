package com.mygdx.game;

import LevelObjects.Level;
import LevelObjects.LevelReader;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class JapanDef extends ApplicationAdapter {
	private GameConfig gameConfig;

	private Level level;

	private SpriteBatch batch;
	private OrthographicCamera camera;

	final float cameraRotationSpeed = 0.05f;

	public JapanDef(GameConfig gameConfig) {
		this.gameConfig = gameConfig;
	}

	@Override
	public void create () {

		batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, gameConfig.getScreenWidth(), gameConfig.getScreenHeight());

        try {
            LevelReader parser = new LevelReader();
            level = parser.readLevel("levels/level_1.xml");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

		Gdx.input.setInputProcessor(new GameScreenInputProcessor());
	}

	@Override
	public void render () {
		handleInput();

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		level.getMap().draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	private void handleInput() {
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			camera.zoom += 0.02;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
			camera.zoom -= 0.02;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			camera.rotate(-cameraRotationSpeed, 0, 0, 1);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.E)) {
			camera.rotate(cameraRotationSpeed, 0, 0, 1);
		}
	}

	private class GameScreenInputProcessor extends InputAdapter {

		private int lastTouchDraggedX, lastTouchDraggedY;

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			lastTouchDraggedX = screenX;
			lastTouchDraggedY = screenY;
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			camera.translate(lastTouchDraggedX - screenX, -(lastTouchDraggedY - screenY));
			lastTouchDraggedX = screenX;
			lastTouchDraggedY = screenY;
			return false;
		}
	}
}
