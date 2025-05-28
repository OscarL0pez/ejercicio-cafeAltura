package com.cafeteria.cafedealtura.util;

import com.cafeteria.cafedealtura.model.User;
import com.cafeteria.cafedealtura.dto.UserResponseDTO;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toList()));
    }
}