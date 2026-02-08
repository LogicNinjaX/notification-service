package com.nitish.notification_service.service;

import com.nitish.notification_service.dto.request.ClientRegisterRequest;
import com.nitish.notification_service.dto.request.UserRegisterRequest;
import com.nitish.notification_service.dto.response.ClientRegisterResponse;
import com.nitish.notification_service.dto.response.UserRegisterResponse;

public interface AuthService {

    ClientRegisterResponse registerClient(ClientRegisterRequest request);

    UserRegisterResponse registerUser(UserRegisterRequest request);

    String login(String username, String password);
}
