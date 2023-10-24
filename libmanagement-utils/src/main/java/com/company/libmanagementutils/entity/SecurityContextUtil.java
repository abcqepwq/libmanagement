package com.company.libmanagementutils.entity;

import org.springframework.core.NamedThreadLocal;

public class SecurityContextUtil {

    private static ThreadLocal<Integer> threadLocal = new NamedThreadLocal<>("userId");

    public static void addUser(Integer userId){
        threadLocal.set(userId);
    }

    public static Integer getUser(){
        return threadLocal.get();
    }

    public static void removeUser(){
        threadLocal.remove();
    }
}