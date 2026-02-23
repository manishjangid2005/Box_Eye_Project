package com.becoder.user_management_app.Repository;

import com.becoder.user_management_app.Entity.UserDtls;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repo extends JpaRepository<UserDtls, Long> {
    UserDtls findByEmail(String email);
    boolean existsByEmail(String email);
}