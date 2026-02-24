package com.nitish.notification_service.controller;


import com.nitish.notification_service.controller.doc.RequestApiDoc;
import com.nitish.notification_service.dto.response.ApiResponse;
import com.nitish.notification_service.dto.response.NotificationRequestResponse;
import com.nitish.notification_service.dto.response.PageResponse;
import com.nitish.notification_service.enums.RequestStatus;
import com.nitish.notification_service.security.CustomUserDetails;
import com.nitish.notification_service.service.NotificationRequestService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/requests")
public class NotificationRequestController implements RequestApiDoc {

    private final NotificationRequestService requestService;

    public NotificationRequestController(NotificationRequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping(path = "/my", produces = APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ApiResponse<PageResponse<NotificationRequestResponse>>> getUserNotificationRequests
    (
            @AuthenticationPrincipal CustomUserDetails user,
            @PageableDefault(sort = {"requestedAt"}) Pageable pageable
    )
    {
        var response = requestService.getAllRequestsByUser(user.getUserId(), pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response, "Request details fetched successfully"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(path = "/{requestId}")
    @Override
    public ResponseEntity<Void> updateRequestStatus(@PathVariable UUID requestId, @RequestParam RequestStatus status){
        requestService.updateRequestStatus(requestId, status);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
