package com.nitish.notification_service.util.mapper;

import com.nitish.notification_service.dto.response.ClientRegisterResponse;
import com.nitish.notification_service.entity.Client;
import com.nitish.notification_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ResponseMapper {

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "client.clientId", target = "clientId")
    ClientRegisterResponse toRegisterResponse(User user, Client client);
}
