package com.benevolo.repo;

import com.benevolo.entity.AddressEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AddressRepo implements PanacheRepositoryBase<AddressEntity, String> {
}
