package com.nitish.notification_service.controller.doc;

import com.nitish.notification_service.dto.request.SendNotificationRequest;
import com.nitish.notification_service.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Notification API", description = "APIs for sending notifications")
@SecurityRequirement(name = "bearerAuth")
public interface NotificationApiDoc {

    @Operation(
            summary = "Send notification",
            description = """
                    Sends a notification using a template.
                    ADMIN and CLIENT roles are allowed.
                    Processing may be asynchronous.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Notification accepted for processing"),
            @ApiResponse(responseCode = "400", description = "Validation error or invalid template",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "Template or client not found",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "409", description = "Invalid notification request",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    ResponseEntity<Void> sendNotification
            (
                    CustomUserDetails user,
                    SendNotificationRequest request
            );
}
