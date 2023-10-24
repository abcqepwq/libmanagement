package com.company.libmanagementlogin.mapper;

import com.company.libmanagementlogin.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
/**
 * @Description:
 * @author loda
 * @date 2023/10/22/
 */
@Mapper
@Repository
public interface UserMapper {

    boolean addUser(User user);

    boolean checkUserName(String name);

    User getUser(@Param("name") String name);

    boolean addUserRole(@Param("userId")Integer userId, @Param("roleId")Integer roleId);
}