package com.nitish.notification_service.controller;

import com.nitish.notification_service.dto.request.UserRegisterRequest;
import com.nitish.notification_service.dto.request.UserUpdateRequest;
import com.nitish.notification_service.dto.response.ApiResponse;
import com.nitish.notification_service.dto.response.UserDetailsResponse;
import com.nitish.notification_service.dto.response.UserRegisterResponse;
import com.nitish.notification_service.dto.response.UserUpdateResponse;
import com.nitish.notification_service.enums.UserRole;
import com.nitish.notification_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<UserRegisterResponse>> registerUser(@RequestBody @Valid UserRegisterRequest request) {
        var response = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "user record saved successfully"));
    }

    @GetMapping(path = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<UserDetailsResponse>> getUserDetails(@PathVariable UUID userId) {
        var response = userService.getUser(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response, "user record fetched successfully"));
    }

    @PatchMapping(path = "/user/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<UserUpdateResponse>> updateUserDetails(@PathVariable UUID userId, @RequestBody @Valid UserUpdateRequest request) {
        var response = userService.updateUserDetails(userId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response, "user record updated successfully"));
    }

    @DeleteMapping(path = "/user/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/user/{userId}/role")
    public ResponseEntity<Void> updateUserRole(@PathVariable UUID userId, @RequestParam UserRole role) {
        userService.updateUserRole(userId, role);
        return ResponseEntity.noContent().build();
    }
}
