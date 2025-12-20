package com.hsbc.bookit.services;

public interface UserService {
    public void addUserdata(String id,String username,String password,String name,String email,String phone,String role,int credits);
    public void deleteUserdata(String username);
    public void getAllUsers();
    public void getUsersByUsername(String username);
}
