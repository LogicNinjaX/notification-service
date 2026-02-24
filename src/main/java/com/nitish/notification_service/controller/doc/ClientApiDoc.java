package com.nitish.notification_service.controller.doc;

import com.nitish.notification_service.dto.request.ClientUpdateRequest;
import com.nitish.notification_service.dto.response.ApiResponse;
import com.nitish.notification_service.dto.response.ClientUpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "Client Management", description = "APIs for managing client accounts")
@SecurityRequirement(name = "bearerAuth") // IMPORTANT for JWT
public interface ClientApiDoc {

    @Operation(
            summary = "Update client status",
            description = "Updates the status of a client (ADMIN only)"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Client status updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid status",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Client not found",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Invalid state transition",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied")
    })
    ResponseEntity<ApiResponse<Void>> updateClientStatus(
            @Parameter(description = "Client ID", example = "550e8400-e29b-41d4-a716-446655440000")
            UUID clientId,
            @Parameter(description = "New status value", example = "ACTIVE")
            String status
    );

    @Operation(
            summary = "Update client details",
            description = "Partially updates client information (ADMIN only)"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Client updated successfully",
                    content = @Content(schema = @Schema(implementation = ClientUpdateResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Client not found",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Duplicate or invalid input",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied")
    })
    ResponseEntity<ApiResponse<ClientUpdateResponse>> updateClient(
            @Parameter(description = "Client ID", example = "550e8400-e29b-41d4-a716-446655440000")
            UUID clientId,
            ClientUpdateRequest request
    );
}
