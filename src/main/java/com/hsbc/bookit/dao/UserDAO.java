package com.hsbc.bookit.dao;

import com.hsbc.bookit.domain.Users;

import java.util.ArrayList;
import java.util.List;

public interface UserDAO {
    //Basic CRUD for users which will be accessed by the admin through the user service
    public boolean addUsers(Users authenticateduser, Users u);
    public boolean deleteUsers(Users authenticatedUser,String username);
    public int updateUsers(Users authenticatedUser,Users user);
    public List<Users>  findUsers();
    public List<Users> getUsersbyusername(String username);
    void updateUserCredits(Users authenticatedUser,int Credits);
}
