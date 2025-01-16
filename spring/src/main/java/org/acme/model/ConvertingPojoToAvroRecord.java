package org.acme.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

import org.acme.entity.Barracks;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;

import org.apache.avro.io.*;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumWriter;

public class ConvertingPojoToAvroRecord {

    public static GenericData.Record mapPojoToRecordStraightForward(Barracks pojo){
        Schema schema = ReflectData.get().getSchema(pojo.getClass());
        GenericData.Record avroRecord = new GenericData.Record(schema);

        avroRecord.put("id", pojo.getId());
        avroRecord.put("name", pojo.getName());


        return avroRecord;
    }

    public static GenericData.Record mapPojoToRecordReflection(Barracks pojo) throws IllegalAccessException {
        Class<?> pojoClass = pojo.getClass();
        Schema schema = ReflectData.get().getSchema(pojoClass);
        GenericData.Record avroRecord = new GenericData.Record(schema);

        for (Field field : pojoClass.getDeclaredFields()) {
            field.setAccessible(true);
            avroRecord.put(field.getName(), field.get(pojo));
        }

        // Handle superclass fields
        Class<?> superClass = pojoClass.getSuperclass();
        while (superClass != null && superClass != Object.class) {
            for (Field field : superClass.getDeclaredFields()) {
                field.setAccessible(true);
                avroRecord.put(field.getName(), field.get(pojo));
            }
            superClass = superClass.getSuperclass();
        }

        return avroRecord;
    }

    public static GenericData.Record mapPojoToRecordReflectDatumWriter(Object pojo) throws IOException {

        Schema schema = ReflectData.get().getSchema(pojo.getClass());
        ReflectDatumWriter<Object> writer = new ReflectDatumWriter<>(schema);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);

        writer.write(pojo, encoder);
        encoder.flush();

        BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(out.toByteArray(), null);
        GenericDatumReader<GenericData.Record> reader = new GenericDatumReader<>(schema);

        return reader.read(null, decoder);
    }
}
