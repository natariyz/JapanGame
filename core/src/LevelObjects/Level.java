package LevelObjects;

import GameObjects.Enemy;
import MapObjects.TileMap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Level {
    private String id;
    private int defaultSpawnDelay;
    private TileMap map;
    private ArrayList<DefenceWave> waves = new ArrayList<>();
    private ArrayList<Enemy> activeEnemies = new ArrayList<>();
    private float levelTime = 0f;

    public void update(float deltaTime){
        levelTime += deltaTime;
        for(int wave = 0; wave < waves.size(); wave++){
            DefenceWave defenceWave = waves.get(wave);
            if(defenceWave.getStartTime() + defenceWave.getSpawnDelay() * defenceWave.getSpawnedEnemiesCount() < levelTime){
                if(!defenceWave.getEnemyGroups().isEmpty()){
                    EnemyGroup enemyGroup = defenceWave.getEnemyGroups().get(0);
                    Enemy enemy = enemyGroup.getEnemy().copy();
                    enemy.getSprite().setPosition(map.getStartPoint().x, map.getStartPoint().y);
                    enemy.setPosition(new Vector2(enemy.getSprite().getX(), enemy.getSprite().getY()));
                    activeEnemies.add(enemy);
                    enemyGroup.setCount(enemyGroup.getCount() - 1);
                    if (enemyGroup.getCount() < 1) defenceWave.getEnemyGroups().remove(0);
                    defenceWave.setSpawnedEnemiesCount(defenceWave.getSpawnedEnemiesCount() + 1);
                }
            }
        }

        for(int enemy = 0; enemy < activeEnemies.size(); enemy++){
            if(activeEnemies.get(enemy).move(map.getPath(), deltaTime)) activeEnemies.remove(enemy);
        }
    }

    public void draw(SpriteBatch batch){
        map.draw(batch);
        for(int enemy = 0; enemy < activeEnemies.size(); enemy++){
            activeEnemies.get(enemy).getSprite().draw(batch);
        }
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


