package rs.raf.userservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import rs.raf.userservice.service.BanService;

@Tag(name = "BanController", description = "Controller for managing banned IP addresses")
@RestController
@RequestMapping("/api/v1/bans")
public class BanController {
    private final BanService service;

    public BanController(BanService service) {
        this.service = service;
    }

    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping
    public ResponseEntity<List<String>> getAll() {
        var response = service.getAll();
        return ResponseEntity.ok(response);
    }

    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @Parameter(name = "ip", description = "IP address to ban", required = true)
    @PostMapping
    public ResponseEntity<Void> ban(@RequestParam String ip) {
        service.ban(ip);
        return ResponseEntity.noContent().build();
    }

    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @Parameter(name = "ip", description = "IP address to unban", required = true)
    @DeleteMapping
    public ResponseEntity<Void> unban(@RequestParam String ip) {
        service.unban(ip);
        return ResponseEntity.noContent().build();
    }
}
