package org.acme.client.ungorithm.composite;

import org.acme.client.ungorithm.Dao;

public class ExeEdit {


    public static void main(String[] args) {
        Editor editor = new Editor();


        Dao dao = new Dao();


        editor.loadShapes(new Rectangle(1, 1),
                          new Rectangle(1, 1),
                          new LoadShape(new Rectangle(1, 1), new Rectangle(1, 1), new Rectangle(1, 1)));


    }


}
