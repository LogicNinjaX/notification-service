package com.nitish.notification_service.controller;

import com.nitish.notification_service.controller.doc.UserApiDoc;
import com.nitish.notification_service.dto.request.UserRegisterRequest;
import com.nitish.notification_service.dto.request.UserUpdateRequest;
import com.nitish.notification_service.dto.response.ApiResponse;
import com.nitish.notification_service.dto.response.UserDetailsResponse;
import com.nitish.notification_service.dto.response.UserRegisterResponse;
import com.nitish.notification_service.dto.response.UserUpdateResponse;
import com.nitish.notification_service.enums.UserRole;
import com.nitish.notification_service.security.CustomUserDetails;
import com.nitish.notification_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class UserController implements UserApiDoc {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/users", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ApiResponse<UserRegisterResponse>> registerUser(@RequestBody @Valid UserRegisterRequest request) {
        var response = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "user record saved successfully"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/users/{userId}", produces = APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ApiResponse<UserDetailsResponse>> getUserDetails(@PathVariable UUID userId) {
        var response = userService.getUser(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response, "user record fetched successfully"));
    }

    @GetMapping(path = "/users/my", produces = APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ApiResponse<UserDetailsResponse>> getUserDetails(@AuthenticationPrincipal CustomUserDetails userDetails) {
        var response = userService.getUser(userDetails.getUserId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response, "user record fetched successfully"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(path = "/users/{userId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ApiResponse<UserUpdateResponse>> updateUserDetails(@PathVariable UUID userId, @RequestBody @Valid UserUpdateRequest request) {
        var response = userService.updateUserDetails(userId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response, "user record updated successfully"));
    }

    @PatchMapping(path = "/users/my", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ApiResponse<UserUpdateResponse>> updateUserDetails(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody @Valid UserUpdateRequest request) {
        var response = userService.updateUserDetails(userDetails.getUserId(), request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response, "user record updated successfully"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/users/{userId}")
    @Override
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/users/my")
    @Override
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.deleteUser(userDetails.getUserId());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(path = "/users/{userId}/role")
    @Override
    public ResponseEntity<Void> updateUserRole(@PathVariable UUID userId, @RequestParam UserRole role) {
        userService.updateUserRole(userId, role);
        return ResponseEntity.noContent().build();
    }
}
