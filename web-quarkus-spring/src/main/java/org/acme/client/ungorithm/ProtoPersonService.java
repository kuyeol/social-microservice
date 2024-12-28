package org.acme.client.ungorithm;


import com.google.protobuf.Empty;
import com.google.protobuf.Int32Value;
import com.google.protobuf.StringValue;
import io.quarkus.grpc.GrpcService;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import org.acme.client.Person;
import org.acme.client.Persons;
import org.acme.client.PersonsService;


@GrpcService
public class ProtoPersonService implements PersonsService {




    @Override
    public Uni<Persons> findByName(StringValue request) {




        SecurityManager sm = System.getSecurityManager();
        return null;
    }


    @Override
    public Uni<Persons> findByAge(Int32Value request) {
        return null;
    }


    @Override
    public Uni<Persons> findAll(Empty request) {
        return null;
    }

    @Override
    public Uni<Person> addPerson(Person request) {
        return null;
    }


    @Override
    public Uni<Person> findById(StringValue request) {
        return null;
    }




    public Uni<Person> sayHello(Person request ) {

        String name = request.getUsername();
        String message = "Hello " + name;

        JpaEntity entity = new JpaEntity();
        entity.setUsername(name);


        return Uni.createFrom().item(() ->
            Person.newBuilder().setUsername("Hello " + request.getUsername()).build());
    }

    private Persons mapToPersons(List<JpaEntity> list) {
        Persons.Builder builder = Persons.newBuilder();
        list.forEach(p -> builder.addPerson(mapToPerson(p)));

        return builder.build();
    }

    private Person mapToPerson(JpaEntity entity) {
        Person.Builder builder = Person.newBuilder();
        if (entity != null) {
            return builder.setUsername(entity.getUsername()).setId(entity.getId()).build();
        } else {
            return null;
        }
    }

}





