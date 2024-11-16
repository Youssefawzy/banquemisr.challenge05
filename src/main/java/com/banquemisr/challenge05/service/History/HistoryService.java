package com.banquemisr.challenge05.service.History;

import com.banquemisr.challenge05.enums.HistoryAction;
import com.banquemisr.challenge05.exception.HistoryNotFoundException;
import com.banquemisr.challenge05.model.History;
import com.banquemisr.challenge05.model.Task;
import com.banquemisr.challenge05.model.User;
import com.banquemisr.challenge05.repository.HistoryRepository;
import com.banquemisr.challenge05.repository.TaskRepository;
import com.banquemisr.challenge05.request.HistoryRequest;
import com.banquemisr.challenge05.service.User.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService implements IHistoryService {

    private final HistoryRepository historyRepository;
    private final TaskRepository taskRepository;
    private final IUserService userService;


    @Override
    @Transactional
    public Task createHistory(Long taskId, HistoryRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new HistoryNotFoundException("Task with ID " + taskId + " not found", 404));

        User currentUser = userService.getAuthenticatedUser();
        History history = new History();
        history.setAction(request.getAction() != null ? request.getAction() : HistoryAction.CREATED);
        history.setActionDate(request.getActionDate() != null ? request.getActionDate() : LocalDateTime.now());
        history.setTask(task);
        history.setUser(currentUser);

        task.getHistory().add(history);

        taskRepository.save(task);

        return task;
    }

    @Override
    @Transactional(readOnly = true)
    public History getHistoryById(Long historyId) {
        return historyRepository.findById(historyId)
                .orElseThrow(() -> new HistoryNotFoundException("History with ID " + historyId + " not found", 404));
    }

    @Override
    @Transactional(readOnly = true)
    public List<History> getTaskHistoryByTaskId(Long taskId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new HistoryNotFoundException("Task with ID " + taskId + " not found", 404));

        return task.getHistory();
    }

    @Override
    public Task addHistory(Task task, HistoryAction action) {

        History history = new History();
        history.setAction(action);
        history.setActionDate(LocalDateTime.now());
        history.setTask(task);
        task.getHistory().add(history);

        return task;
    }

    @Override
    public History updateHistory(Long historyId, HistoryRequest request) {

        History existingHistory = historyRepository.findById(historyId)
                .orElseThrow(() -> new HistoryNotFoundException("History with ID " + historyId + " not found", 404));

        if (request.getAction() != null) {
            existingHistory.setAction(request.getAction());
        }
        if (request.getActionDate() != null) {
            existingHistory.setActionDate(request.getActionDate());
        }

        historyRepository.save(existingHistory);

        return existingHistory;
    }

    @Override
    @Transactional
    public void deleteHistory(Long historyId) {
        History history = historyRepository.findById(historyId)
                .orElseThrow(() -> new HistoryNotFoundException("History with ID " + historyId + " not found", 404));

        historyRepository.delete(history);
    }

}
