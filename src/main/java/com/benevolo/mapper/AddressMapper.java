package com.benevolo.mapper;

import com.benevolo.dto.AddressDTO;
import com.benevolo.entity.AddressEntity;

public class AddressMapper {
    private AddressMapper() {
        // private constructor
    }
    public static AddressDTO map(AddressEntity address) {
        return new AddressDTO(address.getId(), address.getStreet(), address.getCity(), address.getState(), address.getZip());
    }

    public static AddressEntity map(AddressDTO addressDTO) {
        return new AddressEntity(addressDTO.street(), addressDTO.city(), addressDTO.state(), addressDTO.zip(), null);
    }
}
