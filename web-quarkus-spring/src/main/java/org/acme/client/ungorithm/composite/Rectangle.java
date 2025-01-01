package org.acme.client.ungorithm.composite;

import java.awt.Graphics;

public class Rectangle extends AbstractShape{


    public Rectangle(int x, int y) {
        super(x, y);
    }


    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void move(int x, int y) {

    }

    @Override
    public boolean isInsideBounds(int x, int y) {
        return false;
    }

    @Override
    public void select() {

    }

    @Override
    public void unSelect() {

    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public void paint(Graphics graphics) {

    }
}
