package com.mygdx.game;

import MapObjects.MapReader;
import MapObjects.TileMap;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class JapanDef extends ApplicationAdapter {
	private GameConfig gameConfig;

	private TileMap map;

	private SpriteBatch batch;
	private OrthographicCamera camera;

	public JapanDef(GameConfig gameConfig) {
		this.gameConfig = gameConfig;
	}

	@Override
	public void create () {

		batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, gameConfig.getScreenWidth(), gameConfig.getScreenHeight());

        try {
            MapReader parser = new MapReader();
            map = parser.readMap("tilemaps/test_tilemap.tmx");
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
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		map.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	private class GameScreenInputProcessor extends InputAdapter {

		private int lastTouchDraggedX, lastTouchDraggedY;

		@Override
		public boolean scrolled(int amount) {
			camera.zoom += 0.02 * amount;
			return false;
		}

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
