package org.acme.ext.terran.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.acme.EntityID;
import org.acme.ext.terran.entity.Barracks;
import org.acme.ext.terran.entity.CommandCenter;
import org.acme.ext.terran.model.TerranModel;
import org.springframework.stereotype.Component;

@Component
public class TerranService
{

    private final TerranDataAccess<Barracks> build;

    private final TerranDataAccess<CommandCenter> cbuild;

    private static Class clazz;


    TerranService(TerranDataAccess<Barracks> build, TerranDataAccess<CommandCenter> cbulid)
    {
        this.build  = build;
        this.cbuild = cbulid;
    }


    static final AtomicInteger at = new AtomicInteger();


    public int idGet()
    {
        return at.incrementAndGet();
    }


    // @Transactional(Transactional.TxType.REQUIRED)
    public List<Object> anyList(String select)
    {

        try {
            clazz = TerranModel.toStr(select).getClazz();
        } catch (RuntimeException e) {

            throw new IllegalArgumentException(e);
        }

        return build.setClazz(clazz).listAll();
    }


    // @Transactional
    public void save(Barracks o)
    {
        create();
        o.setId(idGet());
        build.save(o);
    }

    private Class<?> enc;

    public Object findOfBarracks(int id)
    {
        return build.find(id, enc);
    }

    public Object findOfCommand(int id)
    {
        return cbuild.find(id, enc);
    }

    public void setEntity(Class<?> clazz)
    {
        this.enc = clazz;
    }


    public Object find(int id)
    {

        return cbuild.find(id, CommandCenter.class);
    }


    // @Transactional
    public <T> Object findById(int id)
    {
        return build.setClazz(Barracks.class).find(id);
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
        cbuild.save(c);
        build.save(fire);

        return "";
    }


}
