package org.acme.ext.terran.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.acme.ext.terran.entity.Barracks;
import org.acme.ext.terran.entity.CommandCenter;
import org.acme.ext.terran.model.TerranModel;
import org.springframework.stereotype.Component;

@Component
public class TerranService<T>
{


    //TODO: ENTITY TYPE && DTO IMPLEMENT
    private static TerranDataAccess<?> T;

    private Class<?> enc;

    private final TerranDataAccess<?> build;

    private static Class clazz;


    TerranService(TerranDataAccess<?> build)
    {
        this.build = build;
    }




    static final AtomicInteger at = new AtomicInteger();


    private int idGet()
    {
        return at.incrementAndGet();
    }


    public Object EntityType(String select, int id)
    {
        try {

            clazz = TerranModel.toStr(select).getClazz();
        } catch (RuntimeException e) {

            throw new IllegalArgumentException(e);
        }
        return build.setClazz(clazz).find(id);
    }


    public List<Object> anyList(String select)
    {

        try {
            clazz = TerranModel.toStr(select).getClazz();
        } catch (RuntimeException e) {

            throw new IllegalArgumentException(e);
        }

        return build.setClazz(clazz).listAll();
    }


    public void setEntity(String select)
    {
        clazz = TerranModel.toStr(select).getClazz();

        this.enc = clazz;

    }

    public Object findOfBarracks(int id)
    {
        return  build.find(id, Barracks.class);
    }


    public Object findOfCommand(int id)
    {
        return build.find(id, CommandCenter.class);
    }


    public Object find(int id)
    {

        return build.find(id,clazz);
    }


    public <T> Object findById(int id)
    {
        return build.setClazz(Barracks.class).find(id);
    }


    public void save(Barracks o)
    {
        create();
        o.setId(idGet());
        build.save(o);
    }


    //test mockdata generate
    // @Transactional
    protected String create()
    {

        Barracks      marine = new Barracks();
        Barracks      fire   = new Barracks();
        CommandCenter c      = new CommandCenter();

        marine.setId(idGet());
        marine.setName("Marine");
        fire.setId(idGet());
        fire.setName("Fire");
        c.setId(idGet());
        c.setName("Command");

        build.save(marine);
        build.save(c);
        build.save(fire);

        return "";
    }


}
