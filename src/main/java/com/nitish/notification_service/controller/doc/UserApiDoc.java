package com.nitish.notification_service.controller.doc;

import com.nitish.notification_service.dto.request.UserRegisterRequest;
import com.nitish.notification_service.dto.request.UserUpdateRequest;
import com.nitish.notification_service.dto.response.ApiResponse;
import com.nitish.notification_service.dto.response.UserDetailsResponse;
import com.nitish.notification_service.dto.response.UserRegisterResponse;
import com.nitish.notification_service.dto.response.UserUpdateResponse;
import com.nitish.notification_service.enums.UserRole;
import com.nitish.notification_service.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "User Management", description = "APIs for managing users")
@SecurityRequirement(name = "bearerAuth")
public interface UserApiDoc {

    @Operation(summary = "Register user (ADMIN)", description = "Create a new user under a client")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "User created",
                    content = @Content(schema = @Schema(implementation = UserRegisterResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Duplicate username/email",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied")
    })
    ResponseEntity<ApiResponse<UserRegisterResponse>> registerUser(UserRegisterRequest request);

    @Operation(summary = "Get user by ID (ADMIN)")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User fetched",
                    content = @Content(schema = @Schema(implementation = UserDetailsResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied")
    })
    ResponseEntity<ApiResponse<UserDetailsResponse>> getUserDetails(
            @Parameter(description = "User ID", example = "550e8400-e29b-41d4-a716-446655440000")
            UUID userId
    );

    @Operation(summary = "Get my profile", description = "Fetch details of the authenticated user")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User fetched",
                    content = @Content(schema = @Schema(implementation = UserDetailsResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ResponseEntity<ApiResponse<UserDetailsResponse>> getUserDetails(CustomUserDetails userDetails);


    @Operation(summary = "Update user by ID (ADMIN)", description = "Partially update user details")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User updated",
                    content = @Content(schema = @Schema(implementation = UserUpdateResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Duplicate or invalid input",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied")
    })
    ResponseEntity<ApiResponse<UserUpdateResponse>> updateUserDetails(
            @Parameter(description = "User ID", example = "550e8400-e29b-41d4-a716-446655440000")
            UUID userId,
            UserUpdateRequest request
    );


    @Operation(summary = "Update my profile", description = "Partially update authenticated user details")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User updated",
                    content = @Content(schema = @Schema(implementation = UserUpdateResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Duplicate or invalid input",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    ResponseEntity<ApiResponse<UserUpdateResponse>> updateUserDetails(CustomUserDetails userDetails, UserUpdateRequest request);


    @Operation(summary = "Delete user by ID (ADMIN)")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "User deleted"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied")
    })
    ResponseEntity<Void> deleteUser(
            @Parameter(description = "User ID", example = "550e8400-e29b-41d4-a716-446655440000")
            UUID userId
    );

    @Operation(summary = "Delete my account")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "User deleted"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ResponseEntity<Void> deleteUser(CustomUserDetails userDetails);

    @Operation(summary = "Update user role (ADMIN)")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Role updated"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid role",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied")
    })
    ResponseEntity<Void> updateUserRole(
            @Parameter(description = "User ID", example = "550e8400-e29b-41d4-a716-446655440000")
            UUID userId,
            @Parameter(description = "New role", example = "ROLE_CLIENT")
            UserRole role
    );
}
