package com.company.libmanagementutils.utils;

/**
 * @description: id helper class
 * @author: loda
 * @create: 2023/10/22
 **/
public class IDUtil {
    /**
     * random generate id, Snowflake algorithm.
     */
    public static long getRandomId() {
        SnowflakeIdWorker sf = new SnowflakeIdWorker();
        long id = sf.nextId();
        return id;
    }
}