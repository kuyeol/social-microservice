package org.acme.ext.terran.entity;

import java.sql.Timestamp;
import org.acme.core.domain.NoteBook;

public class M2Book extends MacBook
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
    protected M2Book()
    {

    }





    @Override
    protected M2Book makeProduct()
    {

        return new M2Book();
    }


    @Override
    protected M2Book orderBook1()
    {
        return publicM2();
    }


    public M2Book publicM2()
    {M2Book M2= new M2Book();
        M2.setSeri("public");
        M2.setName("m2m2m2m");
        return M2;
    }

    @Override
    protected String setSeri(String s)
    {
        return "";
    }
    @Override
    public String toString()
    {
        return getClass().getSimpleName()+"\t{" + "name='" + name +", Time :"+ ts+ '\'' + '}';
    }


}
