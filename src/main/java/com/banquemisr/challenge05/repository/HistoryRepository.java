package com.banquemisr.challenge05.repository;

import com.banquemisr.challenge05.model.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History,Long> {
}
