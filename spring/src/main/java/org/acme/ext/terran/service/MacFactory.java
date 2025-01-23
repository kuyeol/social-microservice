package org.acme.ext.terran.service;

import java.sql.Timestamp;
import java.util.Calendar;
import org.acme.core.domain.NoteBook;
import org.acme.core.service.Factory;
import org.acme.ext.terran.entity.M1Book;
import org.acme.ext.terran.entity.M2Book;
import org.acme.ext.terran.entity.MacBook;

public class MacFactory extends MacBook implements Factory<Mac>
{

    private Mac mac;


    public MacFactory()
    {
    }


    @Override
    protected String setSeri(String s)
    {
        return "";
    }


    MacBook   mm;

    Calendar  c  = Calendar.getInstance();

    Timestamp ts = new Timestamp(System.currentTimeMillis());


    public M2Book newM2()
    {
        M2Book m2 = m2Make("sdf");
        m2.setTs(ts);

        return m2;
    }


    public NoteBook newM1()
    {
        M1Book nem = m1Make("asdf");
        nem.setName("adf");
        nem.setTs(new Timestamp(1l));

        return nem;
    }


    @Override
    public Mac getBrand()
    {
        return null;
    }


    @Override
    public NoteBook<Mac> makeBook()
    {
        MacBook m = makeProduct();
        m.setName("mac");

        return m;
    }


    @Override
    public NoteBook<Mac> ma(NoteBook t)
    {
        return null;
    }


}
