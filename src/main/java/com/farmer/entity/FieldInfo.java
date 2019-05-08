package com.farmer.entity;

import lombok.Data;

/**
 * @author farmer
 * @version 1.0
 * @data 2019/4/25 19:01
 **/

@Data
public class FieldInfo {


    private String databaseName;

    private String tableName;

    private String name;

    private String databaseType;

    private String annotation;

    private Long isKey;



}
