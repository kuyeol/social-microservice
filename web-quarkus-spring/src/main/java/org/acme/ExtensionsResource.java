package org.acme;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.vertx.core.json.JsonObject;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.acme.repository.CustomerRepository;
import org.acme.service.customer.UserForm;
import org.acme.service.customer.entity.User;


@ApplicationScoped
@Path("/user")
public class ExtensionsResource {


    @Inject
    CustomerRepository customerRepository;


    @POST
    @Path("/hello")
    @Produces(MediaType.APPLICATION_JSON)
    public Response makeUser(UserForm form) {


        if (!form.getStatus()) {

            return Response.serverError().entity(errorResponse(form.getMessage())).build();

        } else if (!customerRepository.findByName(form.getUsername()).isEmpty()) {

            String msg = "이미 등록 된 유저 입니다";

            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(errorResponse(msg)).build();

        } else {

            registerUser(form);

            return Response.ok(successResponse(form.getUsername() + " 님의 회원등록 ")).build();
        }


    }




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


    private void registerUser(UserForm form) {

        User user = new User();
        user.setUsername(form.getUsername());
        user.setPassword(form.getPassword());
        user.setEmail(form.getUsername()+"dd");

        customerRepository.add(user);
    }



    public Optional<Boolean> authenticate(String input, Optional<Map<String, String>> convert) {


        return Optional.of(convert.stream().map(s -> s.get("password")).anyMatch(password -> password.equals(input)));
    }


    public Optional<Map<String, String>> convert(TypedQuery<User> query) {

        Map<String, String> map = new HashMap<>();

        map.put("password", query.getSingleResultOrNull().getPassword());

        return Optional.of(map);
    }


    @GET
    @Path("/me")
    public String me(@Context SecurityContext securityContext) {
        String ctx = securityContext.getUserPrincipal().getName();

        return Objects.requireNonNullElse(ctx, "NOT ALLOWED");
    }







}


