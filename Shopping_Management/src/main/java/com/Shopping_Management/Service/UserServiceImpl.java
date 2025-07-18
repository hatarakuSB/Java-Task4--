package com.Shopping_Management.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Shopping_Management.Model.DTO.UserDTO;
import com.Shopping_Management.Model.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    //勉強用メモ:springframework.securityの機能:パスワードのハッシュ化を可能にする。
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public boolean isUsernameTaken(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public void registerNewUser(UserDTO userForm) {
        User user = new User();
        user.setUsername(userForm.getUsername());
        // ハッシュ化
        user.setPassword(passwordEncoder.encode(userForm.getPassword())); 
        // デフォルト権限
        user.setRole("user"); 
        user.setActive(true);

        userRepository.save(user);
    }
}
