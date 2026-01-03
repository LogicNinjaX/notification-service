package com.nitish.notification_service.util.mapper;

import com.nitish.notification_service.dto.request.ClientRegisterRequest;
import com.nitish.notification_service.dto.response.ClientRegisterResponse;
import com.nitish.notification_service.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "clientId", ignore = true)
    Client toClient(ClientRegisterRequest request);

    ClientRegisterResponse toRegisterResponse(Client client);
}
