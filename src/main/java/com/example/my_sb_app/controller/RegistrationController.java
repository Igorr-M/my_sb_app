package com.example.my_sb_app.controller;

import com.example.my_sb_app.controller.ControllerUtils;
import com.example.my_sb_app.entity.User;
import com.example.my_sb_app.entity.dto.CaptchaResponseDto;
import com.example.my_sb_app.service.UserService;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class RegistrationController {
    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    @Autowired
    private UserService userService;
    @Value("${recaptcha.secret}")
    private String secret;
    @Autowired
    private RestTemplate restTemplate;
 
    @GetMapping({"/registration"})
    public String registration() {
        return "registration";
    }
 
    @PostMapping({"/registration"})
    public String addUser(@RequestParam("password2") String passwordConfirm, @RequestParam("g-recaptcha-response") String captchaResponse, @Valid User user, BindingResult bindingResult, Model model) {
        String url = String.format("https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s", new Object[] { this.secret, captchaResponse });
        CaptchaResponseDto response = (CaptchaResponseDto)this.restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class, new Object[0]);
     
        if (!response.isSuccess()) {
            model.addAttribute("captchaError", "Fill captcha");
        }
     
        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);
        if (isConfirmEmpty) {
            model.addAttribute("password2Error", "Password confirmation cannot be empty");
        }
     
        if (user.getPassword() != null && !user.getPassword().equals(passwordConfirm)) {
            model.addAttribute("passwordError", "Passwords are different!");
            return "registration";
        }
     
        if (isConfirmEmpty || bindingResult.hasErrors() || !response.isSuccess()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "registration";
        }
     
        if (!this.userService.addUser(user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }
     
        return "redirect:/login";
    }
 
    @GetMapping({"/activate/{code}"})
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = this.userService.activateUserCode(code);
     
        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated!");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code is not found!");
        }
     
        return "login";
    }
}
