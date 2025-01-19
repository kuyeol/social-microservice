package org.acme.ext.zerg.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.acme.EntityID;
import org.acme.avro.Unit;
import org.acme.core.util.RequestTypeBindSupport;
import org.acme.ext.terran.entity.Barracks;
import org.acme.ext.terran.model.TerranModel;
import org.acme.ext.terran.service.TerranService;
import org.acme.ext.zerg.service.ZergService;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zerg")
 class ZergController
{

    private static final RequestTypeBindSupport<TerranModel> BIND_SUPPORT = new RequestTypeBindSupport<>(
        TerranModel.class);

    private final TerranService unit;

    private final ZergService zergService;


    public ZergController(TerranService unit, ZergService zergService)
    {
        this.unit = unit;
        this.zergService = zergService;
    }


    @PostMapping(value = "/ping", produces = "application/json")
    public String ping(@RequestBody Barracks u)
    {
        unit.save(u);

        return "{ \"ping\": \"pong\" }";
    }


    @GetMapping(value = "/list/{page}")
    public ResponseEntity list(@RequestParam("type") TerranModel type,
                               @PathVariable("page") int page)
    {
        Optional<List<Object>> op = Optional.empty();


        try {
            op = Optional.ofNullable(zergService.anyList(type.toString().toLowerCase()));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(op);
    }


    @GetMapping(value = "/findBarrak/{id}", produces = "application/json")
    public ResponseEntity findBarrak(@PathVariable("id") int id)
    {
        EntityID rep;
        // Barracks barracks = new Barracks();

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

        unit.findById(u.getAge());

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


    @GetMapping(value = "/avro/{id}", produces = "application/json")
    public ResponseEntity avro(@PathVariable("id") int u)
    {

        Barracks rep = null;

        Unit avro = new Unit();

        byte[] bytes = null;

        Schema schema = null;

        try {
            rep = (Barracks) unit.findById(u);

            if (rep == null) {
                Barracks barracks = new Barracks();
                barracks.setId(404);
                barracks.setName("Null Object");
                return ResponseEntity.status(200).body(barracks);
            } else {

                //schema = inferSchema(avro);
                // String rc = convertObjectToJson(rep, schema);
                avro = convertToAvro(rep);
                //bytes = serializeToBytes(avro);
                //avro  = deserializeFromBytes(bytes);
                return ResponseEntity.status(HttpStatus.CREATED).body(avro);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


    public Unit deserializeFromBytes(byte[] data) throws Exception
    {
        DatumReader<Unit> datumReader = new SpecificDatumReader<>(Unit.class);
        Decoder           decoder     = DecoderFactory.get().binaryDecoder(data, null);

        return datumReader.read(null, decoder);
    }


    public Unit convertToAvro(Barracks userEntity)
    {
        Unit user = new Unit();
        user.put("name", "userEntity.getName()");
        user.put("age", userEntity.getAge());
        //user.put("id", "userEntity.getName()");

        return user;
    }


    public byte[] serializeToBytes(Unit avroUser) throws Exception
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DatumWriter<Unit>     datumWriter  = new SpecificDatumWriter<>(Unit.class);
        Encoder               encoder      = EncoderFactory.get().binaryEncoder(outputStream, null);
        datumWriter.write(avroUser, encoder);
        encoder.flush();
        outputStream.close();
        return outputStream.toByteArray();
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


    @InitBinder
    private static void initBinder(WebDataBinder binder)
    {
        binder.registerCustomEditor(TerranModel.class, BIND_SUPPORT);
    }


}
