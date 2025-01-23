package org.acme.ext.zerg.service;

import org.acme.core.domain.NoteBook;
import org.acme.ext.zerg.model.Windows;

public class WinBook extends NoteBook<Windows>
{

    protected String name;

    protected String version;

    protected String window;


    protected WinBook()
    {
        super();
    }


    @Override
    public String toString()
    {
        return "WinBook\t{" + "name='" + name + '\'' + ", version='" + version + '\'' + ", window" +
                                                 "='" +
               window + '\'' + '}';
    }


    @Override
    public String getName()
    {
        return name;
    }


    protected String setWindow(String a)
    {
        this.window = a;
        return window;
    }


    @Override
    protected WinBook makeProduct()
    {
        return new WinBook();
    }


    @Override
    protected WinBook orderBook1()
    {
        return new WinBook();
    }


    @Override
    public void setName(String name)
    {
        this.name = name;
    }


    @Override
    protected String setSeri(String s)
    {
        return "";
    }


    protected String getVersion()
    {
        return version;
    }


    public void setVersion(String version)
    {
        this.version = version;
    }


}
