<#--

package ${packageName};

${(basePackageAllName??)?string("import ${basePackageAllName?if_exists};","")}

/**
 * ${annotation}
 * @author ${author}
 * @version 1.0
 * @data ${createDate}
 **/
public class ${className} ${(baseClassName??)?string("extends ${baseClassName?if_exists}<${pojoName}>","")} implements ${serviceName}{


}


-->


package ${packageName};


${(basePackageAllName??)?string("import ${basePackageAllName?if_exists};","")}
${(pojoPackageAllName??)?string("import ${pojoPackageAllName?if_exists};","")}
${(servicePackageAllName??)?string("import ${servicePackageAllName?if_exists};","")}

import org.springframework.stereotype.Service;


/**
* ${annotation}
* @author ${author}
* @version 1.0
* @data ${createDate}
**/
@Service
public class ${className} ${(baseClassName??)?string("extends ${baseClassName?if_exists}<${pojoName},${primaryKeyType}>","")} implements ${serviceName}{


}
