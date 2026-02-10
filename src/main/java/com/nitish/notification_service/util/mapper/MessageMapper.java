package com.nitish.notification_service.util.mapper;

import com.nitish.notification_service.dto.response.MessageResponse;
import com.nitish.notification_service.entity.NotificationMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageResponse toResponse(NotificationMessage message);
}
