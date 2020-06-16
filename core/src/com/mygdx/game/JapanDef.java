package com.mygdx.game;

import NoName.SParser;
import NoName.TileMap;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class JapanDef extends ApplicationAdapter {
	SpriteBatch batch;
	TileMap map;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
        map = new TileMap();
        try {
            SParser parser = new SParser();
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
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		map.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
	    map.clear();
		batch.dispose();
	}
}
