package com.photon.Helpers;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.photon.UDP.UDPClient;

public class GameTimer {
    private Timer timer;
    private UDPClient udpClient;

    public GameTimer(UDPClient udpClient) {
        this.udpClient = udpClient;
        this.timer = new Timer();
    }

    public void startCountdown() {
        // 30-second warning before the game starts
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("30-second before game starts.");
            }
        }, 0);

        // Game start after 30 seconds
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    udpClient.send("202");
                    System.out.println("Game Has Started");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 30000);

        // Game end after 6 minutes
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 3; i++) {
                        udpClient.send("221");
                        System.out.println("Game Over");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 390000); // 6 minutes and 30 seconds
    }

    public void stopCountdown() {
        timer.cancel();
    }
}