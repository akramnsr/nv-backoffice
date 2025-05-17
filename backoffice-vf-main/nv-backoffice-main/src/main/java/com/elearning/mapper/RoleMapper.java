// src/main/java/com/elearning/mapper/RoleMapper.java
package com.elearning.mapper;

import com.elearning.dto.RoleDto;
import com.elearning.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleDto toDto(Role r) {
        if (r == null) return null;
        Long id = r.getId() != null ? r.getId().longValue() : null;
        return new RoleDto(id, r.getNom());
    }

    public Role toEntity(RoleDto dto) {
        if (dto == null) return null;
        Role r = new Role();
        if (dto.getId() != null) {
            r.setId(dto.getId().intValue());
        }
        r.setNom(dto.getName());
        return r;
    }
}
