package com.nitish.notification_service.service;

import com.nitish.notification_service.dto.response.ClientResponse;
import com.nitish.notification_service.dto.response.PageResponse;
import com.nitish.notification_service.enums.ClientStatus;
import com.nitish.notification_service.enums.UserRole;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AdminService {

    void updateClientStatus(UUID clientId, ClientStatus status);

    PageResponse<ClientResponse> getClientsByStatus(ClientStatus status, Pageable pageable);

    void updateUserRole(UUID userId, UserRole role);
}
