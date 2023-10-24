package com.company.libmanagementorder.enums;

public enum RoleEnum {
    ADMIN("Admin", 1),

    USER("User", 255);

    private final String label;
    private final int roleId;

    RoleEnum(String label, int value){
        this.label = label;
        this.roleId =value;
    }

    public int getValue(){
        return this.roleId;
    }

    public String getLabel(){
        return this.label;
    }

    public static String printLabel(int value){
        switch(value){
            case 1:
                return RoleEnum.ADMIN.label;
            case 255:
                return RoleEnum.USER.label;
            default:
                return "";
        }
    }
}
