package org.acme;

import org.acme.core.domain.NoteBook;
import org.acme.core.service.Factory;
import org.acme.ext.terran.service.MacFactory;
import org.acme.ext.zerg.service.WinFactory;

public class Main
{

    public static void main(String[] args)
    {
        Factory  factory;
        NoteBook anyNote;

        factory = new WinFactory();
        anyNote = factory.makeBook();
        System.out.println(anyNote);

        factory = new MacFactory();
        MacFactory macFactory = new MacFactory();

        anyNote = macFactory.newM2();
        System.out.println(anyNote);

        anyNote = macFactory.newM1();
        System.out.println(anyNote);

    }


}
