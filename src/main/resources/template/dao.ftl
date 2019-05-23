<#--

package ${packageName};

/**
* ${annotation}
* @author ${author}
* @version 1.0
* @data ${createDate}
**/
public class ${className}  {




}


-->

package ${packageName};

${(basePackageAllName??)?string("import ${basePackageAllName?if_exists};","")}
${(pojoPackageAllName??)?string("import ${pojoPackageAllName?if_exists};","")}
import org.springframework.stereotype.Repository;


/**
* ${annotation}
* @author ${author}
* @version 1.0
* @data ${createDate}
**/

@Repository
public interface ${className} ${(baseClassName??)?string("extends ${baseClassName?if_exists}<${pojoName},${primaryKeyType}>","")}{




}
