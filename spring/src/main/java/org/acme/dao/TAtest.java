package org.acme.dao;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.acme.avro.Unit;
import org.acme.entity.Barracks;
import org.acme.entity.CommandCenter;
import org.springframework.stereotype.Component;

@Component
public class TAtest
{
    public enum Model {
        Barraks(Barracks.class),
        Command(CommandCenter.class);

        private final Class<?> clazz;

        Model(Class<?> clazz) {
            this.clazz = clazz;
        }

        public Class<?> getClazz() {
            return clazz;
        }
    }

    private final TableAccess<Barracks> build;

    private final TableAccess<CommandCenter> cbulid;


    public TAtest(TableAccess<Barracks> build, TableAccess<CommandCenter> cbulid)
    {
        this.build  = build;
        this.cbulid = cbulid;
    }


    static final AtomicInteger at = new AtomicInteger();


    public int idGet()
    {
        return at.incrementAndGet();
    }


    public List<Object> anyList(Model type)
    {

        switch (type) {

            case Barraks:

                return build.setClazz(Barracks.class).listAll();


            case Command:

                return build.setClazz(Barracks.class).listAll();

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
    public String create()
    {

        Barracks      marine = new Barracks();
        Barracks      fire   = new Barracks();
        CommandCenter c      = new CommandCenter();

        marine.setId(idGet());
        fire.setId(idGet());
        c.setId(idGet());

        build.save(marine);
        cbulid.save(c);
        build.save(fire);

        return "";
    }


    @Transactional
    public <T> Object find(int id)
    {

        return cbulid.find(id, CommandCenter.class);
    }


    @Transactional
    public <T> Object findById(int id)
    {

        return build.setClazz(Barracks.class).find(id);
    }


    private Unit deserializeUser(byte[] avroData) throws IOException
    {

        return new Unit();
    }


    private byte[] serializeUser(Unit unit) throws IOException
    {

        return new byte[0];
    }


}
