package com.company.libmanagementlogin.service.impl;


import com.company.libmanagementlogin.entity.User;
import com.company.libmanagementlogin.enums.RoleEnum;
import com.company.libmanagementlogin.mapper.UserMapper;
import com.company.libmanagementlogin.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:
 * @author loda
 * @date 2023/10/22/
 */
@Service
public class UserService implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public boolean addUser(User user, boolean isAdmin){
        userMapper.addUser(user);
        if(isAdmin){
            userMapper.addUserRole(user.getUserId(), RoleEnum.ADMIN.getValue());
        }
        return true;
    }

    @Override
    public User getUser(String name){
        return userMapper.getUser(name);
    }

    public boolean checkUserName(String name){
        return userMapper.checkUserName(name);
    }
}
