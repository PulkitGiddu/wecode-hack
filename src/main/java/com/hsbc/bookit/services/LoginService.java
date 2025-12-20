package com.hsbc.bookit.services;

import com.hsbc.bookit.domain.Users;

public interface LoginService {
    public Users login(String username, String password);
    public void resetCredits(Users user);
}
