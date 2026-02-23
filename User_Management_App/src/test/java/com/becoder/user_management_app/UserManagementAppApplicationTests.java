package com.becoder.user_management_app.Service;

import com.becoder.user_management_app.Entity.UserDtls;
import com.becoder.user_management_app.Repository.Repo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Repo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDtls createUser(UserDtls userDtls) {
        userDtls.setPassword(passwordEncoder.encode(userDtls.getPassword()));
        userDtls.setRole("USER");
        return userRepo.save(userDtls);
    }

    @Override
    public boolean checkEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public UserDtls findByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}