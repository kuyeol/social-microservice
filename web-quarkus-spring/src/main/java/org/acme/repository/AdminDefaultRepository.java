package org.acme.repository;

import java.util.Optional;
import org.acme.client.venue.entity.Venue;


public class AdminDefaultRepository implements DefaultRepository<Venue> {


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