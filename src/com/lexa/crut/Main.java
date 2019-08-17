package com.lexa.crut;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JFrame f = new JFrame("Race");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1100, 799);

        f.add(new Road());
        f.setVisible(true);
    }
}
