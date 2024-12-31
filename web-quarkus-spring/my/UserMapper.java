import java.util.Optional;


public class UserMapper implements Mapper<User, UserDto> {

    @Override
    public User toDto(UserDto userDto) {

        return Optional.ofNullable(userDto)
                       .map(this::createUserFromDto)
                       .orElse(null);
    }


    @Override
    public UserDto toEntity(User user) {

        return Optional.ofNullable(user)
                       .map(this::createDtoFromUser)
                       .orElse(null);
    }


    private User createUserFromDto(UserDto dto) {

        try {
            return new User.Builder().setName(dto.getUsername())
                                     .username(dto.getName())
                                     .setUsername(dto.getUsername())
                                     .create();
        } catch (Exception e) {
            throw new UserConversionException("Failed to convert UserDto to User", e);
        }

    }


    private UserDto createDtoFromUser(User user) {

        UserDto userDto = new UserDto();
        userDto.setName(user.getUsername());

        return userDto;
    }

}
