package org.acme.core;


import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.acme.client.customer.entity.Customer;

@ApplicationScoped
@Path("/user")
public class ExtensionsResource
{


    public JsonObject errorResponse(String message)
    {
        JsonObject json = new JsonObject();
        json.put("status", 404);
        json.put("message", message + " 다시 시도 해주세요");
        return json;
    }

    public JsonObject errorResponse(JsonObject json)
    {

        return json;
    }


    public JsonObject successResponse(String message)
    {
        JsonObject json = new JsonObject();
        json.put("status", 200);
        json.put("message", message + " 요청이 성공 하였습니다");
        return json;
    }


    public Optional<Boolean> authenticate(String input, Optional<Map<String, String>> convert)
    {


        return Optional.of(convert.stream()
                                  .map(s -> s.get("password"))
                                  .anyMatch(password -> password.equals(input)));
    }


    public Optional<Map<String, String>> convert(TypedQuery<Customer> query)
    {

        Map<String, String> map = new HashMap<>();

        return Optional.of(map);
    }


    @GET
    @Path("/me")
    public String me(@Context SecurityContext securityContext)
    {
        String ctx = securityContext.getUserPrincipal()
                                    .getName();

        return Objects.requireNonNullElse(ctx, "NOT ALLOWED");
    }


}


