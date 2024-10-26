package org.acme.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.acme.dto.BookDto;
import org.acme.dto.TDTO;
import org.acme.dto.UserDto;
import org.acme.entity.Book;
import org.acme.entity.User;
import org.acme.entity.location.Venue;
import org.acme.mdoel.VenueModel;

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

    //public static Stream<UserDto> toUserDto(Stream<User> user) {
    //    if (user == null) {
    //        return null;
    //    }
    //    Stream<UserDto> dtos = user.map(UserMapper::toUserDt);
    //
    //    return dtos;
    //}

    public static BookDto toDto(Book entity) {
        return new BookDto(entity.getName(), entity.getAuthor());
    }


}
