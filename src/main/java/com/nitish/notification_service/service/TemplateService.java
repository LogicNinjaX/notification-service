package com.nitish.notification_service.service;

import com.nitish.notification_service.dto.request.CreateTemplateRequest;
import com.nitish.notification_service.dto.request.UpdateTemplateRequest;
import com.nitish.notification_service.dto.response.CreateTemplateResponse;
import com.nitish.notification_service.dto.response.PageResponse;
import com.nitish.notification_service.dto.response.TemplateResponse;
import com.nitish.notification_service.dto.response.UpdateTemplateResponse;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TemplateService {

    CreateTemplateResponse createTemplate(UUID userId, CreateTemplateRequest request);

    UpdateTemplateResponse updateTemplate(UUID templateId, UUID creatorId, UpdateTemplateRequest request);

    PageResponse<TemplateResponse> getTemplatesByCreatorId(UUID creatorId, Pageable pageable);

    void deleteTemplateByUserAndTemplateId(UUID userId, UUID templateId);
}
