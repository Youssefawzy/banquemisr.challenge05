package com.banquemisr.challenge05.controller;

import com.banquemisr.challenge05.exception.UserAlreadyExistsException;
import com.banquemisr.challenge05.exception.UserNotFoundException;
import com.banquemisr.challenge05.model.User;
import com.banquemisr.challenge05.request.SignUpRequest;
import com.banquemisr.challenge05.request.UpdateUserRequest;
import com.banquemisr.challenge05.request.UserDto;
import com.banquemisr.challenge05.service.User.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.status(HttpStatus.OK).body(userDto); // Return OK status
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(null); // Return NOT_FOUND without ApiResponse
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> signUp(@Valid @RequestBody SignUpRequest request) {
        try {
            User user = userService.signUp(request); // SignUp logic
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDto); // Return CREATED status for successful signup
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(null); // Return CONFLICT if email already exists
        }
    }


    @PostMapping("/add")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto request) {
        try {
            User user = userService.createUser(request);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.status(HttpStatus.OK).body(userDto); // Return OK status
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(null); // Return NOT_FOUND without ApiResponse
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser( @Valid @RequestBody UpdateUserRequest request,
                                                  @PathVariable Long userId) {
        try {
            User user = userService.updateUser(request, userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.status(HttpStatus.OK).body(userDto); // Return OK status
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(null); // Return NOT_FOUND without ApiResponse
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("Delete User Success!");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
    }

}
