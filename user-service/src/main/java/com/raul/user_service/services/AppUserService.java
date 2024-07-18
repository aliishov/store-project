package com.raul.user_service.services;

import com.raul.user_service.dto.AppUserRequestDto;
import com.raul.user_service.dto.AppUserResponseDto;
import com.raul.user_service.models.AppUser;
import com.raul.user_service.repositories.AppUserRepository;
import com.raul.user_service.util.exception.AppUserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository repository;

    private final AppUserConverter converter;
    public ResponseEntity<AppUserResponseDto> createAppUser(AppUserRequestDto request) {
        var appUser = repository.save(converter.convertToAppUser(request));
        return new ResponseEntity<>(converter.convertToAppUserResponse(appUser), HttpStatus.OK);
    }

    public ResponseEntity<Void> updateAppUser(AppUserRequestDto request) {
        var appUser = repository.findById(request.id())
                .orElseThrow(() -> new AppUserNotFoundException("Cannot update User information. " +
                                                                "User with ID: " + request.id() + " not found"));
        updateAppUserFromRequest(appUser,request);
        repository.save(appUser);

        return ResponseEntity.accepted().build();
    }

    private void updateAppUserFromRequest(AppUser appUser, AppUserRequestDto request) {

        if (StringUtils.isNotBlank(request.firstName())) {
            appUser.setFirstName(request.firstName());
        }
        if (StringUtils.isNotBlank(request.lastName())) {
            appUser.setLastName(request.lastName());
        }
        if (StringUtils.isNotBlank(request.email())) {
            appUser.setEmail(request.email());
        }
    }

    public ResponseEntity<List<AppUserResponseDto>> getAll() {
        var appUsers = repository.findAll().stream()
                .map(converter::convertToAppUserResponse).collect(Collectors.toList());
        if (appUsers.isEmpty())
            return ResponseEntity.notFound().build();

        return new ResponseEntity<>(appUsers, HttpStatus.OK);
    }

    public ResponseEntity<AppUserResponseDto> getAppUserById(Integer appUserId) {
        var appUser = repository.findById(appUserId).
                orElseThrow(() -> new AppUserNotFoundException("User with ID: " + appUserId + " not found"));

        return new ResponseEntity<>(converter.convertToAppUserResponse(appUser), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteAppUser(Integer appUserId) {
        repository.deleteById(appUserId);

        return ResponseEntity.accepted().build();
    }
}
