package managers;

import events.Wave;
import scenes.Playing;

import java.util.ArrayList;
import java.util.Arrays;

public class WaveManager {
    private final Playing playing;
    private final ArrayList<Wave> waves = new ArrayList<>();
    private int spawnEnemySecond = 1;
    private final int enemySpawnTickLimit = 60 * spawnEnemySecond;
    private int enemySpawnTick = enemySpawnTickLimit;
    private int spawnWaveSecond = 15;
    private final int waveTickLimit = 60 * spawnWaveSecond;
    private int enemyIndex, waveIndex;
    private int waveTick = 0;
    private boolean waveStartTimer, waveTickTimerOver;

    public WaveManager(Playing playing) {
        this.playing = playing;
        createWaves();
    }

    public void update() {
        if(enemySpawnTick < enemySpawnTickLimit) {
            enemySpawnTick++;
        }
        if(waveStartTimer) {
            waveTick++;
            if(waveTick >= waveTickLimit) {
                waveTickTimerOver = true;
            }
        }
    }

    public void increaseWaveIndex() {
        waveIndex++;
        waveTick = 0;
        waveTickTimerOver = false;
        waveStartTimer = false;
    }

    public boolean isWaveTimerOver() {
        return waveTickTimerOver;
    }

    public void startWaveTimer() {
        waveStartTimer = true;
    }

    public int getNextEnemy() {
        enemySpawnTick = 0;
        return waves.get(waveIndex).getEnemyList().get(enemyIndex++);
    }

    private void createWaves() {
        int random = (int) (Math.random() * 4 +1);
        for(int i = 0; i < 10; i++) {
            waves.add(new Wave(new ArrayList<>(Arrays.asList(2, random, 0, random, 2, random, 3, random, 1, random))));
        }
    }

    public ArrayList<Wave> getWaves() {
        return waves;
    }

    public boolean isTimeForNewEnemy() {
        return enemySpawnTick >= enemySpawnTickLimit;
    }

    public boolean isThereMoreEnemiesInWave() {
        return enemyIndex < waves.get(waveIndex).getEnemyList().size();
    }

    public boolean isThereMoreWaves() {
        return waveIndex + 1 < waves.size();
    }

    public void resetEnemyIndex() {
        enemyIndex = 0;
    }

    public int getWaveIndex() {
        return waveIndex;
    }

    public float getTimeLeft() {
        float secondsLeft = waveTickLimit - waveTick;
        return secondsLeft / 60.0f;
    }

    public boolean isWaveTimerStarted() {
        return waveStartTimer;
    }

    public void restWaveManager() {
        waves.clear();
        createWaves();
        enemyIndex = 0;
        waveIndex = 0;
        waveStartTimer = false;
        waveTickTimerOver = false;
        waveTick = 0;
        enemySpawnTick = 0;
        spawnWaveSecond = 0;
        spawnEnemySecond = 0;
    }
}
