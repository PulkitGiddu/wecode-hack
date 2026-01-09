package com.wecode.bookit.services;

import com.wecode.bookit.dto.UserDto;
import com.wecode.bookit.entity.User;

public interface UserService {
    User signUp(UserDto userDto);
    User login(UserDto userDto);

}

