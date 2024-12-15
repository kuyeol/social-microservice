package org.acme;


import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;
import org.acme.service.customer.entity.User;


@ApplicationScoped
@Path("/user")
public class ExtensionsResource {


    @Inject
    EntityManager em;

    @Transactional
    public void addUser(String a) {

        User u = new User();
        u.setUsername(a);
        u.setPassword(a);
        em.persist(u);

    }


    @POST
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String makeUser(String a) {

        addUser(a);

        return "hello ";
    }

    @POST
    @Path("/log")
    @Produces(MediaType.TEXT_PLAIN)
    public boolean isValid(@QueryParam("a") String a, @QueryParam("b") String b) {


        return isAUTH(a, b);
    }


    public boolean isAUTH(String id, String pw) {
        User u = new User();

        u.setPassword(pw);
        User isUser = em.find(User.class, id);
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> queryBuilder = builder.createQuery(User.class);
        Root<User> root = queryBuilder.from(User.class);
        TypedQuery<User> queryName = em.createNamedQuery("findByName", User.class);

        queryName.setParameter("name", id.toLowerCase());


        queryName.getResultList();


        System.out.println(queryName.getResultList().toString());

        System.out.println(id);
        System.out.println(queryName.getSingleResult().getUsername());
        System.out.println(queryName.getSingleResult());
        User vaild = new User();

        vaild.setPassword(queryName.getSingleResult().getPassword());

        List<User> sList= new ArrayList<>();

        sList.add(queryName.getSingleResult());
        for(User temp:sList){
          boolean v=  temp.getUsername().equals(id);
            boolean v2= temp.getPassword().equals(pw);
            System.out.println(v+"");
            System.out.println(""+v2);
        }



        if (id.equals(queryName.getSingleResult().getUsername()) && pw.equals(vaild.getPassword())) {

            return true;
        } else {
            return false;
        }

    }


    @GET
    @RolesAllowed("user")
    @Path("/me")
    public String me(@Context SecurityContext securityContext) {
        String ctx = securityContext.getUserPrincipal().getName();

        if (ctx == null) {
            return "NOT ALLOWED";
        } else {
            return ctx;
        }
    }
    // curl -i -X GET -u user:user http://localhost:8080/user/me

}
