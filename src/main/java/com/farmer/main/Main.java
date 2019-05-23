package com.farmer.main;

import cn.hutool.core.io.FileUtil;
import com.farmer.core.*;
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
    private static String url = "jdbc:mysql://212.103.62.162:3306";
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

    /**
     * 输出路径
     */
    protected static String outPath = "F:\\outPath\\temp";

    public static void main(String[] args) {


        FileUtil.del("F:\\outPath\\temp");

        try {
            GenerateBase.init(driver, url, username, password, databaseName, tableName, outPath);
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

        String packageName = "com.auth.core";

        GeneratePojo generatePojo = new GeneratePojo(packageName,"com.auth.base.BaseEntity",null);
        generatePojo.generate();
//
//        GenerateService generateService = new GenerateService(packageName,"com.auth.base.BaseService",null);
//        generateService.generate();
//
//        GenerateServiceImpl generateService1 = new GenerateServiceImpl(packageName,"com.auth.base.BaseServiceImpl",null);
//        generateService1.generate();
//
//        GenerateDao generateDao = new GenerateDao(packageName,"com.auth.base.BaseRepository",null);
//        generateDao.generate();

//        GenerateXml generateXml = new GenerateXml(null, packageName);
//        generateXml.generate();

    }


}
