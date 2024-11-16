package com.banquemisr.challenge05.service.User;

import com.banquemisr.challenge05.exception.*;
import com.banquemisr.challenge05.model.Role;
import com.banquemisr.challenge05.model.Task;
import com.banquemisr.challenge05.model.User;
import com.banquemisr.challenge05.repository.RoleRepository;
import com.banquemisr.challenge05.repository.TaskRepository;
import com.banquemisr.challenge05.repository.UserRepository;
import com.banquemisr.challenge05.request.SignUpRequest;
import com.banquemisr.challenge05.request.UpdateUserRequest;
import com.banquemisr.challenge05.request.UserDto;
import com.banquemisr.challenge05.service.Email.EmailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    @Override
    public User signUp(SignUpRequest request) {

        List<Role> roles = List.of(
                roleRepository.findByName("ROLE_USER")
                        .orElseThrow(() -> new IllegalArgumentException("Role not found: ROLE_USER"))
        );

        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setUsername(request.getUsername());
                    user.setRoles(new HashSet<>(roles));  // Assign only ROLE_USER for signup


                    String subject = "Welcome to the Task Management System!";
                    String body = "Dear " + user.getUsername() + ",\n\n" +
                            "Congratulations on successfully signing up to our system.\n\n" +
                            "Best Regards,\nTask Management System Team";
                    emailService.sendEmail(user.getEmail(), subject, body); // Send email


                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UserAlreadyExistsException("Oops! " + request.getEmail() + " already exists!", 406));
    }


    @Override
    public User createUser(UserDto request) {

        List<Role> roles = request.getRoles().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName)))
                .toList();

        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setUsername(request.getUsername());
                    user.setRoles(new HashSet<>(roles));  // Set the roles to the user (many roles)

                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UserAlreadyExistsException("Oops! " + request.getEmail() + " already exists!", 406));
    }



    @Override
    public User updateUser(UpdateUserRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setUsername(request.getUsername());

            return userRepository.save(existingUser);
        }).orElseThrow(() -> new UserNotFoundException("User not found!", 404));

    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> {
            throw new UserNotFoundException("User not found!", 404);
        });
    }

    @Override
    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User getAuthenticatedUser() {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
                throw new UserAuthenticationException("User is not authenticated", HttpStatus.UNAUTHORIZED.value());
            }

            String email = authentication.getName();

            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserAuthenticationException("Authenticated user not found with email: " + email, HttpStatus.NOT_FOUND.value()));

        } catch (UserAuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new UserAuthenticationException("Failed to retrieve authenticated user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }


    public void checkTaskOwnership(Long taskId, User currentUser)  {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task with ID " + taskId + " not found", 404));


        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new TaskOwnerShipException("You can only manage your own tasks.", HttpStatus.FORBIDDEN.value());
        }

    }
}