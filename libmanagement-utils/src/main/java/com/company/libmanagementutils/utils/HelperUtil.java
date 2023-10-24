package com.company.libmanagementutils.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelperUtil {
    private static String PWD_REGEX = "^([A-Z]|[a-z]|[0-9]){6,32}$";
    private static String ACCOUNT_NAME_REGEX = "^[a-zA-Z]+$";
    private static String NAME_REGEX = "^([A-Z]|[a-z]|\\s){1,255}$";

    private static List<String> OPERATION_LIST = Arrays.asList("$register", "$login");

    private static String NUMBER_REGEX = "^([1-9][0-9]*)$";
    public static boolean checkPwd(String pwd){
        if (pwd == null || pwd.isEmpty()){
            return false;
        }

        Pattern pattern = Pattern.compile(PWD_REGEX);
        Matcher matcher = pattern.matcher(pwd);
        if (matcher.matches()){
            return true;
        }

        return false;
    }

    public static boolean checkAccountName(String name){
        if (name == null || name.isEmpty()){
            return false;
        }

        if (name.trim().length() == 0){
            return false;
        }

        Pattern pattern = Pattern.compile(ACCOUNT_NAME_REGEX);
        Matcher matcher = pattern.matcher(name);
        if (matcher.matches()){
            return true;
        }

        return false;
    }

    public static boolean checkBookName(String name){
        if (name == null || name.isEmpty()){
            return false;
        }

        if (name.trim().length() == 0 || name.trim().length() > 255){
            return false;
        }
        return true;
    }

    public static boolean checkName(String name){
        if (name == null || name.isEmpty()){
            return false;
        }

        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher matcher = pattern.matcher(name.trim());
        if (matcher.matches()){
            return true;
        }

        return false;
    }

    public static boolean checkNumber(String input){
        if (input == null || input.isEmpty()){
            return false;
        }

        Pattern pattern = Pattern.compile(NUMBER_REGEX);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()){
            return true;
        }

        return false;
    }

    public static Integer convertString2Int(String input){
        try{
            return Integer.parseInt(input);
        }catch (Exception ex){
            throw ex;
        }
    }

}
