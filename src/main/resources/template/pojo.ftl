package ${packageName};

import lombok.Data;
${((basePackageAllName)??)?string("import ${basePackageAllName};","")}


/**
 * ${annotation}
 * @author ${author}
 * @version 1.0
 * @data ${createDate}
 **/
@Date
public class ${className} ${((baseClassName)??)?string("${baseClassName}<${primaryKeyType}>","")}{

   <#list fields as field>
    /**
     * ${field.annotation}
     */
    private ${field.javaType} ${field.propertyName};

   </#list>

}
