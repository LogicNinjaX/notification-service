package com.nitish.notification_service.util.mapper;

import com.nitish.notification_service.dto.response.NotificationRequestResponse;
import com.nitish.notification_service.entity.NotificationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationRequestMapper {

    NotificationRequestResponse toResponse(NotificationRequest notificationRequest);
}
