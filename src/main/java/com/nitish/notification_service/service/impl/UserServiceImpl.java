package com.nitish.notification_service.service.impl;

import com.nitish.notification_service.dto.request.UserRegisterRequest;
import com.nitish.notification_service.dto.request.UserUpdateRequest;
import com.nitish.notification_service.dto.response.UserDetailsResponse;
import com.nitish.notification_service.dto.response.UserRegisterResponse;
import com.nitish.notification_service.dto.response.UserUpdateResponse;
import com.nitish.notification_service.entity.User;
import com.nitish.notification_service.enums.UserRole;
import com.nitish.notification_service.exception.custom_exception.DuplicateFieldException;
import com.nitish.notification_service.exception.custom_exception.EntityNotFoundException;
import com.nitish.notification_service.repository.UserRepository;
import com.nitish.notification_service.service.UserService;
import com.nitish.notification_service.util.mapper.UserMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserRegisterResponse registerUser(UserRegisterRequest request){
        User user = userMapper.toUser(request);
        try {
            user = userRepository.save(user);
        }catch (ConstraintViolationException e){
            String message = e.getMessage();
            if (message.contains("uk_username")) throw new DuplicateFieldException("username already exists");
            if (message.contains("uk_email")) throw new DuplicateFieldException("email already exists");
        }

        logger.info("user record successfully [id={}, username={}]", user.getUserId(), user.getUsername());
        return userMapper.toRegisterResponse(user);
    }

    @Override
    public UserDetailsResponse getUser(UUID userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("user", userId));
        return userMapper.toDetailsResponse(user);
    }

    @Transactional
    @Override
    public UserUpdateResponse updateUserDetails(UUID userId, UserUpdateRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("user", userId));

        if (request.email() != null) user.setEmail(request.email());
        if (request.password() != null) user.setPassword(request.password()); // TODO -> Must be encrypted before save
        if (request.username() != null) user.setUsername(request.username());

        user = userRepository.save(user);
        logger.info("user record updated successfully [id={}, username={}]", userId, user.getUsername());
        return userMapper.toUpdateResponse(user);
    }

    @Transactional
    @Override
    public void deleteUser(UUID userId){
        int updatedRows = userRepository.deleteUser(userId);
        if (updatedRows > 0) logger.info("user record updated successfully [id={}]", userId);
    }

    @Transactional
    @Override
    public void updateUserRole(UUID userId, UserRole userRole){
        int updatedRows = userRepository.updateUserRole(userId, userRole);
        if (updatedRows > 0) logger.info("user role updated successfully [id={}, role={}]", userId, userRole);
    }
}
