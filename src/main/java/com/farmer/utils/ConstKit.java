package com.farmer.utils;

/**
 * 常量类
 * @author farmer
 * @version 1.0
 * @data 2019/4/25 19:34
 **/
public class ConstKit {

    /**
     * 需要检查的参数
     */
    public static final String[] VALIDATA_PARAMS = new String[]{"driver", "url", "username", "password"};

    /**
     * 默认当前数据库名
     */
    public static final String DEFAULT_DATABASE_NAME = "SELECT DATABASE ( ) ";


    /**
     * 查询当前数据库所有表SQL
     */
    public static String SQL_QUERY_TABLE;


    /**
     * 查询指定表中的字段信息
     */
    public static  String SQL_QUERY_FIELD;


    /**
     * 模板所在目录 classpath:freemarker
     */
    public static final String TEMPLATE_PATH = "template";

    /**
     * 生成的类文件后缀
     */
    public static final String OUT_FILE_SUFFIX= ".java";

    /**
     * 生成的mybatis文件后缀
     */
    public static final String OUT_MYBATIS_FILE_SUFFIX= ".xml";



    /**
     * 实体类存放目录
     */
    public static final String POJO_OUT_PATH = "/pojo";

    /**
     * 业务接口存放目录
     */
    public static final String SERVICE_OUT_PATH = "/service";

    /**
     * 业务接口实现类存放目录
     */
    public static final String SERVICE_IMPL_OUT_PATH = "/service/impl";

    /**
     * 数据层接口存放目录
     */
    public static final String MAPPER_OUT_PATH = "/mapper";

    /**
     * mybaties配置文件存放目录
     */
    public static final String XML_OUT_PATH = "/xml";


    /**
     * 业务接口名标志
     */
    public static final String SERVICE_NAME_MARK = "Service";

    /**
     * 业务接口实现类存放目录
     */
    public static final String SERVICE_IMPL_NAME_MARK= "ServiceImpl";

    /**
     * 数据层接口存放目录
     */
    public static final String MAPPER_NAME_MARK = "Mapper";



    public static final String DEFAULT_PACKAGE_NAME = "com.vegetables.project";
    public static final String DEFAULT_BASE_PACKAGE_ALL_NAME = "com.vegetables.project.base";

    public static final String DEFAULT_AUTHOR = "automatic";
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm";





    static {

        SQL_QUERY_TABLE = "SELECT\n" +
                "	tal.TABLE_SCHEMA AS 'databaseName',\n" +
                "	tal.TABLE_NAME AS 'name',\n" +
                "	tal.CREATE_TIME AS 'createDate',\n" +
                "	tal.TABLE_COMMENT AS 'annotation' \n" +
                "FROM\n" +
                "	information_schema.TABLES tal \n" +
                "WHERE\n" +
                "	tal.table_schema = ( ? ) \n" +
                "	AND tal.table_type = 'BASE TABLE'";


        SQL_QUERY_FIELD = "SELECT\n" +
                "	clo.TABLE_SCHEMA AS 'databaseName',\n" +
                "	clo.TABLE_NAME AS 'tableName',\n" +
                "	clo.COLUMN_NAME AS 'name',\n" +
                "	clo.DATA_TYPE AS 'databaseType',\n" +
                "	clo.COLUMN_COMMENT AS 'annotation', \n" +
                "	CASE clo.COLUMN_KEY WHEN 'PRI' THEN 1 ELSE 0 END  as 'isKey' \n" +

                "FROM\n" +
                "	information_schema.COLUMNS clo \n" +
                "WHERE\n" +
                "	clo.table_schema = ( ? ) \n" +
                "	AND clo.table_name = ?";

    }
}
