package org.acme.client.ungorithm.composite;

public class Editor {


    private LoadShape allShapes = new LoadShape();

    public void loadShapes(Shape... shapes) {
        allShapes.add(shapes);

    }

}
