package org.acme.mapper;

import org.acme.entity.location.Venue;
import org.acme.mdoel.VenueModel;


public class VenueMapper {

    public static Venue toEntity(VenueModel model) {
        Venue venue = new Venue();
        venue.setId();
        venue.setVenueName(model.getVenueName());
        venue.setSize(model.getSize());
        return venue;
    }


    public static VenueModel toModel(Venue entity) {
        VenueModel model = new VenueModel();
        model.setVenueName(entity.getVenueName());
        model.setSize(entity.getSize());
        return model;
    }


}
