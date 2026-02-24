package com.nitish.notification_service.controller.doc;

import com.nitish.notification_service.dto.response.ApiResponse;
import com.nitish.notification_service.dto.response.MessageResponse;
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

@Tag(name = "Message Management", description = "APIs for viewing rendered notification messages")
@SecurityRequirement(name = "bearerAuth")
public interface MessageApiDoc {

    @Operation(
            summary = "Get messages by request ID",
            description = """
                    Returns paginated messages for a notification request.
                    ADMIN can see all messages.
                    CLIENT/USER can only see their own messages.
                    """
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Messages fetched successfully",
                    content = @Content(schema = @Schema(implementation = PageResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Request not found",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied")
    })
    ResponseEntity<ApiResponse<PageResponse<MessageResponse>>> getMessages(
            CustomUserDetails user,
            @Parameter(description = "Notification Request ID", example = "550e8400-e29b-41d4-a716-446655440000")
            UUID requestId,
            @ParameterObject
            Pageable pageable
    );
}
