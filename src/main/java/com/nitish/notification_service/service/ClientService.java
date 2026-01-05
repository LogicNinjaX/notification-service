package com.nitish.notification_service.service;

import com.nitish.notification_service.dto.request.ClientRegisterRequest;
import com.nitish.notification_service.dto.request.ClientUpdateRequest;
import com.nitish.notification_service.dto.response.ClientRegisterResponse;
import com.nitish.notification_service.dto.response.ClientUpdateResponse;

import java.util.UUID;

public interface ClientService {
    ClientRegisterResponse registerClient(ClientRegisterRequest request);

    void updateClientStatus(UUID clientId, String status);

    ClientUpdateResponse updateClientInfo(UUID clientId, ClientUpdateRequest request);
}
