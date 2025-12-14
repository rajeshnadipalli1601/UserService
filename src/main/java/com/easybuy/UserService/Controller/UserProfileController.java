package com.easybuy.UserService.Controller;

import com.easybuy.UserService.Entity.UserSignup;
import com.easybuy.UserService.Service.UserService;
import com.easybuy.UserService.Exception.UserServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/profile")
public class UserProfileController {

    private final UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/viewprofile/{id}")
    public ResponseEntity<UserSignup> viewProfile(@PathVariable("id") Long id,
                                                  Authentication authentication) {
        UserSignup userProfile = userService.viewProfile(id);

        if (!userProfile.getEmail().equals(authentication.getName())) {
            throw new UserServiceException("FORBIDDEN", "You are not allowed to access this profile");
        }
        return ResponseEntity.ok(userProfile);
    }

    @PutMapping("/updateprofile/{id}")
    public ResponseEntity<UserSignup> updateProfile(@PathVariable("id") Long id,
                                                    @RequestBody UserSignup updatedUser,
                                                    Authentication authentication) {
        UserSignup existingUser = userService.viewProfile(id);

        if (!existingUser.getEmail().equals(authentication.getName())) {
            throw new UserServiceException("FORBIDDEN", "You are not allowed to update this profile");
        }
        UserSignup updatedProfile = userService.updateProfile(id, updatedUser);
        return ResponseEntity.ok(updatedProfile);
    }
}
