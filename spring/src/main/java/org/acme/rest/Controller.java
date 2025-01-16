package org.acme.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import org.acme.avro.Unit;
import org.acme.dao.TAtest;
import org.acme.entity.Barracks;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.specific.SpecificDatumWriter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller
{

    private final TAtest unit;


    public Controller(TAtest unit)
    {
        this.unit = unit;
    }


    @GetMapping(value = "/ping", produces = "application/json")
    public String ping()
    {
        unit.create("DDDD");
        Unit II = new Unit();

        String UU = II.getName();

        return "{ \"ping\": \"pong\" }" + UU;
    }


    @GetMapping(value = "/find/{id}")
    public String find(@PathVariable("id") int id)
    {

        return unit.find(id);
    }


    private final ObjectMapper objectMapper = new ObjectMapper();


    @PostMapping(value = "/toavro", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> toavro(@RequestBody Unit u)
    {
        DatumWriter<Unit>     writer      = new SpecificDatumWriter<>(Unit.class);
        byte[]                data        = new byte[0];
        ByteArrayOutputStream stream      = new ByteArrayOutputStream();
        Encoder               jsonEncoder = null;
        GenericRecord avroRecord = null;
        String conver="";
        try {
            jsonEncoder = EncoderFactory.get().jsonEncoder(Unit.getClassSchema(), stream);
            writer.write(u, jsonEncoder);
            jsonEncoder.flush();
            data = stream.toByteArray();
            stream.close();

            Schema schema  = inferSchema(u);


          conver = convertObjectToJson(u, schema);

        } catch (IOException e) {
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(conver);
    }


    @PostMapping(value = "/avro", consumes = "application/json", produces = "application/json")
    public ResponseEntity avro(@RequestBody Unit u) throws IOException
    {

        Unit u1 = new Unit();


        Barracks br = new Barracks();
        br.setName(u.getName());
        br.setAge(u.getAge());

        Schema schema  = inferSchema(u);
        Schema schema1 = inferSchema(br);

        ByteBuffer bytes = u.toByteBuffer();

        System.out.println(bytes);

        GenericRecord avroRecord  = new GenericData.Record(schema);
        GenericRecord avroRecordB = new GenericData.Record(schema1);

        avroRecord.put("name", u.getName());
        avroRecord.put("age", u.getAge());
        SpecificData d = u.getSpecificData();
        avroRecordB.put("name", u.getName());
        avroRecordB.put("age", u.getAge());
        System.out.println(d);
        System.out.println(avroRecord);
        System.out.println(avroRecordB);
        String conver = convertObjectToJson(br, schema1);

        return ResponseEntity.status(HttpStatus.CREATED).body(conver);
    }


    public Schema inferSchema(Unit p)
    {
        return ReflectData.get().getSchema(p.getClass());
    }


    public Schema inferSchema(Barracks p)
    {
        return ReflectData.get().getSchema(p.getClass());
    }


    public String convertObjectToJson(Barracks p, Schema schema)
    {
        try {
            ByteArrayOutputStream             outputStream  = new ByteArrayOutputStream();
            GenericDatumWriter<GenericRecord> datumWriter   = new GenericDatumWriter<>(schema);
            GenericRecord                     genericRecord = new GenericData.Record(schema);
            genericRecord.put("name", p.getName());
            genericRecord.put("age", p.getAge());
            genericRecord.put("id", p.getId());
            Encoder encoder = EncoderFactory.get().jsonEncoder(schema, outputStream);
            datumWriter.write(genericRecord, encoder);
            encoder.flush();
            outputStream.close();
            return outputStream.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public String convertObjectToJson(Unit p, Schema schema)
    {
        try {
            ByteArrayOutputStream             outputStream  = new ByteArrayOutputStream();
            GenericDatumWriter<GenericRecord> datumWriter   = new GenericDatumWriter<>(schema);
            GenericRecord                     genericRecord = new GenericData.Record(schema);
            genericRecord.put("name", p.getName());
            genericRecord.put("age", p.getAge());
            Encoder encoder = EncoderFactory.get().jsonEncoder(schema, outputStream);
            datumWriter.write(genericRecord, encoder);
            encoder.flush();
            outputStream.close();
            return outputStream.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping(value = "/hi", produces = "application/json")
    public String hi() throws SQLException
    {
        return "{ \"dddddddddddd\": \"12ddfdf\" , \"namessss\": \"JohDDDn\" }";
    }


}
