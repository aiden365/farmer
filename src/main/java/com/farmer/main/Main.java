package com.farmer.main;

import com.farmer.core.GenerateBase;
import com.farmer.core.GeneratePojo;
import com.farmer.exception.ParamsMissingException;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

/**
 * @Description
 * @Author farmer
 * @Data 2019/4/24 17:22
 * @Version 1.0
 **/
public class Main {

    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://192.168.160.128:3306";
    private static String username = "root";
    private static String password = "123456";


    /**
     * 数据库名称
     */
    protected static String databaseName = "vegetables";

    /**
     *  表名称
     */
    protected static String tableName = null;

    static {

        url += "/" + databaseName;
    }

    public static void main(String[] args) {
        try {
            GenerateBase.init(driver, url, username, password, databaseName, tableName);
        } catch (ParamsMissingException e) {

            e.printStackTrace();

        } catch (SQLException e) {

            //数据库连接失败
            if(e instanceof CommunicationsException){

            }

            //数据库不存在
            else if(e instanceof SQLSyntaxErrorException){

            }

            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        GeneratePojo generateBase = new GeneratePojo(null,null,null);
        generateBase.generate();



    }


}
