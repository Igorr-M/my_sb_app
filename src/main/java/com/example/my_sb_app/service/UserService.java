package com.example.my_sb_app.service;

import com.example.my_sb_app.entity.Role;
import com.example.my_sb_app.entity.User;
import com.example.my_sb_app.entity.dto.ProfileUpdateResult;
import com.example.my_sb_app.repository.UserRepository;
import com.example.my_sb_app.service.SmptMailSender;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
/*     */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SmptMailSender mailSender;
    @Value("${myhostname}")
    private String hostname;
 
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);
     
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
     
        return (UserDetails)user;
    }
 
    public boolean addUser(User user) {
        User userFromDb = this.userRepository.findByUsername(user.getUsername());
     
        if (userFromDb != null) {
            return false;
        }
     
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
     
        sendMessage(user);
     
        return true;
    }
 
    private void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format("Hello, %s! \nWelcome to MySbApp. Please visit next link: http://%s/activate/%s",
                    new Object[] { user.getUsername(), this.hostname, user.getActivationCode() });
            this.mailSender.send(user.getEmail(), "Activation code", message);
        }
    }
    public boolean activateUserCode(String code) {
        User user = this.userRepository.findByActivationCode(code);
     
        if (user == null) {
            return false;
        }
        user.setActivationCode(null);
        this.userRepository.save(user);
     
        return true;
    }
 
    public List<User> findAll() {
        return this.userRepository.findAll();
    }
 
    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);
        Set<String> roles = (Set<String>)Arrays.<Role>stream(Role.values()).map(Enum::name).collect(Collectors.toSet());
        user.getRoles().clear();
     
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        this.userRepository.save(user);
    }
 
    public ProfileUpdateResult updateProfile(User user, String password, String email) {
        String userEmail = user.getEmail();

        boolean isEmailChanged = ((email != null && !email.equals(userEmail)) || (userEmail != null && !userEmail.equals(email)));
        boolean passwordChanged = !StringUtils.isEmpty(password);  //new

        if (isEmailChanged) {
            user.setEmail(email);
            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }
     
        if (passwordChanged) {
            user.setPassword(this.passwordEncoder.encode(password));
        }
     
        this.userRepository.save(user);
        if (isEmailChanged) {
            sendMessage(user);
        }

        return new ProfileUpdateResult(isEmailChanged, passwordChanged);
    }

    public void subscribe(User currentUser, User user) {
        user.getSubscribers().add(currentUser);
        userRepository.save(user);
    }

    public void unsubscribe(User currentUser, User user) {
        user.getSubscribers().remove(currentUser);
        userRepository.save(user);
    }
}
