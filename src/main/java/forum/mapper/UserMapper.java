package forum.mapper;

import forum.dto.UserDto;
import forum.model.UserEntity;

public class UserMapper {
    public static UserDto mapToDto(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .login(userEntity.getLogin())
                .build();
    }
}
