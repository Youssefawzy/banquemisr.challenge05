package com.banquemisr.challenge05.repository;

import com.banquemisr.challenge05.model.Notification;
import com.banquemisr.challenge05.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface NotificationRepository extends JpaRepository<Notification,Long> {

    List<Notification> findByTask( Task task);

}
