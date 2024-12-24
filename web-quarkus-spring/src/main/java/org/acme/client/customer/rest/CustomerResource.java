package org.acme.client.customer.rest;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import java.net.URI;

import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;


import org.acme.client.customer.entity.Customer;
import org.acme.client.customer.model.CustomerForm;
import org.acme.client.customer.model.UserModel;
import org.acme.client.customer.model.UserRepresentation;
import org.acme.client.customer.repository.CustomerRepository;
import org.acme.client.customer.service.AccountService;
import org.acme.core.model.Model;


@ApplicationScoped
@Path("/customers")
public class CustomerResource {

    private AccountService accountService;


    public CustomerResource(AccountService accountService, CustomerRepository customerRepository) {
        this.accountService = accountService;
    }


    @GET
    @Path("home")
    @Produces(MediaType.TEXT_HTML)
    public String Home() {


        return "<h1>" + "Home" + "</h1>";
    }

    


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //@Path("{name}")
    public Response hello11(@QueryParam("name") String name) {
        UserRepresentation rep = accountService.findUser(name);

        if (rep.getCustomerName() == null) {

            String msg = "유저를 찾을수 없습니다";
            //리디렉트 페이지
            //return Response.temporaryRedirect(URI.create("msg")).build();
            return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();

        }

        return Response.ok(rep).build();
    }



    @POST
    @Path("RETURNeNTITIY")
    public Response returnEntity(){




        return Response.ok().build();
    }






    @Path("register")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(UserRepresentation user) {

        Customer customer = new Customer();
        customer.setCustomerName(user.getCustomerName());
        customer.setEmail(user.getEmail());


        accountService.add(customer);

        return Response.ok(user).status(200).build();
    }


    //@Path("register2")
    //@POST
    //@Produces(MediaType.APPLICATION_JSON)
    //public Response addCustomer(CustomerForm form) {
    //
    //    if (accountService.findId(form.getUsername()) != null) {
    //
    //        String msg = "이미 등록 된 유저 입니다";
    //
    //        return Response.status(Response.Status.NOT_ACCEPTABLE).entity(msg).build();
    //
    //    } else if (form.getUsername().isEmpty() && form.getPassword().isEmpty()) {
    //
    //        return Response.serverError().entity(form.getMessage()).build();
    //
    //    } else {
    //
    //        Customer customer = new Customer();
    //        customer.setCustomerName(form.getUsername());
    //        customer.setEmail(form.getUsername());
    //        accountService.add(customer);
    //        return Response.ok(form.getUsername() + " 님의 회원등록 ").build();
    //    }
    //
    //}

    public Response finduser(@QueryParam("username") String username) {
        UserModel userModel = new UserRepresentation();
        userModel.setCustomerName(username);


        return Response.ok().build();

    }

    public Customer registerCustomer(CustomerForm form) {

        Customer customer = new Customer();

        customer.setCustomerName(form.getUsername());
        customer.setEmail(form.getUsername());


        return null;
    }


    @GET
    @Path("{error}")
    @Produces(MediaType.TEXT_HTML)
    public String errorResponse(@PathParam("error") String error, @QueryParam("message") String msg) {
        String errorMsg = msg != null ? msg : "알 수 없는 오류가 발생했습니다.";


        return "<!DOCTYPE html>" +
            "<html lang=\"ko\">" +
            "<head>" +
            "<meta charset=\"UTF-8\">" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
            "<title>Error Page</title>" +
            "<style>" +
            "body {" +
            "font-family: 'Arial', sans-serif;" +
            "display: flex;" +
            "justify-content: center;" +
            "align-items: center;" +
            "height: 100vh;" +
            "margin: 0;" +
            "background-color: #f5f5f5;" +
            "}" +
            ".error-container {" +
            "text-align: center;" +
            "padding: 2rem;" +
            "background-color: white;" +
            "border-radius: 8px;" +
            "box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);" +
            "max-width: 80%;" +
            "}" +
            "h1 {" +
            "color: #e74c3c;" +
            "margin-bottom: 1rem;" +
            "}" +
            ".error-message {" +
            "color: #2c3e50;" +
            "margin-bottom: 1.5rem;" +
            "}" +
            ".back-button {" +
            "display: inline-block;" +
            "padding: 10px 20px;" +
            "background-color: #3498db;" +
            "color: white;" +
            "text-decoration: none;" +
            "border-radius: 4px;" +
            "transition: background-color 0.3s;" +
            "}" +
            ".back-button:hover {" +
            "background-color: #2980b9;" +
            "}" +
            "</style>" +
            "</head>" +
            "<body>" +
            "<div class=\"error-container\">" +
            "<h1>요청 실패</h1>" +
            "<div class=\"error-message\">" +
            errorMsg +
            "</div>" +
            "<div>" +
            "<a href=\"javascript:history.back()\" class=\"back-button\">이전 페이지</a>" +
            "</br>" +
            "</br>" +

            "<a href=\"http://localhost:8080/customers/home\" class=\"back-button\">메인 페이지</a>" +
            "</div>" +
            "</div>" +
            "</body>" +
            "</html>";


    }

    private Response createErrorRedirect(String errorMessage) {
        URI redirectUri = UriBuilder.fromResource(CustomerResource.class)
            .path("error")
            .queryParam("message", errorMessage)
            .build();

        return Response.seeOther(redirectUri).build();
    }


}
