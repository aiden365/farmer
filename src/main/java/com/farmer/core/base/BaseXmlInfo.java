package com.farmer.core.base;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author farmer
 * @version 1.0
 * @data 2019/4/26 14:05
 **/

@Data
public class BaseXmlInfo {

    /**
     * mapper全限名
     */
    private String mapperAllName;

    /**
     * mapper全限名
     */
    private String entityAllName;

    /**
     * mapper全限名
     */
    private List<XmlFieldInfo> fields = new ArrayList<>();


    @Data
    class XmlFieldInfo{

        private Boolean isKey;

        private String columnName;

        private String dataType;

        private String propertyName;
    }

}


