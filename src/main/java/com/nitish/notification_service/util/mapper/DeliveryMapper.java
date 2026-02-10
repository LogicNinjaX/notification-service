package com.nitish.notification_service.util.mapper;

import com.nitish.notification_service.dto.response.DeliveryResponse;
import com.nitish.notification_service.entity.NotificationDelivery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {

    DeliveryResponse toResponse(NotificationDelivery delivery);
}
