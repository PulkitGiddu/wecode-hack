package com.wecode.bookit.dao;

import com.wecode.bookit.model.Users;

public interface LoginDAO {
    Users authenticate(String username, String password);
    void resetCredits(Users user);
}
