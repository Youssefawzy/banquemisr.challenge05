package com.banquemisr.challenge05.controller;

import com.banquemisr.challenge05.model.History;
import com.banquemisr.challenge05.model.Task;
import com.banquemisr.challenge05.request.HistoryRequest;
import com.banquemisr.challenge05.service.History.IHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/history")
@RequiredArgsConstructor
public class HistoryController {
    private final IHistoryService historyService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{taskId}")
    public ResponseEntity<Task> CreateHistory(@PathVariable Long taskId,
                                              @RequestBody HistoryRequest historyRequest) {
        Task task = historyService.createHistory(taskId, historyRequest);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{historyId}")
    public ResponseEntity<History> getHistoryById(@PathVariable Long historyId) {
        History history = historyService.getHistoryById(historyId);
        return ResponseEntity.ok(history);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{historyId}")
    public ResponseEntity<History> updateHistory(@PathVariable Long historyId,
                                                 @RequestBody HistoryRequest request) {
        History updatedHistory = historyService.updateHistory(historyId, request);
        return ResponseEntity.ok(updatedHistory);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{historyId}")
    public ResponseEntity<Void> deleteHistory(@PathVariable Long historyId) {
        historyService.deleteHistory(historyId);
        return ResponseEntity.noContent().build();
    }
}
