package com.banquemisr.challenge05.service.User;

import com.banquemisr.challenge05.model.User;
import com.banquemisr.challenge05.request.SignUpRequest;
import com.banquemisr.challenge05.request.UpdateUserRequest;
import com.banquemisr.challenge05.request.UserDto;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(UserDto request);
    User updateUser(UpdateUserRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();

    void checkTaskOwnership(Long taskId, User currentUser);
     User signUp(SignUpRequest request);
}