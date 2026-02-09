package com.nitish.notification_service.service.impl;

import com.nitish.notification_service.dto.request.ClientUpdateRequest;
import com.nitish.notification_service.dto.response.ClientUpdateResponse;
import com.nitish.notification_service.entity.Client;
import com.nitish.notification_service.enums.ClientStatus;
import com.nitish.notification_service.exception.custom_exception.EntityNotFoundException;
import com.nitish.notification_service.exception.custom_exception.InvalidStatusException;
import com.nitish.notification_service.repository.ClientRepository;
import com.nitish.notification_service.service.ClientService;
import com.nitish.notification_service.util.mapper.ClientMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Transactional
    @Override
    public void updateClientStatus(UUID clientId, String status) {
        ClientStatus clientStatus = validateStatus(status);

        int updatedRows = clientRepository.updateClientStatus(clientId, clientStatus);
        if (updatedRows > 0) logger.info("client status updated successfully [id={}]", clientId);
    }

    private ClientStatus validateStatus(String status) {
        ClientStatus clientStatus;
        try {
            clientStatus = ClientStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.error("invalid client status found [value={}]", status);
            throw new InvalidStatusException("invalid client status [value=%s]".formatted(status));
        }

        return clientStatus;
    }

    @Transactional
    @Override
    public ClientUpdateResponse updateClientInfo(UUID clientId, ClientUpdateRequest request) {
        Client existingClient = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("client", clientId));

        if (request.email() != null) existingClient.setEmail(request.email());
        if (request.fullName() != null) existingClient.setFullName(request.fullName());
        existingClient = clientRepository.save(existingClient);
        logger.info("client details updated successfully [id={}, name={}]", clientId, existingClient.getFullName());
        return clientMapper.toUpdateResponse(existingClient);
    }


}
