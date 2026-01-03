package com.nitish.notification_service.service.impl;

import com.nitish.notification_service.dto.request.ClientRegisterRequest;
import com.nitish.notification_service.dto.response.ClientRegisterResponse;
import com.nitish.notification_service.entity.Client;
import com.nitish.notification_service.enums.ClientStatus;
import com.nitish.notification_service.repository.ClientRepository;
import com.nitish.notification_service.service.ClientService;
import com.nitish.notification_service.util.mapper.ClientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public ClientRegisterResponse registerClient(ClientRegisterRequest request) {
        Client client = clientMapper.toClient(request);
        client.setStatus(ClientStatus.ACTIVE);
        clientRepository.save(client);
        logger.info("client registered successfully [id={}, name={}]", client.getClientId(), client.getFullName());
        return clientMapper.toRegisterResponse(client);
    }
}
