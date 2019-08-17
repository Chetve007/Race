package com.lexa.crut;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {

    public static final int MAX_V = 50;
    public static final int MAX_Y = 20;
    public static final int MIN_Y = 610;

    Image imageCentre = new ImageIcon(getClass().getClassLoader().getResource("resources/bmw_c.png")).getImage();
    Image imageUp = new ImageIcon(getClass().getClassLoader().getResource("resources/bmw_l.png")).getImage();
    Image imageDown = new ImageIcon(getClass().getClassLoader().getResource("resources/bmw_r.png")).getImage();

    Image image = imageCentre;

    int v = 15;
    int dv = 0;
    int s = 0;

    int x = 40;
    int y = 300;

    int dy = 0;

    int layer1 = 0;
    int layer2 = 1100;

    public Rectangle getRect() {
        return new Rectangle(x+30, y+30, 200, 90);
    }

    public void move() {
        s += v;
        v += dv;
        if (v <= 0) v = 0;
        if (v >= MAX_V) v = MAX_V;

        y -= dy;
        if (y <= MAX_Y) y = MAX_Y;
        if (y >= MIN_Y) y = MIN_Y;

        if (layer2 - v <= 0) {
            layer1 = 0;
            layer2 = 1100;
        }
        layer1 -= v;
        layer2 -= v;
    }


    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_RIGHT:
                dv = 1;
                break;
            case KeyEvent.VK_LEFT:
                dv = -1;
                break;
            case KeyEvent.VK_UP:
                dy = 7;
                image = imageUp;
                break;
            case KeyEvent.VK_DOWN:
                dy = -7;
                image = imageDown;
                break;
//            case KeyEvent.VK_SPACE:
//                new Bullet(this.x, this.y, this.v);
//                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_LEFT) {
            dv = 0;
        }
        else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
            dy = 0;
            image = imageCentre;
        }
    }
}
