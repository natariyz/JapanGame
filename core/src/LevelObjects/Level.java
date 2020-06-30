package LevelObjects;

import GameObjects.Enemy;
import MapObjects.TileMap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Level {
    private String id;
    private int defaultSpawnDelay;
    private TileMap map;
    private ArrayList<DefenceWave> waves = new ArrayList<DefenceWave>();
    private Enemy enemy;

    public void update(double time){
        //enemy.move(map.getPath(), time);
    }

    public void draw(SpriteBatch batch){
        enemy.getSprite().draw(batch);

        map.draw(batch);
    }

    public void createEnemy(){
        enemy = new Enemy();
        enemy.setMoveSpeed(1);
        Texture texture = new Texture("badlogic.jpg");
        Sprite sprite = new Sprite(texture);
        sprite.setBounds(1200, 1200, 64, 64);
        enemy.setSprite(sprite);
    }

    public ArrayList<DefenceWave> getWaves() {
        return waves;
    }

    public void setWaves(ArrayList<DefenceWave> waves) {
        this.waves = waves;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDefaultSpawnDelay() {
        return defaultSpawnDelay;
    }

    public void setDefaultSpawnDelay(int defaultSpawnDelay) {
        this.defaultSpawnDelay = defaultSpawnDelay;
    }

    public TileMap getMap() {
        return map;
    }

    public void setMap(TileMap map) {
        this.map = map;
    }
}


