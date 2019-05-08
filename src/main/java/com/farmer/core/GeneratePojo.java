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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class GeneratePojo extends BaseClassInfo{


    public static final String DEFAULT_PACKAGENAME = ConstKit.DEFAULT_PACKAGENAME  + ".entity";
    public static final String DEFAULT_BASEPACKAGEALLNAME = ConstKit.DEFAULT_BASEPACKAGEALLNAME  + ".BaseEntity.java";


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

        GenerateBase.fieldInfoMaps.forEach(this::accept);

    }

    private void accept(String key, List<FieldInfo> fieldInfos) {
        JSONObject params = new JSONObject();

        List<Field> fields = fieldInfos.stream().map(fieldInfo -> {

            return new Field(ConverUtils.converJavaType(fieldInfo.getDatabaseType()), fieldInfo.getAnnotation(), ConverUtils.converJavaProperty(fieldInfo.getName()));

        }).collect(Collectors.toList());


        TableInfo tableInfo = GenerateBase.tableInfos.stream().filter(v -> key.equals(v.getName())).findFirst().get();

        String className = ConverUtils.converJavaClassName(key);

        FieldInfo fieldInfo = fieldInfos.stream().filter(v -> v.getIsKey() == 1).findFirst().get();

        String primaryKeyType = ConverUtils.converJavaType(fieldInfo.getDatabaseType());


        params.put("packageName", packageName);
        params.put("basePackageAllName", basePackageAllName);
        params.put("annotation", tableInfo.getAnnotation());
        params.put("author", author);
        params.put("createDate", DateUtil.format(createDate,ConstKit.DEFAULT_FORMAT));
        params.put("className", className);
        params.put("baseClassName", baseClassName);
        params.put("primaryKeyType", primaryKeyType);
        params.put("fields", fields);

        System.out.println(params.toJSONString());


        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\chenj\\Desktop\\temp\\1.java")){

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

        public Field(String javaType, String annotation, String propertyName) {
            this.javaType = javaType;
            this.annotation = annotation;
            this.propertyName = propertyName;
        }
    }


}
