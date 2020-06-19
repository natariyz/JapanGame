package com.mygdx.game;

import MapObjects.MapReader;
import MapObjects.TileMap;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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
        map = new TileMap();

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
    }

	@Override
	public void render () {
		handleInput();

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

	private void handleInput() {

		Vector2 lowestCameraBorder = new Vector2(camera.viewportWidth / 2, camera.viewportHeight / 2);
		Vector2	highestCameraBorder = new Vector2(
				map.getWidth() * map.getTileWidth() - camera.viewportWidth / 2,
				map.getHeight() * map.getTileHeight() - camera.viewportHeight / 2);
		Vector2 cameraSpeed = new Vector2(gameConfig.getScreenWidth() / 100, gameConfig.getScreenHeight() / 100);

		float rotationSpeed = 0.05f;

		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			camera.zoom += 0.02;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
			camera.zoom -= 0.02;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			if (camera.position.x > lowestCameraBorder.x){
				camera.translate(-cameraSpeed.x, 0, 0);
				if(camera.position.x < lowestCameraBorder.x){
					camera.position.set(lowestCameraBorder.x, camera.position.y, 0);
				}
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			if (camera.position.x < highestCameraBorder.x){
				camera.translate(cameraSpeed.x, 0, 0);
				if(camera.position.x > highestCameraBorder.x){
					camera.position.set(highestCameraBorder.x, camera.position.y, 0);
				}
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			if (camera.position.y > lowestCameraBorder.y){
				camera.translate(0, -cameraSpeed.y, 0);
				if(camera.position.y < lowestCameraBorder.y){
					camera.position.set(camera.position.x, lowestCameraBorder.y, 0);
				}
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			if (camera.position.y < highestCameraBorder.y){
				camera.translate(0, cameraSpeed.y, 0);
				if(camera.position.y > highestCameraBorder.y){
					camera.position.set(camera.position.x, highestCameraBorder.y, 0);
				}
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			camera.rotate(-rotationSpeed, 0, 0, 1);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.E)) {
			camera.rotate(rotationSpeed, 0, 0, 1);
		}
	}
}
