package object;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.acme.avro.repo.User;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;



public class Main
{

    public static void main(String[] args)
    {
        try {

            User user = new User();
            user.setId(1);
            user.setName("John Doe");
            user.setEmail("john.doe@example.com");

            // 2. 직렬화
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            DatumWriter<User>     writer       = new SpecificDatumWriter<>(User.class);
            BinaryEncoder         encoder      = EncoderFactory.get()
                                                               .binaryEncoder(outputStream, null);
            writer.write(user, encoder);
            encoder.flush();
            outputStream.close();

            byte[] serializedData = outputStream.toByteArray();
            System.out.println("직렬화된 데이터: " + serializedData);

            // 3. 역직렬화
            ByteArrayInputStream inputStream = new ByteArrayInputStream(serializedData);
            DatumReader<User>    reader      = new SpecificDatumReader<>(User.class);
            BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(inputStream, null);
            User deserializedUser = reader.read(null, decoder);

            System.out.println("역직렬화된 객체:");
            System.out.println("ID: " + deserializedUser.getId());
            System.out.println("Name: " + deserializedUser.getName());
            System.out.println("Email: " + deserializedUser.getEmail());
        } catch (

            IOException e) {
            e.printStackTrace();
        }
    }


}
