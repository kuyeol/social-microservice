package org.acme.ext.terran.entity;

import org.acme.core.domain.Maker;
import org.acme.core.domain.NoteBook;
import org.acme.ext.terran.service.Mac;

public class MacBook extends NoteBook<Mac> implements Maker
{

    protected MacBook mab;

    private String serial;


    public MacBook MAB()
    {
        mab = new MacBook();
        return mab;
    }


    protected MacBook()
    {
        super();
    }


    @Override
    public NoteBook createMaker()
    {
        return makeProduct();
    }


    @Override
    public String toString()
    {
        return "MacBook\t{" + "serial='" + serial + '\'' + ", m=" + m + ", name='" + name + '\'' +
               '}';
    }


    @Override
    protected MacBook makeProduct()
    {
        return new MacBook();
    }


    @Override
    protected NoteBook orderBook1()
    {
        return new MacBook();
    }


    protected M2Book m2Make(String n)
    {
        M2Book m2 = new M2Book();
        m2.setSeri(n);
        return m2;
    }


    protected M1Book m1Make(String s)
    {
        MacBook m1 = new MacBook();
        m1.serial(s);

        return new M1Book();
    }


    protected String serial(String s)
    {

        return this.serial = s;
    }


    protected String setSeri(String s)
    {
        return serial;
    }


}
