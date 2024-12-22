package org.acme.client.customer.rest;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import org.acme.client.customer.CustomerForm;
import org.acme.client.customer.entity.Customer;
import org.acme.client.customer.model.UserModel;
import org.acme.client.customer.model.UserRepresentation;
import org.acme.client.customer.repository.CustomerRepository;
import org.acme.client.customer.service.AccountService;


@ApplicationScoped
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    private final CustomerRepository customerRepository;
    private AccountService accountService;


    public CustomerResource(AccountService accountService, CustomerRepository customerRepository) {
        this.accountService = accountService;
        this.customerRepository = customerRepository;
    }

    @jakarta.inject.Inject
    public CustomerResource(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GET
    public String hello() {
        return "hello";
    }

    


    @Path("register")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void register(Customer customer) {


        accountService.add(customer);
    }


    public Response addCustomer(CustomerForm form) {

        if (accountService.findId(form.getUsername()) != null) {

            String msg = "이미 등록 된 유저 입니다";

            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(msg).build();

        } else if (form.getUsername().isEmpty() && form.getPassword().isEmpty()) {

            return Response.serverError().entity(form.getMessage()).build();

        } else {


            return Response.ok(form.getUsername() + " 님의 회원등록 ").build();
        }

    }

    public Response finduser(@QueryParam("username") String username) {
        UserModel userModel = new UserRepresentation();
        userModel.setUsername(username);
        UserModel us = accountService.findUser(userModel);

        return Response.ok(us).build();

    }

    public Customer registerCustomer(CustomerForm form) {

        Customer customer = new Customer();

        customer.setCustomerName(form.getUsername());
        customer.setEmail(form.getUsername());

        customerRepository.add(customer);

        return null;
    }

}
