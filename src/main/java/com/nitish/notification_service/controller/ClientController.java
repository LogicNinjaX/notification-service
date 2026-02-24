package com.nitish.notification_service.controller;

import com.nitish.notification_service.controller.doc.ClientApiDoc;
import com.nitish.notification_service.dto.request.ClientUpdateRequest;
import com.nitish.notification_service.dto.response.ApiResponse;
import com.nitish.notification_service.dto.response.ClientUpdateResponse;
import com.nitish.notification_service.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ClientController implements ClientApiDoc {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(path = "/clients/{clientId}/status", produces = APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ApiResponse<Void>> updateClientStatus(@PathVariable UUID clientId, @RequestParam String status){
        clientService.updateClientStatus(clientId, status);
        return ResponseEntity.ok(ApiResponse.success("client status updated successfully"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(path = "/clients/{clientId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ApiResponse<ClientUpdateResponse>> updateClient(@PathVariable UUID clientId, @Valid @RequestBody ClientUpdateRequest request){
        ClientUpdateResponse response = clientService.updateClientInfo(clientId,request);
        return ResponseEntity.ok(ApiResponse.success(response, "client updated successfully"));
    }
}
