package org.acme.repository;

import java.util.Optional;
import org.acme.service.customer.entity.User;
import org.acme.service.venue.entity.Venue;


public class AdminRepository implements Repository<Venue> {


    @Override
    public Optional<Venue> findByName(String name) {
        return null;
    }

    @Override
    public void add(Venue a) {}


    @Override
    public boolean remove(Venue venue) {
        return false;
    }
}