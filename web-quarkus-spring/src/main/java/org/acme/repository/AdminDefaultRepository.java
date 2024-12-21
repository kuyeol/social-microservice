package org.acme.repository;

import java.util.stream.Stream;
import org.acme.client.venue.entity.Venue;
import org.acme.core.spi.DefaultRepository;


public class AdminDefaultRepository implements DefaultRepository<Venue> {


    @Override
    public VenueModel findByName(String name) {
        return null;
    }

    @Override
    public Stream<Venue> findAll() {
        return Stream.empty();
    }

    @Override
    public void add(Venue a) {}


    @Override
    public boolean remove(Venue venue) {
        return false;
    }
}