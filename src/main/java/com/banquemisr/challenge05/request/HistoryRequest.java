package com.banquemisr.challenge05.request;

import com.banquemisr.challenge05.enums.HistoryAction;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryRequest {

    @NotNull(message = "Action is required")
    private HistoryAction action;

    @NotNull(message = "Action date is required")
    @PastOrPresent(message = "Action date must be in the past or present")
    private LocalDateTime actionDate;
}