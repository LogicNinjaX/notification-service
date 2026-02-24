package com.nitish.notification_service.controller.doc;

import com.nitish.notification_service.dto.request.CreateTemplateRequest;
import com.nitish.notification_service.dto.request.UpdateTemplateRequest;
import com.nitish.notification_service.dto.response.*;
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

@Tag(name = "Template Management", description = "APIs for managing notification templates")
@SecurityRequirement(name = "bearerAuth")
public interface TemplateApiDoc {


    @Operation(
            summary = "Create template",
            description = "Create a new notification template for the authenticated user"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Template created",
                    content = @Content(schema = @Schema(implementation = CreateTemplateResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Duplicate template name",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied")
    })
    ResponseEntity<ApiResponse<CreateTemplateResponse>> createTemplate
            (
                    CustomUserDetails user,
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "Template creation request",
                            required = true,
                            content = @Content(schema = @Schema(implementation = CreateTemplateRequest.class))
                    )
                    CreateTemplateRequest request
            );


    @Operation(
            summary = "Update template",
            description = "Update an existing template owned by the authenticated user"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Template updated",
                    content = @Content(schema = @Schema(implementation = UpdateTemplateResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid content or validation error",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Template not found",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Duplicate template name",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied")
    })
    ResponseEntity<ApiResponse<UpdateTemplateResponse>> updateTemplate
            (
                    CustomUserDetails user,
                    @Parameter(description = "Template ID",
                            example = "550e8400-e29b-41d4-a716-446655440000")
                    UUID templateId,
                    UpdateTemplateRequest request
            );


    @Operation(
            summary = "Get my templates",
            description = "Fetch paginated templates created by the authenticated user"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Templates fetched",
                    content = @Content(schema = @Schema(implementation = PageResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ResponseEntity<ApiResponse<PageResponse<TemplateResponse>>> getTemplates
            (
                    CustomUserDetails user,

                    @ParameterObject
                    Pageable pageable
            );

    @Operation(
            summary = "Delete template",
            description = "Delete a template owned by the authenticated user"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Template deleted"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Template not found",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied")
    })
    ResponseEntity<Void> deleteTemplate(
            CustomUserDetails user,

            @Parameter(description = "Template ID",
                    example = "550e8400-e29b-41d4-a716-446655440000")
            UUID templateId
    );
}
