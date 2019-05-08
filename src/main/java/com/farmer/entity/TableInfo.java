package com.farmer.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author farmer
 * @version 1.0
 * @data 2019/4/25 19:00
 **/
@Data
public class TableInfo {

    private String databaseName;

    private String name;

    private Date createDate;

    private String annotation;

}
