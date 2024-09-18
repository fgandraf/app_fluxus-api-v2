package com.felipegandra.app_fluxusapiv2.modules.users.dtos;

import com.felipegandra.app_fluxusapiv2.modules.users.User;
import com.felipegandra.app_fluxusapiv2.modules.users.enums.UserRole;

public record UserResponse(
        Long id,
        String email,
        Long professionalId,
        Boolean active,
        Boolean legalResponsible,
        Boolean technicianResponsible,
        UserRole role
){
    public UserResponse(User user) {
        this(
                user.getId(),
                user.getEmail(),
                user.getProfessionalId(),
                user.getActive(),
                user.getLegalResponsible(),
                user.getTechnicianResponsible(),
                user.getRole()
        );
    }
}
