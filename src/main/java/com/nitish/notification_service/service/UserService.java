package com.nitish.notification_service.service;

import com.nitish.notification_service.dto.request.UserRegisterRequest;
import com.nitish.notification_service.dto.request.UserUpdateRequest;
import com.nitish.notification_service.dto.response.UserDetailsResponse;
import com.nitish.notification_service.dto.response.UserRegisterResponse;
import com.nitish.notification_service.dto.response.UserUpdateResponse;
import com.nitish.notification_service.enums.UserRole;

import java.util.UUID;

public interface UserService {

    UserRegisterResponse registerUser(UserRegisterRequest request);

    UserDetailsResponse getUser(UUID userId);

    UserUpdateResponse updateUserDetails(UUID userId, UserUpdateRequest request);

    void deleteUser(UUID userId);

    void updateUserRole(UUID userId, UserRole userRole);
}
