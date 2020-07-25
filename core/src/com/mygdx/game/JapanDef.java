package com.mygdx.game;

import LevelObjects.Level;
import LevelObjects.LevelReader;
import MapObjects.Cell;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class JapanDef extends ApplicationAdapter {
	private GameConfig gameConfig;

	private Level level;

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private final double zoomValue = 0.1;

	public JapanDef(GameConfig gameConfig) {
		this.gameConfig = gameConfig;
	}

	@Override
	public void create () {

		shapeRenderer = new ShapeRenderer();
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
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();


		level.update(Gdx.graphics.getDeltaTime());
		level.draw(batch);

		batch.end();

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(0, 1, 0, 1);

		for(int x = 0; x < level.getMap().getCells().length; x++){
			for (int y = 0; y < level.getMap().getCells()[0].length; y++){
				Cell cell = level.getMap().getCells()[x][y];
				if(cell.getPoints() != null){
					for(int point = 0; point < cell.getPoints().size(); point++){
						shapeRenderer.circle(cell.getPoints().get(point).x, cell.getPoints().get(point).y, 5);
					}
				}
			}
		}

		ArrayList<Vector2> roadPolygon = level.getMap().getRoadPolygon();

		for(int point = 0; point < roadPolygon.size() - 1; point++){
			shapeRenderer.line(roadPolygon.get(point), roadPolygon.get(point + 1));
		}
		shapeRenderer.line(roadPolygon.get(0), roadPolygon.get(roadPolygon.size() - 1));

		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	private class GameScreenInputProcessor extends InputAdapter {

		private int lastTouchDraggedX, lastTouchDraggedY;

		@Override
		public boolean scrolled(int amount) {
			camera.zoom += zoomValue * amount;
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
