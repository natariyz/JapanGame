package LevelObjects;

import LevelObjects.EnemyGroup;

import java.util.ArrayList;

public class DefenceWave {
    private int startTime, spawnDelay;
    private ArrayList<EnemyGroup> enemyGroups = new ArrayList<EnemyGroup>();

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getSpawnDelay() {
        return spawnDelay;
    }

    public void setSpawnDelay(int spawnDelay) {
        this.spawnDelay = spawnDelay;
    }

    public ArrayList<EnemyGroup> getEnemyGroups() {
        return enemyGroups;
    }

    public void setEnemyGroups(ArrayList<EnemyGroup> enemyGroups) {
        this.enemyGroups = enemyGroups;
    }
}

