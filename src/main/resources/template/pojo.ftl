<#--

package ${packageName};

import lombok.Data;
import javax.persistence.Entity;
${(basePackageAllName??)?string("import ${basePackageAllName?if_exists };","")}



/**
 * ${annotation}
 * @author ${author}
 * @version 1.0
 * @data ${createDate}
 **/
@Data
@Entity
public class ${className} ${(baseClassName??)?string("extends ${baseClassName?if_exists}<${primaryKeyType}>","")}{

   <#list fields as field>

    /**
     * ${field.annotation}
     */
    @Column(name = "${field.columnName}")
    private ${field.javaType} ${field.propertyName};

   </#list>

}


-->

package ${packageName};

import lombok.Data;
import javax.persistence.*;
${(basePackageAllName??)?string("import ${basePackageAllName?if_exists };","")}



/**
* ${annotation}
* @author ${author}
* @version 1.0
* @data ${createDate}
**/
@Data
@Entity
@Table(name = "${tableName}")
public class ${className} ${(baseClassName??)?string("extends ${baseClassName?if_exists}<${primaryKeyType}>","")}{

<#list fields as field>

 <#if field.isKey>

  /**
  * 主键
  */
  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private ${field.javaType} ${field.propertyName};

 <#else >

  /**
  * ${field.annotation}
  */
  @Column(name = "${field.columnName}")
  private ${field.javaType} ${field.propertyName};

 </#if>


</#list>

}


