package org.acme.client.ungorithm.composite;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoadShape  extends AbstractShape{



    public LoadShape(Shape... shapes) {
        super(0,0);
        add(shapes);
    }

    public void add(Shape component) {
        children.add(component);
    }

    protected List<Shape> children = new ArrayList<>();

    public void add(Shape... components) {
        children.addAll(Arrays.asList(components));
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
