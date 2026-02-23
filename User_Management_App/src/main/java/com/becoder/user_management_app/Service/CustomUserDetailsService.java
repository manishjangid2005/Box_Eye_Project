package com.becoder.user_management_app.Service;

import com.becoder.user_management_app.Entity.UserDtls;
import com.becoder.user_management_app.Repository.Repo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final Repo repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDtls user = repo.findByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Fixed Error: Correctly utilizing SimpleGrantedAuthority
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
}