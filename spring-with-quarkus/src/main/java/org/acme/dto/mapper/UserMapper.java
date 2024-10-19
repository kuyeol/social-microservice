package org.acme.dto.mapper;

import java.util.stream.Collectors;
import org.acme.dto.BookDto;
import org.acme.dto.TDTO;
import org.acme.entity.Book;
import org.acme.entity.User;

public class UserMapper {




    public static User toEntity(TDTO dto) {
        return new User(dto.getFirstName(), dto.getLastName(), dto.getAddress());
    }




    public static BookDto toDto(Book entity) {
        return new BookDto(entity.getName(), entity.getAuthor());
    }


}
