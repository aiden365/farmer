package com.farmer.utils;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.tokenizer.Result;
import cn.hutool.extra.tokenizer.TokenizerEngine;
import cn.hutool.extra.tokenizer.TokenizerUtil;
import cn.hutool.extra.tokenizer.Word;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author farmer
 * @version 1.0
 * @data 2019/4/26 13:22
 **/
public class ConverUtils {

    private static Map<String, String> typeMap = new HashMap<>();

    private final static String DEFAULT_TYPE = "String";

    static {

        typeMap.put("int", "Long");
        typeMap.put("bigint", "Long");

        typeMap.put("float", "Double");
        typeMap.put("double", "Double");
        typeMap.put("decimal", "Double");
        typeMap.put("real", "Double");

        typeMap.put("datetime", "java.sql.Date");
        typeMap.put("timestamp", "java.sql.Date");
        typeMap.put("date", "java.sql.Date");
        typeMap.put("time", "java.sql.Time");

        typeMap.put("bit", "Boolean");

        typeMap.put("varchar", "String");
        typeMap.put("longtext", "String");
        typeMap.put("char", "String");
        typeMap.put("json", "String");

    }

    public static String converJavaType(String columnType) {
        return typeMap.getOrDefault(columnType, DEFAULT_TYPE);
    }

    public static String converJavaProperty(String columnName) {

        //自动根据用户引入的分词库的jar来自动选择使用的引擎
        TokenizerEngine engine = TokenizerUtil.createEngine();

        Result result = engine.parse(columnName);

        List<String> collect = Arrays.asList(CollUtil.join(((Iterator<Word>)result), "").split("_")).stream().map(v -> StrUtil.upperFirst(v)).collect(Collectors.toList());

        String lowerFirst = StrUtil.lowerFirst(collect.stream().findFirst().get());

        return collect.size() > 1 ? lowerFirst + CollUtil.join(collect.subList(1, collect.size()), "") : lowerFirst;
    }

    public static String converJavaClassName(String tableName) {

        //自动根据用户引入的分词库的jar来自动选择使用的引擎
        TokenizerEngine engine = TokenizerUtil.createEngine();

        Result result = engine.parse(tableName);
        List<String> collect = Arrays.asList(CollUtil.join(((Iterator<Word>) result), "").split("_")).stream().map(v -> StrUtil.upperFirst(v)).collect(Collectors.toList());

        collect.remove(0);

        return CollUtil.join(collect,"");

    }

}
