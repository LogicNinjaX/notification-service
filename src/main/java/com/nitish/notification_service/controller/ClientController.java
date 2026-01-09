package com.nitish.notification_service.controller;

import com.nitish.notification_service.dto.request.ClientRegisterRequest;
import com.nitish.notification_service.dto.request.ClientUpdateRequest;
import com.nitish.notification_service.dto.response.ApiResponse;
import com.nitish.notification_service.dto.response.ClientRegisterResponse;
import com.nitish.notification_service.dto.response.ClientUpdateResponse;
import com.nitish.notification_service.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping(path = "/client", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<ClientRegisterResponse>> registerClient(@RequestBody @Valid ClientRegisterRequest request){
        ClientRegisterResponse savedClient = clientService.registerClient(request);
        return ResponseEntity.ok(ApiResponse.success(savedClient, "client registered successfully"));
    }

    @PatchMapping(path = "/client/{clientId}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Void>> updateClientStatus(@PathVariable UUID clientId, @RequestParam String status){
        clientService.updateClientStatus(clientId, status);
        return ResponseEntity.ok(ApiResponse.success("client status updated successfully"));
    }

    @PatchMapping(path = "/client/{clientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<ClientUpdateResponse>> updateClient(@PathVariable UUID clientId, @Valid @RequestBody ClientUpdateRequest request){
        ClientUpdateResponse response = clientService.updateClientInfo(clientId,request);
        return ResponseEntity.ok(ApiResponse.success(response, "client updated successfully"));
    }
}
