package com.nitish.notification_service.controller;

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

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/{messageId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<DeliveryResponse>> getDeliveryByMessageId(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable UUID messageId
    )
    {
        DeliveryResponse response = deliveryService.getDeliveryByMessageId(messageId);

        // optional ownership check for non-admin
//        if (!(user.getRole() == UserRole.ROLE_ADMIN) && !response.getUserId().equals(user.getUserId())) {
//            throw new AccessDeniedException("Not allowed to access this delivery");
//        }

        return ResponseEntity.ok(
                ApiResponse.success(response, "Delivery fetched successfully")
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT','USER')")
    public ResponseEntity<ApiResponse<PageResponse<DeliveryResponse>>> getDeliveries(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam UUID requestId,
            @PageableDefault(sort = "createdAt") Pageable pageable
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
