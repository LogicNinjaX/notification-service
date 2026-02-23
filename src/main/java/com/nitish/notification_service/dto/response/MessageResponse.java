package com.nitish.notification_service.dto.response;

import com.nitish.notification_service.enums.MessageStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Notification message details")
public record MessageResponse
        (
                @Schema(example = "550e8400-e29b-41d4-a716-446655440000")
                UUID messageId,

                @Schema(example = "nitish@gmail.com")
                String recipient,

                @Schema(example = "Welcome to our platform")
                String renderedSubject,

                @Schema(description = "Final rendered message content after template variable replacement",
                        example = "Hello xyz, welcome!")
                String renderedContent,

                @Schema(example = "SENT")
                MessageStatus status,

                @Schema(description = "Number of retry attempts",
                        example = "0")
                int retryCount,

                @Schema(example = "2026-02-23T20:50:00")
                LocalDateTime createdAt,

                @Schema(example = "2026-02-23T21:00:00")
                LocalDateTime updatedAt
        )
{ }
