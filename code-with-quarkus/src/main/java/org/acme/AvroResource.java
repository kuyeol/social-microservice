package org.acme;


import io.vertx.core.json.JsonObject;
import jakarta.enterprise.inject.build.compatible.spi.Validation;
import org.acme.avro.repo.User;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
@Path("/avro")
public class AvroResource
{


    @POST
    public Response createUser(User user) {
        try {
            // Avro 데이터를 역직렬화
            //User user = deserializeUser(avroData);

            // 예제: 받은 User 데이터를 출력
            System.out.println("Received User:");
            System.out.println("ID: " + user.getId());
            System.out.println("Name: " + user.getName());
            System.out.println("Email: " + user.getEmail());
            JsonObject json = new JsonObject();
            json.put("ID", user.getId());
            json.put("Name", user.getName());
            json.put("Email", user.getEmail());



            // 응답으로 동일한 User 객체를 직렬화하여 반환
            byte[] responseData = serializeUser(user);

            // Response 객체 생성 (헤더와 함께)
            return Response.ok(json)
                           .header("Content-Type", "application/avro")
                           .build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    // GET 요청: 샘플 User 객체를 생성하여 직렬화된 Avro 데이터 반환
    @GET
    @Produces("application/avro")
    public Response getUser() {
        try {
            // 샘플 User 객체 생성
            User user = new User();
            user.setId(1);
            user.setName("John Doe");
            user.setEmail("john.doe@example.com");

            // Avro 데이터로 직렬화
            byte[] responseData = serializeUser(user);

            // Response 객체 생성 (헤더와 함께)
            return Response.ok(responseData)
                           .header("Content-Type", "application/avro")
                           .build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

        // Avro 데이터 역직렬화 메서드 (User 객체로 변환)
        private User deserializeUser(byte[] avroData) throws IOException {
            // Avro 역직렬화 로직 구현 (여기서는 예시로 단순화)
            // 실제 구현에서는 Avro 스키마를 사용하여 데이터 변환을 해야 합니다.
            return new User(); // 단순화된 예시
        }

        // User 객체를 Avro 데이터로 직렬화하는 메서드
        private byte[] serializeUser(User user) throws IOException {
            // Avro 직렬화 로직 구현 (여기서는 예시로 단순화)
            // 실제 구현에서는 Avro 스키마를 사용하여 데이터를 직렬화해야 합니다.
            return new byte[0]; // 단순화된 예시
        }
}
