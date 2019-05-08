<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" " http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperAllName}">
    <resultMap id="BaseResultMap" type="${entityAllName}">
        <#list fields as field>

            <#if field.isKey>
                <id column="${field.columnName}" jdbcType="${field.dataType}" property="${field.propertyName}"/>
            <#else>
                <result column="${field.columnName}" jdbcType="${field.dataType}" property="${field.propertyName}"/>
            </#if>
        </#list>
    </resultMap>

</mapper>
