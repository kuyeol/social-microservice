package org.acme.util;

import java.time.Instant;
import org.acme.avro.Unit;
import org.acme.entity.Barracks;
import org.apache.avro.specific.SpecificRecord;

public class Main
{

    private static final long startTime = System.nanoTime();

    static Instant start = Instant.now();


    public static void main(String[] args)
    {
        System.out.println("hello");

        Barracks b = new Barracks();
        b.setName("s");
        b.setId(1);
        b.setAge(12);

        Unit unit = new Unit();

        System.out.println(unit);

        System.out.println("Copy :" + "\n origin :" + unit);
        MoToEn(b, unit);

        SpecificRecord unit1 = MoToEn(b);
        Object         OOO   = MoToEn(b, Unit.class);
        System.out.println("\t OOOO " + OOO);
        System.out.println("\t hhhh " + unit1);
        System.out.println("\t hhhh " + MoToEn(b, unit));
    }


    private static Unit EnToMo(Barracks b)
    {

        return null;
    }


    private static SpecificRecord MoToEn(Object b)
    {

        Unit             u          = new Unit();
        AvroMapper<Unit> avroMapper = new AvroMapper<>(u);

        return avroMapper.fromObject(b);
    }


    private static Object MoToEn(Object b, Unit sf)
    {
        Unit             u          = new Unit();
        AvroMapper<Unit> avroMapper = new AvroMapper<>(u);

        b = avroMapper.fromObject(b);

        return b;
    }


    private static <sf extends SpecificRecord> Object MoToEn(Object b, Class<?> sf)
    {
        Unit           u          = new Unit();
        AvroMapper<sf> avroMapper = new AvroMapper<>(u);

        b = avroMapper.fromObject(b);

        return b;
    }


    private static Object avroRest(Unit sf, Object obj)
    {
        Unit             u          = new Unit();
        AvroMapper<Unit> avroMapper = new AvroMapper<>(u);

        String js = avroMapper.toJson(sf);
        obj = avroMapper.fromJson(js);

        return obj;
    }


}
