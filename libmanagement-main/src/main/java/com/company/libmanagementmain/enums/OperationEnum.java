package com.company.libmanagementmain.enums;

import java.util.Arrays;
import java.util.Optional;

public enum OperationEnum {
    REGISTER("$register"),
    LOGIN("$login"),
    LOGOUT("$logout"),
    BORROW("$borrow"),
    RETURN("$return"),
    DELETE("$delete"),
    LIST("$list"),
    SEARCH("$search"),
    ADD("$add");

    private String url;

    OperationEnum(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
    public static Optional<OperationEnum> get(String url) {
        return Arrays.stream(OperationEnum.values())
                .filter(env -> env.url.equals(url))
                .findFirst();
    }
}
