package com.banquemisr.challenge05.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.banquemisr.challenge05.enums.HistoryAction;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    @NotNull(message = "Task is required")
    @JsonIgnore
    private Task task;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Action is required")
    private HistoryAction action;

    @NotNull(message = "Action date is required")
    private LocalDateTime actionDate;
}
