package com.farmer.core;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.farmer.core.base.BaseClassInfo;
import com.farmer.entity.FieldInfo;
import com.farmer.entity.TableInfo;
import com.farmer.utils.ConstKit;
import com.farmer.utils.ConverUtils;
import com.farmer.utils.TemplateUtils;
import lombok.Data;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;


public class GeneratePojo extends BaseClassInfo{

    public static final String DEFAULT_PACKAGENAME = ConstKit.DEFAULT_PACKAGE_NAME + ".entity";
    public static final String DEFAULT_BASEPACKAGEALLNAME = ConstKit.DEFAULT_BASE_PACKAGE_ALL_NAME + ".BaseEntity";

    public GeneratePojo(String packageName, String basePackageAllName, String author){

        super(StrUtil.emptyToDefault(packageName,DEFAULT_PACKAGENAME ), StrUtil.emptyToDefault(basePackageAllName, DEFAULT_BASEPACKAGEALLNAME), author);
    }

    /**
     * 生成实体类
     * @author farmer
     * @date 2019/4/24 19:48
     * @param
     * @return void
     */
    public void generate(){

        if(GenerateBase.fieldInfoMaps.isEmpty()){

            System.out.printf("did not find tables, please first init");
            return;
        }

        //检查目录父级
        File parentFile = new File(outPath + ConstKit.POJO_OUT_PATH);
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }

        GenerateBase.fieldInfoMaps.forEach(this::accept);

    }

    private void accept(String key, List<FieldInfo> fieldInfos) {
        JSONObject params = new JSONObject();

        List<Field> fields = fieldInfos.stream().map(fieldInfo -> {

            return new Field(ConverUtils.converJavaType(fieldInfo.getDatabaseType()), fieldInfo.getAnnotation(), ConverUtils.converJavaProperty(fieldInfo.getName()), fieldInfo.getName(), fieldInfo.getIsKey() == 1);

        }).collect(Collectors.toList());


        TableInfo tableInfo = GenerateBase.tableInfos.stream().filter(v -> key.equals(v.getName())).findFirst().get();

        String className = ConverUtils.converJavaClassName(key);

        FieldInfo fieldInfo = fieldInfos.stream().filter(v -> v.getIsKey() == 1).findFirst().get();

        String primaryKeyType = ConverUtils.converJavaType(fieldInfo.getDatabaseType());

        params.put("packageName", packageName + "." + ConstKit.DEFAULT_POJO_PACKAGE_NAME);
        params.put("basePackageAllName", basePackageAllName);
        params.put("annotation", tableInfo.getAnnotation());
        params.put("author", author);
        params.put("createDate", DateUtil.format(createDate,ConstKit.DEFAULT_FORMAT));
        params.put("className", className);
        params.put("baseClassName", baseClassName);
        params.put("primaryKeyType", primaryKeyType);
        params.put("fields", fields);
        params.put("tableName", key);




        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             FileOutputStream fileOutputStream = new FileOutputStream(outPath + ConstKit.POJO_OUT_PATH + "/" + className + ConstKit.OUT_FILE_SUFFIX)){
            TemplateUtils.exportFreemarkerTemplate("pojo.ftl", byteArrayOutputStream, params);
            byteArrayOutputStream.writeTo(fileOutputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Data
    class Field{
        private String javaType;
        private String annotation;
        private String propertyName;
        private String columnName;
        private Boolean isKey;

        public Field(String javaType, String annotation, String propertyName, String columnName, Boolean isKey) {
            this.javaType = javaType;
            this.annotation = annotation;
            this.propertyName = propertyName;
            this.columnName = columnName;
            this.isKey = isKey;
        }
    }


}
