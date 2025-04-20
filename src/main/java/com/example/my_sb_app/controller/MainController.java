package com.example.my_sb_app.controller;

import com.example.my_sb_app.controller.ControllerUtils;
import com.example.my_sb_app.entity.Message;
import com.example.my_sb_app.entity.User;
import com.example.my_sb_app.repository.MessageRepository;
import jakarta.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {
    @Autowired
    private MessageRepository messageRepository;
    @Value("${upload.path}")
    private String uploadPath;
 
    @GetMapping({"/"})
    public String greeting(Model model) {
        return "greeting";
    }
 
    @GetMapping({"/main"})
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Message> messages = this.messageRepository.findAll();
        if (filter != null && !filter.isEmpty()) {
            messages = this.messageRepository.findByTag(filter);
        }
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "main";
    }
 
    @PostMapping({"/main"})
    public String addMessage(@AuthenticationPrincipal User user, @Valid Message message, BindingResult bindingResult, Model model, @RequestParam("file") MultipartFile file) throws IOException {
        message.setAuthor(user);
     
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        } else {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(this.uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + uuidFile;
                file.transferTo(new File(this.uploadPath + "/" + this.uploadPath));
             
                message.setFilename(resultFilename);
                    }
                model.addAttribute("message", null);
                this.messageRepository.save(message);
            }
     
        Iterable<Message> messages = this.messageRepository.findAll();
        model.addAttribute("messages", messages);
        return "main";
    }
    
}
