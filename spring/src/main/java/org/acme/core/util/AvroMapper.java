package org.acme.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.avro.Schema;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecord;


public class AvroMapper<T extends SpecificRecord>
{

    private static Schema schema;

    private static Encoder encoder;

    private static Decoder decoder;

    private final DatumReader<T> reader;

    private static DatumWriter<SpecificRecord> writer;

    private static ObjectMapper mapper;



    //public AvroMapper(SpecificRecord t)
    //{
    //    schema = t.getSchema();
    //    reader = new SpecificDatumReader<>(schema);
    //    writer = new SpecificDatumWriter<>(schema);
    //    mapper = new ObjectMapper();
    //}

    public AvroMapper(T t)
    {
        schema = t.getSchema();
        reader = new SpecificDatumReader<>(schema);
        writer = new SpecificDatumWriter<>(schema);
        mapper = new ObjectMapper();
    }


    public String toJson(T t)
    {

        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {

            encoder = EncoderFactory.get().jsonEncoder(schema, stream);
            writer.write(t, encoder);
            encoder.flush();

            return stream.toString();
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }



    public T fromJson(String json)
    {
        try {
            decoder = DecoderFactory.get().jsonDecoder(schema, json);

            return reader.read(null, decoder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public SpecificRecord fromObject(Object object)
    {

        String json = "null";

        SpecificRecord record;

        try {

            json    = mapper.writeValueAsString(object);
            decoder = DecoderFactory.get().jsonDecoder(schema, json);
            record  = reader.read(null, decoder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return record;
    }


}
