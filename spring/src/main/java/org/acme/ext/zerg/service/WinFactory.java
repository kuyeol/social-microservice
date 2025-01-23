package org.acme.ext.zerg.service;

import org.acme.core.domain.NoteBook;
import org.acme.core.service.Factory;
import org.acme.ext.zerg.model.Windows;

public class WinFactory extends WinBook implements Factory<Windows>
{

    private Windows windows;


    public WinFactory()
    {
        getBrand();
    }


    @Override
    public Windows getBrand()
    {
        return windows;
    }



    public WinBook macBookNew(String s, String t)
    {

        WinBook w = makeProduct();
        w.setVersion(s);
        w.setName(t);

        return w;
    }


    @Override
    public NoteBook<Windows> makeBook()
    {
        WinBook w = makeProduct();
        w.setName("dfsd");
        w.setVersion("ll");
        w.setSeri("시리얼");
        w.setWindow("win");
        return w;
    }


    @Override
    public NoteBook<Windows> ma(NoteBook t)
    {
        return null;
    }


    private WinBook winBook;


}
