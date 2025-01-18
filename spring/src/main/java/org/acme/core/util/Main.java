package org.acme.core.util;

import org.acme.avro.Unit;
import org.apache.avro.specific.SpecificRecord;

public class Main
{

    public static void main(String[] args)
    {

    }



    private static SpecificRecord MoToEn(Object b)
    {

        Unit             u          = new Unit();
        AvroMapper<Unit> avroMapper = new AvroMapper<>(u);

        return avroMapper.fromObject(b);
    }


    private static <T extends Object> Object MoToEn(Object b, Unit sf)
    {
        Unit             u          = new Unit();
        AvroMapper<Unit> avroMapper = new AvroMapper<>(u);

        b = avroMapper.fromObject(b);

        return b;
    }


    private static <sf extends SpecificRecord> Object MoToEn(Object b, Class<?> sf)
    {
        Unit           u          = new Unit();


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
