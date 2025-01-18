package org.acme.ext.terran.model;

import org.acme.ext.terran.entity.Barracks;
import org.acme.ext.terran.entity.CommandCenter;

public enum TerranModel
{
    BARRACKS(Barracks.class), COMMAND(CommandCenter.class);

    private final Class<?> clazz;


    TerranModel(Class<?> clazz)
    {
        this.clazz = clazz;
    }


    public Class<?> getClazz()
    {
        return clazz;
    }
}