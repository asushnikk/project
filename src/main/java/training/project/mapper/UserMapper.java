package training.project.mapper;

import org.mapstruct.Mapper;
import training.project.domain.User;
import training.project.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDto userDto);
}