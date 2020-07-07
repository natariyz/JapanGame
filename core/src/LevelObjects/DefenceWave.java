package LevelObjects;

import java.util.ArrayList;

public class DefenceWave {
    private int startTime, spawnDelay, spawnedEnemiesCount = 0;
    private ArrayList<EnemyGroup> enemyGroups = new ArrayList<EnemyGroup>();

    public int getSpawnedEnemiesCount() {
        return spawnedEnemiesCount;
    }

    public void setSpawnedEnemiesCount(int spawnedEnemiesCount) {
        this.spawnedEnemiesCount = spawnedEnemiesCount;
    }

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

