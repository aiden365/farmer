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

import java.io.*;
import java.util.List;

public class GenerateService extends BaseClassInfo {

    public static final String DEFAULT_PACKAGENAME = ConstKit.DEFAULT_PACKAGE_NAME + ".service";
    public static final String DEFAULT_BASEPACKAGEALLNAME = ConstKit.DEFAULT_BASE_PACKAGE_ALL_NAME + ".BaseService";

    public GenerateService(String packageName, String basePackageAllName, String author){
        super(StrUtil.emptyToDefault(packageName,DEFAULT_PACKAGENAME ), StrUtil.emptyToDefault(basePackageAllName, DEFAULT_BASEPACKAGEALLNAME), author);
    }

    /**
     * 生成业务接口
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
        File parentFile = new File(outPath + ConstKit.SERVICE_OUT_PATH);
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }

        GenerateBase.fieldInfoMaps.forEach(this::accept);
    }

    private void accept(String key, List<FieldInfo> fieldInfos) {
        JSONObject params = new JSONObject();

        TableInfo tableInfo = GenerateBase.tableInfos.stream().filter(v -> key.equals(v.getName())).findFirst().get();

        String pojoName = ConverUtils.converJavaClassName(key);

        String className = pojoName + ConstKit.SERVICE_NAME_MARK;

        FieldInfo fieldInfo = fieldInfos.stream().filter(v -> v.getIsKey() == 1).findFirst().get();

        String primaryKeyType = ConverUtils.converJavaType(fieldInfo.getDatabaseType());

        params.put("packageName", packageName);
        params.put("basePackageAllName", basePackageAllName);
        params.put("annotation", tableInfo.getAnnotation());
        params.put("author", author);
        params.put("createDate", DateUtil.format(createDate,ConstKit.DEFAULT_FORMAT));
        params.put("className", className);
        params.put("pojoName", pojoName);
        params.put("baseClassName", baseClassName);
        params.put("primaryKeyType", primaryKeyType);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             FileOutputStream fileOutputStream = new FileOutputStream(outPath + ConstKit.SERVICE_OUT_PATH + "/" + className + ConstKit.OUT_FILE_SUFFIX)){
            TemplateUtils.exportFreemarkerTemplate("service.ftl", byteArrayOutputStream, params);
            byteArrayOutputStream.writeTo(fileOutputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
