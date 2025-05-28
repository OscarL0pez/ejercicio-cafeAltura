package com.cafeteria.cafedealtura.util;

import com.cafeteria.cafedealtura.dto.CafeDTO;
import com.cafeteria.cafedealtura.dto.CafeCreateDTO;
import com.cafeteria.cafedealtura.dto.CafeUpdateDTO;
import com.cafeteria.cafedealtura.model.Cafe;

public class CafeMapper {

    public static CafeDTO toDTO(Cafe cafe) {
        CafeDTO dto = new CafeDTO();
        dto.setId(cafe.getId());
        dto.setName(cafe.getName());
        dto.setDescription(cafe.getDescription());
        dto.setPrice(cafe.getPrice());
        dto.setOrigin(cafe.getOrigin());
        return dto;
    }

    public static Cafe fromCreateDTO(CafeCreateDTO dto) {
        Cafe cafe = new Cafe();
        cafe.setName(dto.getName());
        cafe.setDescription(dto.getDescription());
        cafe.setPrice(dto.getPrice());
        cafe.setOrigin(dto.getOrigin());
        return cafe;
    }

    public static Cafe fromUpdateDTO(CafeUpdateDTO dto) {
        Cafe cafe = new Cafe();
        cafe.setName(dto.getName());
        cafe.setDescription(dto.getDescription());
        cafe.setPrice(dto.getPrice());
        cafe.setOrigin(dto.getOrigin());
        return cafe;
    }

}