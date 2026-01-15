package com.ramona.capstone.mappers;

import com.ramona.capstone.dtos.RegisterUserRequest;
import com.ramona.capstone.dtos.UpdateUserRequest;
import com.ramona.capstone.dtos.UserDto;
import com.ramona.capstone.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request);
    void update(UpdateUserRequest request, @MappingTarget User user);

}
