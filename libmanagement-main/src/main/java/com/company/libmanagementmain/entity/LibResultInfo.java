package com.company.libmanagementmain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class LibResultInfo {
    private boolean status;

    private String info;

    private boolean isAdmin;

    public LibResultInfo(boolean status, String info){
        this.status = status;
        this.info = info;
    }
}

