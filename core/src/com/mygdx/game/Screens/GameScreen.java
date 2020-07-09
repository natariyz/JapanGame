package com.mygdx.game.Screens;

import LevelObjects.Level;
import LevelObjects.LevelReader;
import MapObjects.Cell;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.JapanGame;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class GameScreen implements Screen {

    private Stage stage;
    private JapanGame japanGame;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Level level;

    private final double zoomValue = 0.1;

    public GameScreen(JapanGame japanGame){
        this.japanGame = japanGame;
        this.batch = japanGame.getBatch();
        this.camera = japanGame.getCamera();

        shapeRenderer = new ShapeRenderer();

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

        stage = new GameStage();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
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

        shapeRenderer.end();

        stage.act();
        stage.draw();
    }

    private class GameStage extends Stage{
        private int lastTouchDraggedX, lastTouchDraggedY;

        @Override
        public boolean keyDown(int keyCode) {
            if(keyCode == Input.Keys.ESCAPE){
                japanGame.pause();
            }

            return super.keyDown(keyCode);
        }

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
        shapeRenderer.dispose();
    }
}
