package org.acme.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.acme.EntityID;
import org.acme.avro.Unit;
import org.acme.dao.TAtest;
import org.acme.entity.Barracks;
import org.acme.entity.CommandCenter;
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


    @PostMapping(value = "/ping", produces = "application/json")
    public String ping(@RequestBody Barracks u)
    {

        unit.save(u);

        return "{ \"ping\": \"pong\" }";
    }


    @GetMapping(value = "list/{type}")
    public ResponseEntity list(@PathVariable("type") String type)
    { 
        Optional<List<Object>> op = Optional.empty();
        if (type.equals("barraks")) {
            op = Optional.ofNullable(unit.anyList(TAtest.Model.Barraks));
            return ResponseEntity.status(HttpStatus.CREATED).body(op);

        } else if (type.equals("command")) {
           op = Optional.ofNullable(unit.anyList(TAtest.Model.Command));
            return ResponseEntity.status(HttpStatus.CREATED).body(op);

        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(op);
        }
    }


    @GetMapping(value = "/find/{id}", produces = "application/json")
    public ResponseEntity find(@PathVariable("id") int id)
    {
        EntityID rep      = new CommandCenter();
        Barracks barracks = new Barracks();

        try {
            //Object out = unit.find(id);
            rep = (EntityID) unit.find(id);
        } catch (Exception e) {

            return ResponseEntity.status(200).location(URI.create("ping")).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(rep);
    }


    @GetMapping(value = "/findID/{id}", produces = "application/json")
    public ResponseEntity findD(@PathVariable("id") int id)
    {
        EntityID rep = null;

        try {
            rep = (EntityID) unit.findById(id);

            if (rep == null) {
                Barracks barracks = new Barracks();
                barracks.setId(404);
                barracks.setName("Null Object");
                return ResponseEntity.status(200).body(barracks);
            } else {
                return ResponseEntity.status(HttpStatus.CREATED).body(rep);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


    private final ObjectMapper objectMapper = new ObjectMapper();


    @PostMapping(value = "/toavro", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> toavro(@RequestBody Unit u)
    {
        DatumWriter<Unit>     writer      = new SpecificDatumWriter<>(Unit.class);
        byte[]                data        = new byte[0];
        ByteArrayOutputStream stream      = new ByteArrayOutputStream();
        Encoder               jsonEncoder = null;
        GenericRecord         avroRecord  = null;
        String                conver      = "";
        try {
            jsonEncoder = EncoderFactory.get().jsonEncoder(Unit.getClassSchema(), stream);
            writer.write(u, jsonEncoder);
            jsonEncoder.flush();
            data = stream.toByteArray();
            stream.close();

            Schema schema = inferSchema(u);

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
