package com.banquemisr.challenge05.service.History;

import com.banquemisr.challenge05.enums.HistoryAction;
import com.banquemisr.challenge05.model.History;
import com.banquemisr.challenge05.model.Task;
import com.banquemisr.challenge05.request.HistoryRequest;

import java.util.List;

public interface IHistoryService {

    History getHistoryById(Long historyId) ;

    List<History> getTaskHistoryByTaskId(Long taskId) ;

    Task createHistory(Long taskId, HistoryRequest request);

    Task addHistory(Task task, HistoryAction action);

    History updateHistory(Long HistoryId, HistoryRequest request);

    void deleteHistory(Long HistoryId);

}
