package com.photon.Helpers;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.control.Label;

public class GameTimer {
    private static GameTimer instance;
    private Timer localTimer1;
    private Timer localTimer2;

    public GameTimer() {
        this.localTimer1 = new Timer(true);
        this.localTimer2 = new Timer(true);
    }


    public static GameTimer getInstance() {
        if (instance == null) {
            instance = new GameTimer();
        }
        return instance;
    }

    public void startPreGameCountdown(int preGameTime, Label preGameTimerLabel, CountdownCallback callback) {
        final int[] time = {preGameTime};        
        this.localTimer1.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    preGameTimerLabel.setText(String.valueOf(time[0]));
                });
                time[0]--;
                if (time[0] < 0) {
                    localTimer1.cancel();
                    Platform.runLater(callback::onCountdownFinished);
                }
            }
        }, 0, 1000);
    }

    public void startGameCountdown(int gameTime, Label gameTimerLabel, CountdownCallback callback) {
        final int[] time = {gameTime};
        localTimer2.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    int minutes = time[0] / 60;
                    int seconds = time[0] % 60;
                    String formattedTime = String.format("%02d:%02d", minutes, seconds);
                    gameTimerLabel.setText("Time: " + formattedTime);
                });
                time[0]--;
                if (time[0] < 0) {
                    localTimer2.cancel();
                    Platform.runLater(callback::onCountdownFinished);
                }
            }
        }, 0, 1000);
    }
    
    public void stopCountdown() {
        localTimer1.cancel();
        localTimer2.cancel();
    }

}