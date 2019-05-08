package com.farmer.core.base;

import com.farmer.core.GenerateBase;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author farmer
 * @version 1.0
 * @data 2019/4/26 14:05
 **/

@Data
public class BaseXmlInfo extends GenerateBase {

    /**
     * mapper所在报名
     */
    protected String mapperPackageName;


    public BaseXmlInfo() {
    }
    public BaseXmlInfo(String mapperPackageName) {
        this.mapperPackageName = mapperPackageName;
    }
}


