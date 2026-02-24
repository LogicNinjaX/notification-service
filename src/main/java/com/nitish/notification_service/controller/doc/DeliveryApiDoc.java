package com.nitish.notification_service.controller.doc;

import com.nitish.notification_service.dto.response.ApiResponse;
import com.nitish.notification_service.dto.response.DeliveryResponse;
import com.nitish.notification_service.dto.response.PageResponse;
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

@Tag(name = "Delivery Management", description = "APIs for tracking message deliveries")
@SecurityRequirement(name = "bearerAuth")
public interface DeliveryApiDoc {

    @Operation(
            summary = "Get delivery by message ID",
            description = "Fetch delivery details for a specific message (ADMIN only)"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Delivery fetched successfully",
                    content = @Content(schema = @Schema(implementation = DeliveryResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Delivery not found",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied")
    })
    ResponseEntity<ApiResponse<DeliveryResponse>> getDeliveryByMessageId(
            CustomUserDetails user,
            @Parameter(description = "Message ID", example = "550e8400-e29b-41d4-a716-446655440000")
            UUID messageId
    );

    @Operation(
            summary = "Get deliveries by request ID",
            description = "Fetch paginated deliveries for a given notification request. " +
                    "ADMIN sees all deliveries. CLIENT/USER sees only their own."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Deliveries fetched successfully",
                    content = @Content(schema = @Schema(implementation = PageResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Request not found",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied")
    })
    ResponseEntity<ApiResponse<PageResponse<DeliveryResponse>>> getDeliveries(
            CustomUserDetails user,
            @Parameter(description = "Notification Request ID", example = "550e8400-e29b-41d4-a716-446655440000")
            UUID requestId,
            @ParameterObject
            Pageable pageable
    );
}
