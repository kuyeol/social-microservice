package org.acme.mapper;

import org.acme.dto.BookDto;
import org.acme.dto.TDTO;
import org.acme.dto.UserDto;

import org.acme.entity.person.User;

public class UserMapper {


    public static User toEntity(TDTO dto) {
        return new User(dto.getFirstName(), dto.getLastName(), dto.getAddress());
    }

    public static TDTO toDTO(User user) {

        return new TDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getAddress());
    }

    public static UserDto toUserDt(User user) throws Exception {

        if (user == null) {
            throw new Exception("User is null");
        }

        UserDto rep = new UserDto();

        if (user.getFirstName() != null && !user.getFirstName().isEmpty()) {
            rep.setUsername(user.getFirstName());
        }


        return rep;
    }





}
