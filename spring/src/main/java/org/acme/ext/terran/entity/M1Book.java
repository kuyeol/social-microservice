package org.acme.ext.terran.entity;

import java.sql.Timestamp;
import org.acme.core.domain.NoteBook;

public class M1Book extends MacBook
{

    public Timestamp getTs()
    {
        return ts;
    }


    public void setTs(Timestamp ts)
    {
        this.ts = ts;
    }


    private Timestamp ts;


    protected M1Book()
    {

    }


    @Override
    protected MacBook makeProduct()
    {
        M1Book m1 = makeProduct().m1Make("sss");
        return m1;
    }


    @Override
    protected NoteBook orderBook1()
    {
        return null;
    }


    @Override
    protected String setSeri(String s)
    {
        return "";
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName()+"\t{" + "name='" + name +getClass().getSimpleName()+ '\'' + '}';
    }

}
