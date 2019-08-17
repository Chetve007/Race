package com.lexa.crut;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class AudioThread implements Runnable {

    @Override
    public void run() {
        try {
            while (true) {
                new Player(getClass()
                        .getClassLoader()
                        .getResourceAsStream("resources/power_rangers_cut.mp3"))
                        .play();
            }
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }
}