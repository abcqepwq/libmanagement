package com.company.libmanagementlogin.entity;
import com.company.libmanagementlogin.enums.RoleEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
/**
 * @Description:
 * @author loda
 * @date 2023/10/22/
 */
@Setter
@Getter
@Data
public class User{
    private Integer userId;
    private String userName;
    private String password;
    private int age;
    private long phone;
    private int gender;

    private List<Role> roleList;

    public User(String userName, String password){
        this.password = password;
        this.userName = userName;
        this.roleList = new ArrayList<>();
    }

    public boolean isAdmin(){
        if (roleList == null) return false;
        for (Role role : roleList) {
            if (RoleEnum.ADMIN.getValue() == (role.getRoleId())){
                return true;
            }
        }
        return false;
    }
}

