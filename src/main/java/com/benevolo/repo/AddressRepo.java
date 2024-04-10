package com.benevolo.repo;

import com.benevolo.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<AddressEntity, String> {
}
