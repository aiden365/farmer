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
