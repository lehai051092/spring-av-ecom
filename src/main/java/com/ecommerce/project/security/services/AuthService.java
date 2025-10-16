package com.ecommerce.project.security.services;

import com.ecommerce.project.security.dtos.LoginDTO;
import com.ecommerce.project.security.response.UserInfoResponse;

public interface AuthService {

    UserInfoResponse doLogin(LoginDTO loginDTO);
}
