package com.company.libmanagementlogin.service;

import com.company.libmanagementlogin.entity.User;

/**
 * @Description:
 * @author loda
 * @date 2023/10/22/
 */
public interface IUserService {
    boolean addUser(User user, boolean isAdmin);

    User getUser(String name);

    boolean checkUserName(String name);
}