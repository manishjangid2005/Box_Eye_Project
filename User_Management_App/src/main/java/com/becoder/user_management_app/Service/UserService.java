package com.becoder.user_management_app.Service;

import com.becoder.user_management_app.Entity.UserDtls;

public interface UserService {
    UserDtls createUser(UserDtls userDtls);
    boolean checkEmail(String email);
    UserDtls findByEmail(String email);
}