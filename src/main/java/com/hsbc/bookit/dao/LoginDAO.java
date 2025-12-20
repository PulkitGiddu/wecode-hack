package com.hsbc.bookit.dao;

import com.hsbc.bookit.domain.Users;

public interface LoginDAO {
    Users authenticate(String username, String password);
    void resetCredits(Users user);
}
