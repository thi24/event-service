package com.benevolo.mapper;

import com.benevolo.dto.AddressDTO;
import com.benevolo.dto.EventDTO;
import com.benevolo.entity.AddressEntity;
import com.benevolo.entity.EventEntity;

import java.util.List;

public class AddressMapper {


    public static AddressDTO map(AddressEntity address) {
        return new AddressDTO(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZip()
        );
    }

    public static AddressEntity map(AddressDTO addressDTO) {
        return new AddressEntity(
                addressDTO.street(),
                addressDTO.city(),
                addressDTO.state(),
                addressDTO.zip(),
                null
        );
    }
}
