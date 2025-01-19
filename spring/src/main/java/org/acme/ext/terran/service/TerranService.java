package org.acme.ext.terran.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
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

    private TerranModel Model;


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


    public List<Object> anyList(String select)
    {
        switch (select) {

            case "barracks":
                clazz = Model.BARRACKS.getClazz();
                return build.setClazz(clazz).listAll();

            case "command":
                clazz = Model.COMMAND.getClazz();
                return cbuild.setClazz(clazz).listAll();

            default:
                return build.listAll();
        }
    }


    @Transactional
    public void save(Barracks o)
    {
        create();
        o.setId(idGet());
        build.save(o);
    }


    @Transactional
    public Object find(int id)
    {
        return cbuild.find(id, CommandCenter.class);
    }


    @Transactional
    public <T> Object findById(int id)
    {
        return build.setClazz(Barracks.class).find(id);
    }


    //test mockdata generate
    @Transactional
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
