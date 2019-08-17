package com.lexa.crut;

import javax.swing.*;
import java.awt.*;

public class Bullet {

    Image nuke = new ImageIcon(getClass().getClassLoader().getResource("resources/nuke_ch.png")).getImage();
    int x;
    int y;
    int v;
    Road road;

    public Bullet(int x, int y, int v, Road road) {
        this.x = x;
        this.y = y;
        this.v = v;
        this.road = road;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, 40, 34);
    }

    public void move() {
        x = x + 25;
    }
}
