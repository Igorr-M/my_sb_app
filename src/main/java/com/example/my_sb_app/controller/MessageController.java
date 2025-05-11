package com.example.my_sb_app.controller;

import com.example.my_sb_app.entity.Message;
import com.example.my_sb_app.entity.User;
import com.example.my_sb_app.repository.MessageRepository;
import com.example.my_sb_app.repository.UserRepository;
import com.example.my_sb_app.service.MessageService;
import jakarta.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageService messageService;
    @Value("${upload.path}")
    private String uploadPath;
 
    @GetMapping({"/"})
    public String greeting(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        return "greeting";
    }
 
    @GetMapping({"/main"})
    public String main(@RequestParam(required = false, defaultValue = "") String filter,
                       Model model,
                       @AuthenticationPrincipal User user,
                       @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 6) Pageable pageable)
    {
        Page<Message> page = messageService.messageList(pageable, filter);

        model.addAttribute("page", page);
        model.addAttribute("url", "/main");
        model.addAttribute("filter", filter);
        model.addAttribute("user", user);
        return "main";
    }
 
    @PostMapping({"/main"})
    public String addMessage(@AuthenticationPrincipal User user,
                             @Valid Message message,
                             BindingResult bindingResult,
                             Model model,
                             @RequestParam("file") MultipartFile file,
                             @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 6) Pageable pageable) throws IOException
    {
        message.setAuthor(user);
     
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        } else {
            saveFile(message, file);
            model.addAttribute("message", null);
                this.messageRepository.save(message);
            }

        Page<Message> page = this.messageService.messageList(pageable, "");
        model.addAttribute("page", page);
        model.addAttribute("url", "/main");
        model.addAttribute("user", user);
        return "main";
    }

    private void saveFile(Message message, MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(this.uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + uuidFile;
                file.transferTo(new File(this.uploadPath + "/" + resultFilename));

                message.setFilename(resultFilename);
            }
    }

    @GetMapping("/user-messages/{author}")
    public String userMessages(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User author,
            Model model,
            @RequestParam(required = false) Message message,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 6) Pageable pageable
    ) {
        Page<Message> page = messageService.messageListForUser(pageable, currentUser, author);

        model.addAttribute("userChannel", author);
        model.addAttribute("subscriptionsCount", author.getSubscriptions().size());
        model.addAttribute("subscribersCount", author.getSubscribers().size());
        model.addAttribute("page", page);
        model.addAttribute("message", message);
        model.addAttribute("isCurrentUser", currentUser.equals(author));
        model.addAttribute("isSubscriber", author.getSubscribers().contains(currentUser));
        model.addAttribute("isEditMode", message != null);
        model.addAttribute("url", "/user-messages/" + author.getId());
        model.addAttribute("user", author);
        return "userMessages";
    }

    @PostMapping("/user-messages/{user}")
    public String updateMessage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long user,
            @RequestParam(value = "id", required = false) Message message,
            @RequestParam("text") String text,
            @RequestParam("tag") String tag,
            @RequestParam("file") MultipartFile file,
            Model model
    ) throws IOException {

        if(message.getAuthor().equals(currentUser)){
            if(!StringUtils.isEmpty(text)) {
                message.setText(text);
            }
            if(!StringUtils.isEmpty(tag)) {
                message.setTag(tag);
            }
            saveFile(message, file);
            messageRepository.save(message);
        }
        model.addAttribute("user", currentUser);
        return "redirect:/user-messages/" + user;
    }

    @PostMapping({"/user-messages/{user}/add"})
    public String addMessageForUser(@AuthenticationPrincipal User currentUser,
                             @PathVariable("user") Long userId,
                             @Valid Message message,
                             BindingResult bindingResult,
                             Model model,
                             @RequestParam("file") MultipartFile file,
                             @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 6) Pageable pageable) throws IOException
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        message.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        } else {
            saveFile(message, file);
            model.addAttribute("message", null);
            this.messageRepository.save(message);
        }

        Page<Message> page = this.messageService.messageList(pageable, "");
        model.addAttribute("page", page);
        model.addAttribute("url", "/main");
        model.addAttribute("user", currentUser);
        return "redirect:/user-messages/" + userId;
    }
    
}
