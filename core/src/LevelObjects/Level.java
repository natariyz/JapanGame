package LevelObjects;

import MapObjects.TileMap;

import java.util.ArrayList;

public class Level {
    private String id;
    private int defaultSpawnDelay;
    private TileMap map;
    private ArrayList<DefenceWave> waves = new ArrayList<DefenceWave>();

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


