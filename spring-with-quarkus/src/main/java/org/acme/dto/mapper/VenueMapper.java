package org.acme.dto.mapper;

import java.util.List;
import java.util.Objects;
import org.acme.dto.Dto;
import org.acme.entity.location.Venue;
import org.springframework.stereotype.Service;


@Service
public class VenueMapper {


    public Dto userToUserDTO(Venue user) {
        return new Dto(user);
    }

    public List<Venue> userDTOsToUsers(List<Dto> userDTOs) {
        return userDTOs.stream().filter(Objects::nonNull).map(this::VenueDTOToUser).toList();
    }

    public Venue VenueDTOToUser(Dto userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            Venue user = new Venue();
            user.setVenueName(userDTO.getName());
            user.setSize(userDTO.getSize());
            return user;
        }
    }


}
