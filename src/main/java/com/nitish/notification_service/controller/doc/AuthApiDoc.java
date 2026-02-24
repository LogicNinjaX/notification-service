package com.nitish.notification_service.controller.doc;

import com.nitish.notification_service.dto.request.ClientRegisterRequest;
import com.nitish.notification_service.dto.request.LoginRequest;
import com.nitish.notification_service.dto.request.UserRegisterRequest;
import com.nitish.notification_service.dto.response.ApiResponse;
import com.nitish.notification_service.dto.response.ClientRegisterResponse;
import com.nitish.notification_service.dto.response.LoginResponse;
import com.nitish.notification_service.dto.response.UserRegisterResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Authentication API", description = "APIs for authentication and registration")
public interface AuthApiDoc {

    @Operation(
            summary = "Login user",
            description = "Authenticates user and returns JWT token"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Login success",
                    content = @Content(
                            schema = @Schema(implementation = LoginResponse.class)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponse.class)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),

            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Duplicate or invalid input",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),

            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Entity not found",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    ResponseEntity<ApiResponse<LoginResponse>> login(LoginRequest request);

    @Operation(
            summary = "Register new user",
            description = "Registers a new user under an existing client"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "User registered successfully",
                    content = @Content(
                            schema = @Schema(implementation = UserRegisterResponse.class)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponse.class)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Client not found",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),

            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "User already exists",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    ResponseEntity<ApiResponse<UserRegisterResponse>> registerUser(UserRegisterRequest request);

    @Operation(
            summary = "Register new client",
            description = "Registers a new client and its primary admin user"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Client registered successfully",
                    content = @Content(schema = @Schema(implementation = ClientRegisterResponse.class))),

            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),

            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Client already exists",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    ResponseEntity<ApiResponse<ClientRegisterResponse>> registerClient(ClientRegisterRequest request);
}
