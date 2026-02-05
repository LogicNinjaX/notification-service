package com.nitish.notification_service.service.impl;

import com.nitish.notification_service.dto.response.ClientResponse;
import com.nitish.notification_service.dto.response.PageResponse;
import com.nitish.notification_service.enums.ClientStatus;
import com.nitish.notification_service.enums.UserRole;
import com.nitish.notification_service.repository.ClientRepository;
import com.nitish.notification_service.repository.UserRepository;
import com.nitish.notification_service.service.AdminService;
import com.nitish.notification_service.util.mapper.ClientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final ClientMapper clientMapper;

    public AdminServiceImpl(ClientRepository clientRepository, UserRepository userRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.clientMapper = clientMapper;
    }


    @Override
    public void updateClientStatus(UUID clientId, ClientStatus status) {
        int updatedRows = clientRepository.updateClientStatus(clientId, status);
        if (updatedRows > 0) {
            logger.info("Client status updated successfully [client id={}, status={}]", clientId, status);
        }
    }

    @Override
    public PageResponse<ClientResponse> getClientsByStatus(ClientStatus status, Pageable pageable) {
        Page<ClientResponse> clientResponsePage = clientRepository.findAllClientsByStatus(status, pageable)
                .map(clientMapper::toClientResponse);
        return PageResponse.from(clientResponsePage);
    }

    @Override
    public void updateUserRole(UUID userId, UserRole role) {
        int updatedRows = userRepository.updateUserRole(userId, role);
        if (updatedRows > 0) {
            logger.info("Client status updated successfully [user id={}, role={}]", userId, role);
        }
    }

}
