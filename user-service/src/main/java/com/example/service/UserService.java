package com.example.service;

import com.example.entity.UserEntity;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    final private UserRepository userRepository;

    public UserEntity register(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity getByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public UserEntity getById(Integer threadLocalId) {
        return userRepository.findById(threadLocalId).orElse(null);
    }

    public void deleteById(int id){
        userRepository.deleteById(id);
    }
}
