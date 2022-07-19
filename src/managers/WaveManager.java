package managers;

import events.Wave;
import scenes.Playing;

import java.util.ArrayList;
import java.util.Arrays;

public class WaveManager {
    private Playing playing;
    private ArrayList<Wave> waves = new ArrayList<>();
    private int spawnEnemySecond = 1;
    private int enemySpawnTickLimit = 60 * spawnEnemySecond;
    private int enemySpawnTick = enemySpawnTickLimit;
    private int enemyIndex, waveIndex;
    private int spawnWaveSecond = 15;
    private int waveTickLimit = 60 * spawnWaveSecond;
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
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,0,0,0,0,1))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2,0,0,0,0,0,0,0,0,1))));
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
        return waveIndex+1 < waves.size();
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
}
