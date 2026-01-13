package com.nitish.notification_service.util.mapper;

import com.nitish.notification_service.dto.request.CreateTemplateRequest;
import com.nitish.notification_service.dto.response.CreateTemplateResponse;
import com.nitish.notification_service.dto.response.UpdateTemplateResponse;
import com.nitish.notification_service.entity.NotificationTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TemplateMapper {

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "templateId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "placeholders", ignore = true)
    @Mapping(target = "notificationRequests", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "client", ignore = true)
    NotificationTemplate toTemplate(CreateTemplateRequest request);

    CreateTemplateResponse toCreateResponse(NotificationTemplate template);

    UpdateTemplateResponse toUpdateResponse(NotificationTemplate template);
}
