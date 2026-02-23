package com.nitish.notification_service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "Standard API response wrapper")
public class ApiResponse<T> {

    @Schema(description = "Indicates whether the request was successful", example = "true")
    private final boolean success;

    @Schema(description = "Human-readable response message", example = "User created successfully")
    private final String message;

    @Schema(description = "Response payload (present only for successful requests)")
    private final T data;

    @Schema(description = "Validation or business errors (present only when request fails)",
            example = "{\"email\":\"Email already exists\"}")
    private final Map<String, String> error;

    @Schema(description = "Timestamp when the response was generated",
            example = "2026-02-23T21:15:30")
    private final LocalDateTime timeStamp;

    ApiResponse(Builder<T> builder){
        this.success = builder.success;
        this.message = builder.message;
        this.data = builder.data;
        this.error = builder.error;
        this.timeStamp = builder.timeStamp;
    }

    public static class Builder<T>{
        private boolean success;
        private String message;
        private T data;
        private Map<String, String> error;
        private LocalDateTime timeStamp;

        public Builder<T> success(boolean success){
            this.success = success;
            return this;
        }

        public Builder<T> message(String message){
            this.message = message;
            return this;
        }

        public Builder<T> data(T data){
            this.data = data;
            return this;
        }

        public Builder<T> error(Map<String, String> error){
            this.error = error;
            return this;
        }

        public Builder<T> timeStamp(LocalDateTime timeStamp){
            this.timeStamp = timeStamp;
            return this;
        }

        public ApiResponse<T> build(){
            return new ApiResponse<>(this);
        }
    }


    public static <T> ApiResponse<T> success(T data, String message){
        return new Builder<T>()
                .success(true)
                .message(message)
                .data(data)
                .timeStamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> success(String message){
        return new Builder<T>()
                .success(true)
                .message(message)
                .timeStamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> error(String message, Map<String, String> error){
        return new Builder<T>()
                .success(false)
                .message(message)
                .error(error)
                .timeStamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> error(Map<String, String> errorMessage){
        return new Builder<T>()
                .success(false)
                .error(errorMessage)
                .timeStamp(LocalDateTime.now())
                .build();
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public Map<String, String> getError() {
        return error;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
