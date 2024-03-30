package com.benevolo.repo;

import com.benevolo.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<EventEntity, String> {
}
