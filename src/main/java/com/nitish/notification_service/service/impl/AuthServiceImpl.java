package com.nitish.notification_service.service.impl;

import com.nitish.notification_service.dto.request.ClientRegisterRequest;
import com.nitish.notification_service.dto.request.UserRegisterRequest;
import com.nitish.notification_service.dto.response.ClientRegisterResponse;
import com.nitish.notification_service.dto.response.UserRegisterResponse;
import com.nitish.notification_service.entity.Client;
import com.nitish.notification_service.entity.User;
import com.nitish.notification_service.enums.ClientStatus;
import com.nitish.notification_service.enums.UserRole;
import com.nitish.notification_service.exception.custom_exception.DuplicateFieldException;
import com.nitish.notification_service.exception.custom_exception.EntityNotFoundException;
import com.nitish.notification_service.repository.ClientRepository;
import com.nitish.notification_service.repository.UserRepository;
import com.nitish.notification_service.security.CustomUserDetails;
import com.nitish.notification_service.service.AuthService;
import com.nitish.notification_service.util.JWTUtil;
import com.nitish.notification_service.util.mapper.ClientMapper;
import com.nitish.notification_service.util.mapper.ResponseMapper;
import com.nitish.notification_service.util.mapper.UserMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final ClientMapper clientMapper;
    private final ResponseMapper responseMapper;
    private final JWTUtil jwtUtil;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, ClientRepository clientRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, ClientMapper clientMapper, ResponseMapper responseMapper, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.clientMapper = clientMapper;
        this.responseMapper = responseMapper;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    @Override
    public ClientRegisterResponse registerAsClient(ClientRegisterRequest request) {
        Client client = clientMapper.toClient(request);
        client.setStatus(ClientStatus.ACTIVE);

        User user = userMapper.toUser(request);
        user.setClient(client);
        user.setRole(UserRole.ROLE_CLIENT);
        user.setPassword(passwordEncoder.encode(request.loginDetails().password()));

        client.getUsers().add(user);

        client = clientRepository.save(client);

        logger.info("CLIENT registered successfully [client id={}, user id={}]", client.getClientId(), user.getUserId());
        return responseMapper.toRegisterResponse(user, client);
    }

    @Transactional
    @Override
    public UserRegisterResponse registerAsUser(UserRegisterRequest request) {
        Client client = clientRepository.findById(request.clientId())
                .orElseThrow(() -> new EntityNotFoundException("client", request.clientId()));

        User user = userMapper.toUser(request);
        user.setRole(UserRole.ROLE_USER);
        user.setPassword(passwordEncoder.encode(request.password()));
        try {
            user.setClient(client);
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            String message = e.getMessage();
            if (message.contains("uk_username")) throw new DuplicateFieldException("username already exists");
            if (message.contains("uk_email")) throw new DuplicateFieldException("email already exists");
        }

        logger.info("user registered successfully [id={}, username={}]", user.getUserId(), user.getUsername());
        return userMapper.toRegisterResponse(user);
    }

    @Override
    public String login(String username, String password) {
        var authToken = new UsernamePasswordAuthenticationToken(username, password);
        var authentication = authenticationManager.authenticate(authToken);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return jwtUtil.generateToken(userDetails);
    }

}
