package com.nitish.notification_service.controller;

import com.nitish.notification_service.controller.doc.TemplateApiDoc;
import com.nitish.notification_service.dto.request.CreateTemplateRequest;
import com.nitish.notification_service.dto.request.UpdateTemplateRequest;
import com.nitish.notification_service.dto.response.*;
import com.nitish.notification_service.security.CustomUserDetails;
import com.nitish.notification_service.service.TemplateService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/templates")
public class TemplateController implements TemplateApiDoc {

    private final TemplateService templateService;

    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ApiResponse<CreateTemplateResponse>> createTemplate
    (
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody @Valid CreateTemplateRequest request
    )
    {
        var response = templateService.createTemplate(user.getUserId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Template created successfully"));
    }

    @PatchMapping(path = "/{templateId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ApiResponse<UpdateTemplateResponse>> updateTemplate
    (
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable UUID templateId,
            @RequestBody UpdateTemplateRequest request
    ){
        var response = templateService.updateTemplate(templateId, user.getUserId(), request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response, "Template details updated successfully"));
    }

    @GetMapping(path = "/my", produces = APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ApiResponse<PageResponse<TemplateResponse>>> getTemplates
    (
            @AuthenticationPrincipal CustomUserDetails user,
            @PageableDefault(sort = {"createdAt"}) Pageable pageable
    ){
        var pageResponse = templateService.getTemplatesByCreatorId(user.getUserId(), pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(pageResponse, "Template details fetched successfully"));
    }

    @DeleteMapping(path = "/{templateId}", produces = APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<Void> deleteTemplate(@AuthenticationPrincipal CustomUserDetails user, @PathVariable UUID templateId){
        templateService.deleteTemplateByUserAndTemplateId(user.getUserId(), templateId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
