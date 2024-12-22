package org.acme.core;


import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.acme.client.customer.CustomerForm;
import org.acme.client.customer.model.UserModel;
import org.acme.client.customer.repository.CredentialRepository;
import org.acme.client.customer.entity.Customer;
import org.acme.client.customer.repository.CustomerRepository;

@ApplicationScoped
@Path("/user")
public class ExtensionsResource {






    public JsonObject errorResponse(String message) {
        JsonObject json = new JsonObject();
        json.put("status", 404);
        json.put("message", message + " 다시 시도 해주세요");
        return json;
    }

    public JsonObject errorResponse(JsonObject json) {

        return json;
    }


    public JsonObject successResponse(String message) {
        JsonObject json = new JsonObject();
        json.put("status", 200);
        json.put("message", message + " 요청이 성공 하였습니다");
        return json;
    }


    public Optional<Boolean> authenticate(String input, Optional<Map<String, String>> convert) {


        return Optional.of(convert.stream().map(s -> s.get("password")).anyMatch(password -> password.equals(input)));
    }


    public Optional<Map<String, String>> convert(TypedQuery<Customer> query) {

        Map<String, String> map = new HashMap<>();

        return Optional.of(map);
    }


    @GET
    @Path("/me")
    public String me(@Context SecurityContext securityContext) {
        String ctx = securityContext.getUserPrincipal().getName();

        return Objects.requireNonNullElse(ctx, "NOT ALLOWED");
    }


}


