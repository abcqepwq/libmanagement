package com.company.libmanagementlogin.controller;

import com.company.libmanagementlogin.entity.User;
import com.company.libmanagementlogin.enums.RoleEnum;
import com.company.libmanagementlogin.service.impl.UserService;
import com.company.libmanagementutils.entity.LibResultInfo;
import com.company.libmanagementutils.utils.Encryption;
import com.company.libmanagementutils.utils.HelperUtil;
import com.company.libmanagementutils.utils.JwtUtils;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author loda
 * @date 2023/10/22/
 */
@Api(tags = "auth")
@RestController
@RequestMapping(value = "api/v1/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);


    @RequestMapping(value="new-user", method= RequestMethod.POST)
    public LibResultInfo RegisterUser(String userName, String pwd, boolean isAdmin){
        if (!HelperUtil.checkAccountName(userName)){
            return new LibResultInfo(false, "Name only allows letters and spaces");
        }
        if (!HelperUtil.checkPwd(pwd)){
            return new LibResultInfo(false, "password only allows letters and numbers, length between 6 and 32.");
        }

        if (userService.checkUserName(userName.trim())){
            return new LibResultInfo(false, "name has existed");
        }
        User user = new User(userName.trim(), Encryption.SHA256Encryption(pwd.trim()));
        if (!userService.addUser(user, isAdmin)){
            return new LibResultInfo(false, "Register has failed");
        }else{
            return new LibResultInfo(true, "");
        }
    }

    @RequestMapping( method= RequestMethod.POST)
    public LibResultInfo userLogin(String userName, String pwd){
        if (!HelperUtil.checkAccountName(userName.trim()) || !HelperUtil.checkPwd(pwd.trim())){
            return null;
        }

        User user = userService.getUser(userName.trim());
        if(user != null && Encryption.SHA256Encryption(pwd.trim()).equals(user.getPassword())){
            log.info(String.format("User :" + userName + " login."));
            String token = JwtUtils.getToken(user.getUserId(), user.isAdmin() ? RoleEnum.ADMIN.getLabel() : "");
            return new LibResultInfo(true, token, user.isAdmin());
        }

        return new LibResultInfo(false, "password is wrong.");
    }
}
