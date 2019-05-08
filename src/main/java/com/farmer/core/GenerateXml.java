package com.farmer.core;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.farmer.core.base.BaseXmlInfo;
import com.farmer.entity.FieldInfo;
import com.farmer.entity.TableInfo;
import com.farmer.utils.ConstKit;
import com.farmer.utils.ConverUtils;
import com.farmer.utils.TemplateUtils;
import lombok.Data;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class GenerateXml extends BaseXmlInfo {

    public static final String DEFAULT_MAPPER_PACKAGE_NAME = ConstKit.DEFAULT_PACKAGE_NAME + ".mapper";
    public static final String DEFAULT_PACKAGE_NAME = ConstKit.DEFAULT_PACKAGE_NAME + ".entity";

    public GenerateXml(String mapperPackageName, String pojoPackageName){
        super(StrUtil.emptyToDefault(mapperPackageName,DEFAULT_MAPPER_PACKAGE_NAME ), StrUtil.emptyToDefault(pojoPackageName,DEFAULT_PACKAGE_NAME ));
    }

    /**
     * 生成XML
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
        File parentFile = new File(outPath + ConstKit.XML_OUT_PATH);
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }

        GenerateBase.fieldInfoMaps.forEach(this::accept);

    }

    private void accept(String key, List<FieldInfo> fieldInfos) {
        JSONObject params = new JSONObject();


        List<GenerateXml.XmlField> fields = fieldInfos.stream().map(fieldInfo -> {

            return new GenerateXml.XmlField(fieldInfo.getIsKey() == 1, fieldInfo.getName(), ConverUtils.converJavaType(fieldInfo.getDatabaseType()), ConverUtils.converJavaProperty(fieldInfo.getName()));

        }).collect(Collectors.toList());

        String className = ConverUtils.converJavaClassName(key);

        params.put("mapperAllName", mapperPackageName + "." + className + ConstKit.MAPPER_NAME_MARK);
        params.put("entityAllName", pojoPackageName + "." +  className);
        params.put("fields", fields);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             FileOutputStream fileOutputStream = new FileOutputStream(outPath + ConstKit.XML_OUT_PATH + "/" + className + ConstKit.OUT_MYBATIS_FILE_SUFFIX)){
            TemplateUtils.exportFreemarkerTemplate("xml.ftl", byteArrayOutputStream, params);
            byteArrayOutputStream.writeTo(fileOutputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Data
    class XmlField{

        private Boolean isKey;

        private String columnName;

        private String dataType;

        private String propertyName;

        public XmlField(Boolean isKey, String columnName, String dataType, String propertyName) {
            this.isKey = isKey;
            this.columnName = columnName;
            this.dataType = dataType;
            this.propertyName = propertyName;
        }
    }


}
