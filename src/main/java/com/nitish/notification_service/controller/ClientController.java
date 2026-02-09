package com.nitish.notification_service.controller;

import com.nitish.notification_service.dto.request.ClientUpdateRequest;
import com.nitish.notification_service.dto.response.ApiResponse;
import com.nitish.notification_service.dto.response.ClientUpdateResponse;
import com.nitish.notification_service.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(path = "/client/{clientId}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Void>> updateClientStatus(@PathVariable UUID clientId, @RequestParam String status){
        clientService.updateClientStatus(clientId, status);
        return ResponseEntity.ok(ApiResponse.success("client status updated successfully"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(path = "/client/{clientId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<ClientUpdateResponse>> updateClient(@PathVariable UUID clientId, @Valid @RequestBody ClientUpdateRequest request){
        ClientUpdateResponse response = clientService.updateClientInfo(clientId,request);
        return ResponseEntity.ok(ApiResponse.success(response, "client updated successfully"));
    }
}
