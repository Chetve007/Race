package com.lexa.crut;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Enemy {

    int x;
    int y;
    int v;
    String[] cars = {"car3_ch.png", "genesis_ch.png", "fiesta_ch.png"};
    Image img = getEnemy();
    Road road;

    public Image getEnemy() {
        Random r = new Random();
        return new ImageIcon(getClass().getClassLoader().getResource("resources/" + cars[r.nextInt(cars.length)])).getImage();
    }

    public Rectangle getRect() {
        return new Rectangle(x+30, y+40, 190, 90);
    }

    public Enemy(int x, int y, int v, Road road) {
        this.x = x;
        this.y = y;
        this.v = v;
        this.road = road;
    }

    public void move() {
        x = x - road.p.v + v;
    }
}
