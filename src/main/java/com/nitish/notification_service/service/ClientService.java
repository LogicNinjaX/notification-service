package com.nitish.notification_service.service;

import com.nitish.notification_service.dto.request.ClientRegisterRequest;
import com.nitish.notification_service.dto.response.ClientRegisterResponse;

public interface ClientService {
    ClientRegisterResponse registerClient(ClientRegisterRequest request);
}
