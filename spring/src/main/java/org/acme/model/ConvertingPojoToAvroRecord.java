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

/**
 * This class provides methods for converting POJOs (Plain Old Java Objects) to Avro records.  It offers three different approaches:
 * <ul>
 *   <li>Straightforward mapping: Manually maps POJO fields to Avro record fields.</li>
 *   <li>Reflection-based mapping: Uses reflection to automatically map POJO fields to Avro record fields, including superclass fields.</li>
 *   <li>ReflectDatumWriter-based mapping: Leverages the ReflectDatumWriter for a more robust and efficient conversion.</li>
 * </ul>
 * Each method demonstrates a different technique for achieving the same goal, allowing for flexibility based on specific needs and performance considerations.
 */
public class ConvertingPojoToAvroRecord {

    /**
     * Maps a Barracks POJO to an Avro record using a straightforward approach.
     * @param pojo The Barracks POJO to convert.
     * @return The Avro record representing the POJO.
     */
    public static GenericData.Record mapPojoToRecordStraightForward(Barracks pojo) {
        Schema schema = ReflectData.get().getSchema(pojo.getClass());
        GenericData.Record avroRecord = new GenericData.Record(schema);

        avroRecord.put("id", pojo.getId());
        avroRecord.put("name", pojo.getName());


        return avroRecord;
    }

    /**
     * Maps a Barracks POJO to an Avro record using reflection. This method handles fields from superclasses.
     * @param pojo The Barracks POJO to convert.
     * @return The Avro record representing the POJO.
     * @throws IllegalAccessException If accessing a field throws an exception.
     */
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

    /**
     * Maps a Object to an Avro record using ReflectDatumWriter.
     * This method is generally more efficient and robust.
     * @param pojo The Object to convert.
     * @return The Avro record representing the Object.
     * @throws IOException If an I/O error occurs during the conversion.
     */
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
