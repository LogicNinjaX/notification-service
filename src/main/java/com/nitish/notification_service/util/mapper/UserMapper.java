package com.nitish.notification_service.util.mapper;

import com.nitish.notification_service.dto.request.ClientRegisterRequest;
import com.nitish.notification_service.dto.request.UserRegisterRequest;
import com.nitish.notification_service.dto.response.UserDetailsResponse;
import com.nitish.notification_service.dto.response.UserRegisterResponse;
import com.nitish.notification_service.dto.response.UserUpdateResponse;
import com.nitish.notification_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "templates", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "notificationRequests", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "client", ignore = true)
    User toUser(UserRegisterRequest request);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "templates", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "notificationRequests", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(source = "loginDetails.username", target = "username")
    @Mapping(source = "loginDetails.email", target = "email")
    @Mapping(source = "loginDetails.email", target = "password")
    User toUser(ClientRegisterRequest request);

    UserRegisterResponse toRegisterResponse(User user);

    UserDetailsResponse toDetailsResponse(User user);

    UserUpdateResponse toUpdateResponse(User user);
}
