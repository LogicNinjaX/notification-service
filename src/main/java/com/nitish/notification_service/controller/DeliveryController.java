package com.nitish.notification_service.controller;

import com.nitish.notification_service.controller.doc.DeliveryApiDoc;
import com.nitish.notification_service.dto.response.ApiResponse;
import com.nitish.notification_service.dto.response.DeliveryResponse;
import com.nitish.notification_service.dto.response.PageResponse;
import com.nitish.notification_service.enums.UserRole;
import com.nitish.notification_service.security.CustomUserDetails;
import com.nitish.notification_service.service.DeliveryService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/deliveries")
public class DeliveryController implements DeliveryApiDoc {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping(value = "/{messageId}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Override
    public ResponseEntity<ApiResponse<DeliveryResponse>> getDeliveryByMessageId(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable UUID messageId
    )
    {
        DeliveryResponse response = deliveryService.getDeliveryByMessageId(messageId);

        return ResponseEntity.ok(
                ApiResponse.success(response, "Delivery fetched successfully")
        );
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT','USER')")
    @Override
    public ResponseEntity<ApiResponse<PageResponse<DeliveryResponse>>> getDeliveries(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam UUID requestId,
            @PageableDefault(sort = "sentAt") Pageable pageable
    )
    {

        PageResponse<DeliveryResponse> response;

        if (user.getRole() == UserRole.ROLE_ADMIN) {
            response = deliveryService.findDeliveriesByRequestId(requestId, pageable);
        } else {
            // normal user sees only their deliveries
            response = deliveryService.findDeliveriesByUserIdAndRequestId(
                    user.getUserId(), requestId, pageable
            );
        }

        return ResponseEntity.ok(
                ApiResponse.success(response, "Deliveries fetched successfully")
        );
    }
}
