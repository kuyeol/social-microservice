package org.acme.client.customer.service;

import java.util.List;
import org.acme.core.utils.Validator;

public class AccountValidator extends Validator {


    private int edge;
    private String namey;

    public AccountValidator( int edge) throws ClassNotFoundException {

        super("name");
        List<String> names = List.of("Jak", "Paula", "Kate", "Peter");

        names.stream()
            .filter(name -> name.length() < 5)
            .filter(name -> name.length() > 3)
            .forEach(name -> System.out.println(name));

        if (edge <= 0) {
            throw new IllegalArgumentException("Edge should be greater than 0");

        } else if (edge == 1) {

            throw new IllegalArgumentException("Edge should be greater than 0");
        }

        this.edge = edge;
    }

    public int edge() {
        return edge;
    }

    public String toString() {
        return  ""+ edge ;
    }

}
