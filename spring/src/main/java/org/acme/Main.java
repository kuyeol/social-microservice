package org.acme;

import org.acme.core.domain.NoteBook;
import org.acme.core.service.Factory;
import org.acme.ext.terran.entity.M1Book;
import org.acme.ext.terran.entity.MacBook;
import org.acme.ext.terran.service.Mac;
import org.acme.ext.terran.service.MacFactory;
import org.acme.ext.zerg.service.WinFactory;

public class Main
{

    public static void main(String[] args)
    {
        Factory  factory;
        NoteBook anyNote;
        factory = new WinFactory();
        factory = new MacFactory();
        MacFactory macFactory = new MacFactory();





    }


}
