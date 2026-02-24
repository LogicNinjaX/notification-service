package com.nitish.notification_service.controller.doc;

import com.nitish.notification_service.dto.response.ApiResponse;
import com.nitish.notification_service.dto.response.NotificationRequestResponse;
import com.nitish.notification_service.dto.response.PageResponse;
import com.nitish.notification_service.enums.RequestStatus;
import com.nitish.notification_service.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "Notification Requests", description = "APIs for managing notification requests")
@SecurityRequirement(name = "bearerAuth")
public interface RequestApiDoc {


    @Operation(
            summary = "Get my notification requests",
            description = "Fetch paginated notification requests created by the authenticated user"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Request details fetched successfully",
                    content = @Content(schema = @Schema(implementation = PageResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied")
    })
    ResponseEntity<ApiResponse<PageResponse<NotificationRequestResponse>>> getUserNotificationRequests
            (
                    CustomUserDetails user,
                    @ParameterObject
                    Pageable pageable
            );


    @Operation(
            summary = "Update notification request status (ADMIN)",
            description = "Update the status of a notification request"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Request status updated"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid status",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Request not found",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied")
    })
    ResponseEntity<Void> updateRequestStatus(
            @Parameter(description = "Notification Request ID",
                    example = "550e8400-e29b-41d4-a716-446655440000")
            UUID requestId,
            @Parameter(description = "New request status",
                    example = "COMPLETED")
            RequestStatus status
    );
}
