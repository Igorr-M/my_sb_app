package com.example.my_sb_app.controller;

import com.example.my_sb_app.entity.Role;
import com.example.my_sb_app.entity.User;
import com.example.my_sb_app.entity.dto.ProfileUpdateResult;
import com.example.my_sb_app.service.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/user"})
public class UserController {
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userList(Model model) {
        model.addAttribute("users", this.userService.findAll());
        return "userList";
        } @Autowired
    private UserService userService;
    @GetMapping({"{user}"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userSave(@RequestParam String username, @RequestParam Map<String, String> form, @RequestParam("userId") User user) {
        this.userService.saveUser(user, username, form);
        return "redirect:/user";
    }

    @GetMapping({"profile"})
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "profile";
    }

//    @PostMapping({"profile"})
//    public String updateProfile(@AuthenticationPrincipal User user,
//                                @RequestParam String password,
//                                @RequestParam String email) {
//        this.userService.updateProfile(user, password, email);
//        return "redirect:/user/profile";
//    }

    @PostMapping("/profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email,
            Model model
    ) {
        ProfileUpdateResult result = userService.updateProfile(user, password, email);

        String message;
        if (result.isEmailChanged() && result.isPasswordChanged()) {
            message = "Profile updated";
        } else if (result.isEmailChanged()) {
            message = "Check new email";
        } else if (result.isPasswordChanged()) {
            message = "Your password updated";
        } else {
            message = "No changes detected";
        }

        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("message", message);

        return "profile";
    }
}
