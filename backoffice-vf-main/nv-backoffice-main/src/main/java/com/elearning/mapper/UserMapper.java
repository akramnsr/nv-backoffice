// src/main/java/com/elearning/mapper/UserMapper.java
package com.elearning.mapper;

import com.elearning.dto.UserDto;
import com.elearning.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User u) {
        UserDto dto = new UserDto();
        dto.setId(u.getId());
        dto.setNom(u.getNom());
        dto.setPrenom(u.getPrenom());
        dto.setEmail(u.getEmail());
        dto.setDateInscription(u.getDateInscription());
        dto.setRoleName(u.getRole().getNom());
        return dto;
    }

    public User toEntity(UserDto dto) {
        User u = new User();
        u.setId(dto.getId());
        u.setNom(dto.getNom());
        u.setPrenom(dto.getPrenom());
        u.setEmail(dto.getEmail());
        u.setDateInscription(dto.getDateInscription());
        // role lookup by name can be added later
        return u;
    }
}
