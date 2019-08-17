package com.lexa.crut;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class Road extends JPanel implements ActionListener, Runnable {

    Timer mainTimer = new Timer(20, this);
    Image img = new ImageIcon(getClass().getClassLoader().getResource("resources/road_ch.jpg")).getImage();
    Image boom = new ImageIcon(getClass().getClassLoader().getResource("resources/boom_ch.png")).getImage();
    Player p = new Player();
    List<Enemy> enemies = Collections.synchronizedList(new ArrayList<>());

    Thread enemiesFactory = new Thread(this);
    Thread audioThread = new Thread(new AudioThread());
    List<Bullet> bullets = Collections.synchronizedList(new ArrayList<>());

    public Road() {
        mainTimer.start();
        enemiesFactory.start();
        audioThread.start();
        addKeyListener(new MyKeyAdapter());
        setFocusable(true);
    }

    @Override
    public void run() {
        while (true) {
            Random rand = new Random();
            try { Thread.sleep(rand.nextInt(2000)); } catch (InterruptedException e) {}
            enemies.add(new Enemy(1200,
                    rand.nextInt((610-20))+20,
                    rand.nextInt(10),
                    this));
        }
    }

    private class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            p.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_SPACE) {
                bullets.add(new Bullet(p.x + p.image.getWidth(null) - 60, p.y + 10, p.v, Road.this));
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            p.keyReleased(e);
        }
    }

    public void paint(Graphics g) {
        g.drawImage(img, p.layer1, 0, null);
        g.drawImage(img, p.layer2, 0, null);
        g.drawImage(p.image, p.x, p.y, null);

        double v = 200 / Player.MAX_V * p.v;
        g.setColor(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 20);
        g.setFont(font);
        g.drawString("Скорость: " + v + " км/ч", 100, 30);

        createEnemies(g);

        createBullets(g);

        deadShot(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        p.move();
        repaint();
        testCollisionsWithEnemies();
        testWin();
    }

    private void testCollisionsWithEnemies() {
        Iterator<Enemy> enem = enemies.iterator();
        while (enem.hasNext()) {
            Enemy e = enem.next();
            if (p.getRect().intersects(e.getRect())) {
                JOptionPane.showMessageDialog(this, "You're lose!");
                audioThread.stop();
                enemiesFactory.stop();
                mainTimer.stop();
                Main.main(new String[0]);
            }
        }
    }

    private void testWin() {
        if (p.s > 20000) {
            JOptionPane.showMessageDialog(this, "You're WON!!!");
            audioThread.stop();
            enemiesFactory.stop();
            mainTimer.stop();
            Main.main(new String[0]);
        }
    }

    private void createEnemies(Graphics g) {
        Iterator<Enemy> enem = enemies.iterator();
        while (enem.hasNext()) {
            Enemy e = enem.next();
            if (e.x >= 2400 || e.x <= -2400)
                enem.remove();
            else {
                e.move();
                g.drawImage(e.img, e.x, e.y, null);
            }
        }
    }

    private void createBullets(Graphics g) {
        Iterator<Bullet> bul = bullets.iterator();
        while (bul.hasNext()) {
            Bullet bu = bul.next();
            if (bu.x > 2400)
                bul.remove();
            else {
                bu.move();
                g.drawImage(bu.nuke, bu.x, bu.y, null);
            }
        }
    }

    private void deadShot(Graphics g) {
        Iterator<Bullet> bul = bullets.iterator();
        Iterator<Enemy> enem = enemies.iterator();
        while (bul.hasNext()) {
            Bullet bu = bul.next();
            while (enem.hasNext()) {
                Enemy en = enem.next();
                if (bu.getRect().intersects(en.getRect())) {
                    enem.remove();
                    bul.remove();
                    g.drawImage(boom, en.x, en.y, null);
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}