package com.farmer.core;

import com.farmer.entity.FieldInfo;
import com.farmer.entity.TableInfo;
import com.farmer.exception.ParamsMissingException;
import com.farmer.utils.ConstKit;
import com.farmer.utils.ReflectUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description
 * @author farmer
 * @date 2019/4/24 17:23
 * @version 1.0
 * */
public class GenerateBase {


    protected static String driver;
    protected static String url;
    protected static String username;
    protected static String password;

    /**
     * 输出路径
     */
    protected static String outPath;

    /**
     * 数据库名称
     */
    protected static String databaseName;

    /**
     *  表名称
     */
    protected static String tableName;

    protected static List<TableInfo> tableInfos = new ArrayList<>();

    protected static Map<String, List<FieldInfo>> fieldInfoMaps = new HashMap<>();

    private static Connection connection = null;

    /**
     * 是否初始化
     */
    private static boolean isInit = false;

    public GenerateBase() {


    }


    public static void init(String... params) throws ParamsMissingException, SQLException, ClassNotFoundException {

        for (int i = 0; i < params.length; i++) {

            switch (i){

                case 0 : GenerateBase.driver  = params[i]; break;
                case 1 : GenerateBase.url  = params[i]; break;
                case 2 : GenerateBase.username  = params[i]; break;
                case 3 : GenerateBase.password  = params[i]; break;
                case 4 : GenerateBase.databaseName  = params[i]; break;
                case 5 : GenerateBase.tableName  = params[i]; break;
                case 6 : GenerateBase.outPath  = params[i]; break;
            }
        }

        //检查参数
        validataParams();
        //初始化连接
        initConnection();
        //初始化表名
        initTableInfo();
        //初始化属性名
        initField();

        isInit = true;
    }

    private static void validataParams()throws ParamsMissingException{

        Class<GenerateBase> generateBaseClass = GenerateBase.class;

        for (int i = 0; i < ConstKit.VALIDATA_PARAMS.length; i++) {

            try {

                //绕过
                Field field = generateBaseClass.getDeclaredField(ConstKit.VALIDATA_PARAMS[i]);

                String val = (String) field.get(generateBaseClass);

                if(val.isEmpty()){
                    throw new ParamsMissingException(field.getName() + "is not null, Please check it");
                }

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

    }



    /**
     * 创建JDBC连接
     * @author farmer
     * @date 2019/4/24 17:41
     * @param
     * @return java.sql.Connection
     */
    private static Connection initConnection() throws ClassNotFoundException, SQLException {


        if(connection != null){
            connection.close();
        }

        return connection = connection = (Connection) DriverManager.getConnection(url, username, password);
    }




    private static void initTableInfo() throws SQLException {


        try(PreparedStatement preparedStatement = connection.prepareStatement(ConstKit.SQL_QUERY_TABLE)){

            preparedStatement.setString(1, databaseName);

            try(ResultSet resultSet = preparedStatement.executeQuery()){


                while(resultSet.next()){

                    tableInfos.add(ReflectUtils.resultSetToObject(resultSet, TableInfo.class));
                }
            }

        }
    }


    private static void initField() throws SQLException {


        List<String> tableNames = tableName == null || tableName.isEmpty() ? tableInfos.stream().map(v -> v.getName()).collect(Collectors.toList()) : Arrays.asList(tableName);

        if(tableNames.isEmpty()){
            System.out.printf("No data table was obtained，please check database table is exist");
            return;
        }

        for (String name : tableNames) {

            try(PreparedStatement preparedStatement = connection.prepareStatement(ConstKit.SQL_QUERY_FIELD)){

                preparedStatement.setString(1, databaseName);
                preparedStatement.setString(2, name);

                try(ResultSet resultSet = preparedStatement.executeQuery()){

                    List<FieldInfo> fieldInfos = new ArrayList<>();

                    while(resultSet.next()){

                        FieldInfo fieldInfo = ReflectUtils.resultSetToObject(resultSet, FieldInfo.class);

                        fieldInfos.add(fieldInfo);

                    }

                    fieldInfoMaps.put(name, fieldInfos);
                }

            }
        }

    }

}
