package com.nitish.notification_service.controller;

import com.nitish.notification_service.dto.response.ApiResponse;
import com.nitish.notification_service.dto.response.MessageResponse;
import com.nitish.notification_service.dto.response.PageResponse;
import com.nitish.notification_service.enums.UserRole;
import com.nitish.notification_service.security.CustomUserDetails;
import com.nitish.notification_service.service.MessageService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','CLIENT','USER')")
    public ResponseEntity<ApiResponse<PageResponse<MessageResponse>>> getMessages(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam UUID requestId,
            @PageableDefault(sort = "createdAt") Pageable pageable
    )
    {

        PageResponse<MessageResponse> response;

        if (user.getRole() == UserRole.ROLE_ADMIN) {
            response = messageService.getMessagesByRequestId(requestId, pageable);
        } else {
            response = messageService.getMessagesByRequestIdAndUserId(
                    requestId,
                    user.getUserId(),
                    pageable
            );
        }

        return ResponseEntity.ok(
                ApiResponse.success(response, "Messages fetched successfully")
        );
    }
}
