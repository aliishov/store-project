package com.raul.user_service.services;

import com.raul.user_service.dto.AppUserRequestDto;
import com.raul.user_service.dto.AppUserResponseDto;
import com.raul.user_service.models.AppUser;
import org.springframework.stereotype.Service;

@Service
public class AppUserConverter {
    public AppUser convertToAppUser(AppUserRequestDto request) {

        return AppUser.builder()
                .id(request.id())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
    }

    public AppUserResponseDto convertToAppUserResponse(AppUser appUser) {

        return new AppUserResponseDto(
                appUser.getId(),
                appUser.getFirstName(),
                appUser.getLastName(),
                appUser.getEmail()
        );
    }
}
